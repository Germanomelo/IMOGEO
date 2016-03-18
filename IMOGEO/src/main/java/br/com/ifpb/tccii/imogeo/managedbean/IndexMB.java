package br.com.ifpb.tccii.imogeo.managedbean;

import br.com.ifpb.tccii.imogeo.entidades.Endereco;
import br.com.ifpb.tccii.imogeo.entidades.Imovel;
import br.com.ifpb.tccii.imogeo.entidades.Usuario;
import br.com.ifpb.tccii.imogeo.entidades.especializacao.Apartamento;
import br.com.ifpb.tccii.imogeo.entidades.especializacao.Casa;
import br.com.ifpb.tccii.imogeo.sessionbeans.ApartamentoDao;
import br.com.ifpb.tccii.imogeo.sessionbeans.CasaDao;
import br.com.ifpb.tccii.imogeo.sessionbeans.EnderecoDao;
import br.com.ifpb.tccii.imogeo.sessionbeans.ImovelDao;
import br.com.ifpb.tccii.imogeo.sessionbeans.UsuarioDao;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Germano
 */
@ManagedBean(name = "indexMB")
@ViewScoped
public class IndexMB implements Serializable {

    private String loc;
    private double lat;
    private double log;
    private int distancia = 5;
    private String tituloH3 = "Imóveis";
    private String finalidade = null;
    private boolean exibeTodosImoveis = true;
    private boolean exibePesquisaLocalizacao = false;
    private boolean exibeResultadoPesquisaLocalizacao = false;
    private boolean exibeCasas = false;
    private boolean exibeAptos = false;
    private boolean exibeImovelFinalidade = false;
    private boolean exibebuscaSimples = false;
    private boolean exibeDetalhesCasa = false;
    private boolean exibeDetalhesApto = false;
    private String busca = null;

    private Casa casa = new Casa();
    private Imovel imovel = new Imovel();
    private Apartamento apto = new Apartamento();
    private Usuario user = new Usuario();
    private Endereco endereco = new Endereco();

    @EJB
    private UsuarioDao userDao;

    @EJB
    private EnderecoDao enderecoDao;

    @EJB
    private CasaDao casaDao;

    @EJB
    private ImovelDao imovelDao;

    @EJB
    private ApartamentoDao aptoDao;

    public IndexMB() {
    }

    public Geometry geometriaLocalUser() {
        Geometry g1 = null;
        try {
            g1 = new WKTReader().read(this.loc);
        } catch (ParseException ex) {
            this.mensagemErro("Erro!", ex.getMessage());
        }
        return g1;
    }

    public List<Endereco> enderecoPorLocalizacao() throws ParseException {
        List<Endereco> result = new ArrayList<Endereco>();
        List<Endereco> enderecos;
        try {
            enderecos = enderecoDao.listarEnderecosPorDistancia(this.loc, this.distancia);
            for (int i = 0; i < enderecos.size(); i++) {
                if (enderecos.get(i).getImovel().getAnuncio().getAnunciado() == true) {
                    result.add(enderecos.get(i));
                }
            }
        } catch (Exception e) {
            this.mensagemErro("Erro!", e.getMessage());
        }

        return result;
    }
//
//    public void buscarUsuario() {
//        
//        this.user = userDao.listarUsuarioPorImovel(this.imovel);
//    }

    public List<Imovel> buscaAvançada() {
        return null;
    }

    public List<Imovel> buscaSimplesPorPalavraChave() {
        try {
            return imovelDao.listarSimplesPorPalavraChave(busca);
        } catch (Exception e) {
            this.mensagemErro("Erro!", e.getMessage());
        }
        return null;
    }

    public List<Apartamento> listarApartamentosAnunciados() {
        try {
            return aptoDao.listarApartamentosAnunciados();
        } catch (Exception e) {
            this.mensagemErro("Erro!", e.getMessage());
        }
        return null;
    }

    public List<Casa> listarCasasAnunciadas() {
        try {
            return casaDao.listarCasasAnunciadas();
        } catch (Exception e) {
            this.mensagemErro("Erro!", e.getMessage());
        }
        return null;
    }

    public List<Imovel> listarImoveisAnunciados() {
        try {
            return imovelDao.listarImoveisAnunciados();
        } catch (Exception e) {
            this.mensagemErro("Erro!", e.getMessage());
        }
        return null;
    }

    public List listarImoveisFinalidadeAnunciados() {
        try {
            return imovelDao.listarImoveisFinalidadeAnunciados(finalidade);
        } catch (Exception e) {
            this.mensagemErro("Erro!", e.getMessage());
        }
        return null;
    }

    public void detalhesImovel() {
        if (this.imovel instanceof Casa) {
            this.detalhesCasa();
        } else if (this.imovel instanceof Apartamento) {
            this.detalhesApto();
        } else {
            this.mensagemErro("Erro!", "Erro ao ver informações de Imovél, tente mais tarde");
        }
    }

    public void detalhesCasa() {
        try {
            this.casa = casaDao.retornarCasa(this.imovel.getId());
        } catch (Exception e) {
            this.mensagemErro("Erro!", e.getMessage());
        }
        this.telaDetalhesCasa();
    }

    public void detalhesApto() {
        try {
            this.apto = aptoDao.retornarApartamento(this.imovel.getId());
        } catch (Exception e) {
            this.mensagemErro("Erro!", e.getMessage());
        }
        this.telaDetalhesApto();
    }

    public void telaDetalhesApto() {
        this.exibePesquisaLocalizacao = false;
        this.exibeResultadoPesquisaLocalizacao = false;
        this.exibeDetalhesApto = true;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = false;
        this.tituloH3 = "Apartamento";
        this.lat = this.apto.getEndereco().getLocalizacao().getCoordinate().x;
        this.log = this.apto.getEndereco().getLocalizacao().getCoordinate().y;
    }

    public void telaDetalhesCasa() {
        this.exibePesquisaLocalizacao = false;
        this.exibeResultadoPesquisaLocalizacao = false;
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = true;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = false;
        this.tituloH3 = "Casa";
        this.lat = this.casa.getEndereco().getLocalizacao().getCoordinate().x;
        this.log = this.casa.getEndereco().getLocalizacao().getCoordinate().y;
    }

    public void telaExibebuscaSimples() {
        this.exibePesquisaLocalizacao = false;
        this.exibeResultadoPesquisaLocalizacao = false;
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = true;
        this.tituloH3 = "Busca Simples";
    }

    public void telaExibeTodosImoveis() {
        this.exibePesquisaLocalizacao = false;
        this.exibeResultadoPesquisaLocalizacao = false;
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = true;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = false;
        this.tituloH3 = "Imóveis";
    }

    public void telaExibeCasas() {
        this.exibePesquisaLocalizacao = false;
        this.exibeResultadoPesquisaLocalizacao = false;
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = true;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = false;
        this.tituloH3 = "Casas";
    }

    public void telaExibeAptos() {
        this.exibePesquisaLocalizacao = false;
        this.exibeResultadoPesquisaLocalizacao = false;
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = true;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = false;
        this.tituloH3 = "Apartamentos";
    }

    public void telaExibeImovelTemporada() {
        this.exibePesquisaLocalizacao = false;
        this.exibeResultadoPesquisaLocalizacao = false;
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = true;
        this.exibebuscaSimples = false;
        this.finalidade = "TEMPORADA";
        this.tituloH3 = "Temporada";
    }

    public void telaExibeImovelComprar() {
        this.exibePesquisaLocalizacao = false;
        this.exibeResultadoPesquisaLocalizacao = false;
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = true;
        this.exibebuscaSimples = false;
        this.finalidade = "VENDER";
        this.tituloH3 = "Comprar ";
    }

    public void telaExibeImovelAlugar() {
        this.exibePesquisaLocalizacao = false;
        this.exibeResultadoPesquisaLocalizacao = false;
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = true;
        this.exibebuscaSimples = false;
        this.finalidade = "ALUGAR";
        this.tituloH3 = "Alugar";
    }

    public void telaResultadoPesquisaLocalizacao() {
        this.exibePesquisaLocalizacao = false;
        this.setExibeResultadoPesquisaLocalizacao(true);
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = false;
        this.tituloH3 = "Raio de atuação da busca: " + this.distancia + "Km";
    }

    public void telaPesquisaLocalizacao() {
        this.exibePesquisaLocalizacao = true;
        this.exibeResultadoPesquisaLocalizacao = false;
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = false;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLog() {
        return log;
    }

    public void setLog(double log) {
        this.log = log;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public String getTituloH3() {
        return tituloH3;
    }

    public void setTituloH3(String tituloH3) {
        this.tituloH3 = tituloH3;
    }

    public String getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(String finalidade) {
        this.finalidade = finalidade;
    }

    public boolean isExibeTodosImoveis() {
        return exibeTodosImoveis;
    }

    public void setExibeTodosImoveis(boolean exibeTodosImoveis) {
        this.exibeTodosImoveis = exibeTodosImoveis;
    }

    public boolean isExibePesquisaLocalizacao() {
        return exibePesquisaLocalizacao;
    }

    public void setExibePesquisaLocalizacao(boolean exibePesquisaLocalizacao) {
        this.exibePesquisaLocalizacao = exibePesquisaLocalizacao;
    }

    public boolean isExibeCasas() {
        return exibeCasas;
    }

    public void setExibeCasas(boolean exibeCasas) {
        this.exibeCasas = exibeCasas;
    }

    public boolean isExibeAptos() {
        return exibeAptos;
    }

    public void setExibeAptos(boolean exibeAptos) {
        this.exibeAptos = exibeAptos;
    }

    public boolean isExibeImovelFinalidade() {
        return exibeImovelFinalidade;
    }

    public void setExibeImovelFinalidade(boolean exibeImovelFinalidade) {
        this.exibeImovelFinalidade = exibeImovelFinalidade;
    }

    public boolean isExibebuscaSimples() {
        return exibebuscaSimples;
    }

    public void setExibebuscaSimples(boolean exibebuscaSimples) {
        this.exibebuscaSimples = exibebuscaSimples;
    }

    public boolean isExibeDetalhesCasa() {
        return exibeDetalhesCasa;
    }

    public void setExibeDetalhesCasa(boolean exibeDetalhesCasa) {
        this.exibeDetalhesCasa = exibeDetalhesCasa;
    }

    public boolean isExibeDetalhesApto() {
        return exibeDetalhesApto;
    }

    public void setExibeDetalhesApto(boolean exibeDetalhesApto) {
        this.exibeDetalhesApto = exibeDetalhesApto;
    }

    public String getBusca() {
        return busca;
    }

    public void setBusca(String busca) {
        this.busca = busca;
    }

    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public Apartamento getApto() {
        return apto;
    }

    public void setApto(Apartamento apto) {
        this.apto = apto;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public boolean isExibeResultadoPesquisaLocalizacao() {
        return exibeResultadoPesquisaLocalizacao;
    }

    public void setExibeResultadoPesquisaLocalizacao(boolean exibeResultadoPesquisaLocalizacao) {
        this.exibeResultadoPesquisaLocalizacao = exibeResultadoPesquisaLocalizacao;
    }

//    Mensagens
    public void mensagemInformativa(String titulo, String msg) {
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(titulo, msg);
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        fc.addMessage(null, fm);
    }

    public void mensagemErro(String titulo, String msg) {
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(titulo, msg);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        fc.addMessage(null, fm);
    }

    public void mensagemAlerta(String titulo, String msg) {
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(titulo, msg);
        fm.setSeverity(FacesMessage.SEVERITY_WARN);
        fc.addMessage(null, fm);
    }
}
