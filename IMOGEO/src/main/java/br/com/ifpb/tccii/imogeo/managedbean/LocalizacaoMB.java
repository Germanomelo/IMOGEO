/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifpb.tccii.imogeo.managedbean;

import br.com.ifpb.tccii.imogeo.entidades.Endereco;
import br.com.ifpb.tccii.imogeo.entidades.Imovel;
import br.com.ifpb.tccii.imogeo.entidades.especializacao.Apartamento;
import br.com.ifpb.tccii.imogeo.entidades.especializacao.Casa;
import br.com.ifpb.tccii.imogeo.sessionbeans.ApartamentoDao;
import br.com.ifpb.tccii.imogeo.sessionbeans.CasaDao;
import br.com.ifpb.tccii.imogeo.sessionbeans.EnderecoDao;
import br.com.ifpb.tccii.imogeo.sessionbeans.ImovelDao;
import com.vividsolutions.jts.io.ParseException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Mano
 */
@ManagedBean
@ViewScoped
public class LocalizacaoMB implements Serializable {

    private String loc;
    private double lat;
    private double log;

    private int distancia = 5;
    private String tituloH3;
    private boolean pesquisaLocalizacao = true;
    private boolean resultLocalizacao = false;
    private boolean exibeDetalhesCasa = false;
    private boolean exibeDetalhesApto = false;

    private Imovel imovel = new Imovel();
//    private Endereco endereco = new Endereco();
    private Casa casa = new Casa();
    private Apartamento apto = new Apartamento();

    @EJB
    private EnderecoDao enderecoDao;

    @EJB
    private CasaDao casaDao;

    @EJB
    private ApartamentoDao aptoDao;

    public LocalizacaoMB() {
    }

    public List<Endereco> enderecoPorLocalizacao() throws ParseException {
        List<Endereco> result = new ArrayList<Endereco>();
        List<Endereco> enderecos = enderecoDao.listarEnderecosPorDistancia(loc, distancia);
        for (int i = 0; i < enderecos.size(); i++) {
            if (enderecos.get(i).getImovel().getAnuncio().getAnunciado() == true) {
                result.add(enderecos.get(i));
            }
        }
        return result;
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

    public void telaPesquisaLocalizacao() {
        this.pesquisaLocalizacao = true;
        this.resultLocalizacao = false;
        this.exibeDetalhesCasa = false;
        this.exibeDetalhesApto = false;

    }

    public void telaDetalhesCasa() {
        this.pesquisaLocalizacao = false;
        this.resultLocalizacao = false;
        this.exibeDetalhesCasa = true;
        this.exibeDetalhesApto = false;
        this.tituloH3 = "Casa";
        this.lat = this.casa.getEndereco().getLocalizacao().getCoordinate().x;
        this.log = this.casa.getEndereco().getLocalizacao().getCoordinate().y;
    }

    public void telaDetalhesApto() {
        this.pesquisaLocalizacao = false;
        this.resultLocalizacao = false;
        this.exibeDetalhesCasa = false;
        this.exibeDetalhesApto = true;
        this.tituloH3 = "Apartamento";
        this.lat = this.apto.getEndereco().getLocalizacao().getCoordinate().x;
        this.log = this.apto.getEndereco().getLocalizacao().getCoordinate().y;
    }

    public void telaResultLocalizacao() {
        this.pesquisaLocalizacao = false;
        this.resultLocalizacao = true;
        this.exibeDetalhesCasa = false;
        this.exibeDetalhesApto = false;
        this.tituloH3 = "Raio de atuação da busca: ";
        this.tituloH3 = this.tituloH3 + this.distancia + "Km";
    }

//    public Endereco getEndereco() {
//        return endereco;
//    }
//
//    public void setEndereco(Endereco endereco) {
//        this.endereco = endereco;
//    }
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

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public boolean isPesquisaLocalizacao() {
        return pesquisaLocalizacao;
    }

    public void setPesquisaLocalizacao(boolean pesquisaLocalizacao) {
        this.pesquisaLocalizacao = pesquisaLocalizacao;
    }

    public boolean isResultLocalizacao() {
        return resultLocalizacao;
    }

    public void setResultLocalizacao(boolean resultLocalizacao) {
        this.resultLocalizacao = resultLocalizacao;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public String getTituloH3() {
        return tituloH3;
    }

    public void setTituloH3(String tituloH3) {
        this.tituloH3 = tituloH3;
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
