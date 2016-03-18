package br.com.ifpb.tccii.imogeo.managedbean;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import br.com.ifpb.tccii.imogeo.entidades.Anuncio;
import br.com.ifpb.tccii.imogeo.entidades.Endereco;
import br.com.ifpb.tccii.imogeo.entidades.Usuario;
import br.com.ifpb.tccii.imogeo.entidades.especializacao.Apartamento;
import br.com.ifpb.tccii.imogeo.sessionbeans.ApartamentoDao;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import javax.faces.application.FacesMessage;  
import javax.faces.context.FacesContext;    
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Germano
 */
@ManagedBean(name="apartamentoMB")
@ViewScoped
public class ApartamentoMB implements Serializable{    

    private String loc;
    private boolean cadastrarApto = true;
    private boolean cadastrarEnderecoApto = false;
    
    private Apartamento apto = new Apartamento();
    private Endereco endereco = new Endereco();
    
    @EJB
    private ApartamentoDao aptoDao;
    
    public boolean isCadastrarApto() {
        return cadastrarApto;
    }
   
    public void telaCadastrarApto(){
        this.cadastrarApto = true;
        this.cadastrarEnderecoApto = false;
    }
    
    public void telaCadastrarEnderecoApto(){
        this.cadastrarApto = false;
        this.cadastrarEnderecoApto = true;
        
    }
   
    public String addApto() throws ParseException{
        try{
        Geometry g1 = new WKTReader().read(this.loc.toString());
        this.endereco.setLocalizacao((Point) g1);
        this.endereco.setImovel(this.apto);
        this.apto.setUsuario(this.getUsuarioSession());
        this.apto.setAnuncio(new Anuncio());
        this.apto.setEndereco(this.endereco);   
        this.aptoDao.inserirApartamento(this.apto);
              mensagemInformativa("Sucesso", "Apartamento cadastrada com sucesso.");
        return "meus-imoveis.jsf";
        }catch(Exception e){
            mensagemErro("Erro!", "Erro ao cadastrar apartamento");
        }
        return null;
    }

    public boolean isCadastrarEnderecoApto() {
        return cadastrarEnderecoApto;
    }

    public void setCadastrarEnderecoApto(boolean cadastrarEnderecoApto) {
        this.cadastrarEnderecoApto = cadastrarEnderecoApto;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public Apartamento getApto() {
        return apto;
    }

    public void setApto(Apartamento apto) {
        this.apto = apto;
    }
    
     public Usuario getUsuarioSession() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        Usuario user = (Usuario) session.getAttribute("usuario");
        return user;
    }
    
    public void addImagem(FileUploadEvent event) {  
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");  
        FacesContext.getCurrentInstance().addMessage(null, msg); 
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
