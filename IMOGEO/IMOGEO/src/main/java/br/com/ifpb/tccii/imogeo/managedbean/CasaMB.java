package br.com.ifpb.tccii.imogeo.managedbean;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import br.com.ifpb.tccii.imogeo.entidades.Anuncio;
import br.com.ifpb.tccii.imogeo.entidades.Endereco;
import br.com.ifpb.tccii.imogeo.entidades.Usuario;
import br.com.ifpb.tccii.imogeo.entidades.especializacao.Casa;
import br.com.ifpb.tccii.imogeo.sessionbeans.CasaDao;
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
@ManagedBean(name="casaMB")
@ViewScoped
public class CasaMB implements Serializable{    

    private String loc;
    private boolean cadastrarCasa = true;
    private boolean cadastrarEnderecoCasa = false;
    
    private Casa casa = new Casa();
    private Endereco endereco = new Endereco();
    
    @EJB
    private CasaDao casaDao;
    
    public boolean isCadastrarCasa() {
        return cadastrarCasa;
    }
   
    public void telaCadastrarCasa(){
        this.cadastrarCasa = true;
        this.cadastrarEnderecoCasa = false;
    }
    
    public void telaCadastrarEnderecoCasa(){
        this.cadastrarCasa = false;
        this.cadastrarEnderecoCasa = true;
        
    }
   
    public String addCasa() throws ParseException{
        this.casa.setUsuario(this.getUsuarioSession());
        this.casa.setAnuncio(new Anuncio());
        Geometry g1 = new WKTReader().read(this.loc.toString());
        this.endereco.setLocalizacao((Point) g1);
        this.casa.setEndereco(this.endereco);
        this.casaDao.inserirCasa(this.casa);
        return "meus-imoveis.jsf";
    }
    
    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    public boolean isCadastrarEnderecoCasa() {
        return cadastrarEnderecoCasa;
    }

    public void setCadastrarEnderecoCasa(boolean cadastrarEnderecoCasa) {
        this.cadastrarEnderecoCasa = cadastrarEnderecoCasa;
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
}
