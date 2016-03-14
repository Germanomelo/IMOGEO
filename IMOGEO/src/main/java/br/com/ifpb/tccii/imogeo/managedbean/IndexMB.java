package br.com.ifpb.tccii.imogeo.managedbean;

import br.com.ifpb.tccii.imogeo.entidades.Endereco;
import br.com.ifpb.tccii.imogeo.entidades.Imovel;
import br.com.ifpb.tccii.imogeo.entidades.Usuario;
import br.com.ifpb.tccii.imogeo.entidades.especializacao.Apartamento;
import br.com.ifpb.tccii.imogeo.entidades.especializacao.Casa;
import br.com.ifpb.tccii.imogeo.sessionbeans.ApartamentoDao;
import br.com.ifpb.tccii.imogeo.sessionbeans.CasaDao;
import br.com.ifpb.tccii.imogeo.sessionbeans.ImovelDao;
import br.com.ifpb.tccii.imogeo.sessionbeans.UsuarioDao;
import java.io.Serializable;
import java.util.List;
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
    
    private String tituloH3 = "Imóveis";
    private String finalidade = null;
    private boolean exibeTodosImoveis = true;
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
    private CasaDao casaDao;

    @EJB
    private ImovelDao imovelDao;

    @EJB
    private ApartamentoDao aptoDao;

    public IndexMB() {
    }

    public void buscarUsuario() {
        this.user = userDao.listarUsuarioPorImovel(this.imovel);
    }

    public List<Imovel> buscaAvançada() {
        return null;
    }

    public List<Imovel> buscaSimplesDescricao() {
        return imovelDao.listarSimplesDescricao(busca);
    }

    public List<Apartamento> listarApartamentosAnunciados() {
        return aptoDao.listarApartamentosAnunciados();
    }

    public List<Casa> listarCasasAnunciadas() {
        return casaDao.listarCasasAnunciadas();
    }

    public List<Imovel> listarImoveisAnunciados() {
        return imovelDao.listarImoveisAnunciados();
    }

    public List listarImoveisFinalidadeAnunciados() {
        return imovelDao.listarImoveisFinalidadeAnunciados(finalidade);
    }

    public void detalhesImovel() {
        System.out.println("\n Entrou detalhes imovel!!!!!!!!!!!!!!!!");
        if (this.imovel instanceof Casa) {
            this.detalhesCasa();
        } else if (this.imovel instanceof Apartamento) {
            this.detalhesApto();
        } else {
            this.mensagemErro(null, "Erro ao ver informações de Imovél, tente novamente");
        }
    }

    public void detalhesCasa() {
        System.out.println("\n Entrou Detalhes casa!!!!!!!!!!!!!!!!!");
        this.casa = casaDao.retornarCasa(this.imovel.getId());
        this.telaDetalhesCasa();
    }

    public void detalhesApto() {
        System.out.println("\n Entrou detalhes apto!!!!!!!!!!!!!!!");
        this.apto = aptoDao.retornarApartamento(this.imovel.getId());
        this.telaDetalhesApto();
    }

    public void telaDetalhesApto() {
        System.out.println("\n Entrou tela detalhes apto");
        this.exibeDetalhesApto = true;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = false;
        this.tituloH3 ="Apartamento";
        this.lat = this.apto.getEndereco().getLocalizacao().getCoordinate().x;
        this.log = this.apto.getEndereco().getLocalizacao().getCoordinate().y;
    }

    public void telaDetalhesCasa() {
        System.out.println("\nEntrou tela detalhes casa!!!!!!!!!!!!!!!!!");
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = true;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = false;
        this.tituloH3 ="Casa";
        this.lat = this.casa.getEndereco().getLocalizacao().getCoordinate().x;
        this.log = this.casa.getEndereco().getLocalizacao().getCoordinate().y;
    }

    public void telaExibebuscaSimples() {
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = true;
        this.tituloH3 ="Busca Simples";
    }

    public void telaExibeTodosImoveis() {
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = true;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = false;
        this.tituloH3 ="Imóveis";
    }

    public void telaExibeCasas() {
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = true;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = false;
        this.tituloH3 ="Casas";
    }

    public void telaExibeAptos() {
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = true;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = false;
        this.tituloH3 ="Apartamentos";
    }

    public void telaExibeImovelTemporada() {
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = true;
        this.exibebuscaSimples = false;
        this.finalidade = "TEMPORADA";
        this.tituloH3 ="Temporada";
    }

    public void telaExibeImovelComprar() {
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = true;
        this.exibebuscaSimples = false;
        this.finalidade = "VENDER";
        this.tituloH3 ="Comprar ";
    }

    public void telaExibeImovelAlugar() {
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = true;
        this.exibebuscaSimples = false;
        this.finalidade = "ALUGAR";
        this.tituloH3 ="Alugar";
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

    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    public Apartamento getApto() {
        return apto;
    }

    public void setApto(Apartamento apto) {
        this.apto = apto;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public boolean isExibeTodosImoveis() {
        return exibeTodosImoveis;
    }

    public void setExibeTodosImoveis(boolean exibeTodosImoveis) {
        this.exibeTodosImoveis = exibeTodosImoveis;
    }

    public boolean isExibeCasas() {
        return exibeCasas;
    }

    public boolean isExibeAptos() {
        return exibeAptos;
    }

    public String getBusca() {
        return busca;
    }

    public void setBusca(String busca) {
        this.busca = busca;
    }

    public String getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(String finalidade) {
        this.finalidade = finalidade;
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

    public String getTituloH3() {
        return tituloH3;
    }

    public void setTituloH3(String tituloH3) {
        this.tituloH3 = tituloH3;
    }
    
    
     public void mensagemInformativa(String destino, String msg) {
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(msg);
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        fc.addMessage(destino, fm);
    }

    public void mensagemErro(String destino, String msg) {
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(msg);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        fc.addMessage(destino, fm);
    }

}
