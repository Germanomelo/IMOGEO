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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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

    private String finalidade = null;
    private boolean exibeTodosImoveis = true;
    private boolean exibeCasa = false;
    private boolean exibeApto = false;
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

    public List<Apartamento> listarApartamento() {
        return aptoDao.listarApartamentos();
    }

    public List<Casa> listarCasa() {
        return casaDao.listarCasas();
    }

    public List<Imovel> listarImoveis() {
        return imovelDao.listarImoveis();
    }

    public List listarImovelFinalidade() {
        return imovelDao.listarImoveisFinalidade(finalidade);
    }

    public void detalhesImovel() {
        if (this.imovel instanceof Casa) {
            System.out.println("Entrou em casa");
            this.casa = casaDao.retornarCasa(this.imovel.getId());
            this.telaDetalhesCasa();
        } else if (this.imovel instanceof Apartamento) {
            System.out.println("Entrou em apartamento");
            this.apto = aptoDao.retornarApartamento(this.imovel.getId());
            this.telaDetalhesApto();
        }
//        buscarUsuario();

    }

    public void telaDetalhesApto() {
        System.out.println("Entrou tela detalhes apto");
        this.exibeDetalhesApto = true;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeApto = false;
        this.exibeCasa = false;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = false;
    }

    public void telaDetalhesCasa() {
        System.out.println("Entrou tela detalhes casa");
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = true;
        this.exibeTodosImoveis = false;
        this.exibeApto = false;
        this.exibeCasa = false;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = false;
    }

    public void telaExibebuscaSimples() {
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeApto = false;
        this.exibeCasa = false;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = true;
//        return "index.jsf";
    }

    public void telaExibeTodosImoveis() {
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = true;
        this.exibeApto = false;
        this.exibeCasa = false;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = false;
    }

    public void telaExibeCasa() {
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeApto = false;
        this.exibeCasa = true;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = false;
    }

    public void telaExibeApto() {
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeApto = true;
        this.exibeCasa = false;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = false;
    }

    public void telaExibeImovelTemporada() {
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeApto = false;
        this.exibeCasa = false;
        this.exibeImovelFinalidade = true;
        this.exibebuscaSimples = false;
        this.finalidade = "TEMPORADA";
    }

    public void telaExibeImovelComprar() {
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeApto = false;
        this.exibeCasa = false;
        this.exibeImovelFinalidade = true;
        this.exibebuscaSimples = false;
        this.finalidade = "COMPRAR";
    }

    public void telaExibeImovelAlugar() {
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeApto = false;
        this.exibeCasa = false;
        this.exibeImovelFinalidade = true;
        this.exibebuscaSimples = false;
        this.finalidade = "ALUGAR";
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

    public boolean isExibeCasa() {
        return exibeCasa;
    }

    public boolean isExibeApto() {
        return exibeApto;
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

}
