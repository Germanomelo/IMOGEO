/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.ifpb.tccii.imogeo.managedbean;

import br.com.ifpb.tccii.imogeo.entidades.Endereco;
import br.com.ifpb.tccii.imogeo.entidades.Imagem;
import br.com.ifpb.tccii.imogeo.entidades.Imovel;
import br.com.ifpb.tccii.imogeo.entidades.Usuario;
import br.com.ifpb.tccii.imogeo.entidades.especializacao.Apartamento;
import br.com.ifpb.tccii.imogeo.entidades.especializacao.Casa;
import br.com.ifpb.tccii.imogeo.sessionbeans.ApartamentoDao;
import br.com.ifpb.tccii.imogeo.sessionbeans.CasaDao;
import br.com.ifpb.tccii.imogeo.sessionbeans.ImagemDao;
import br.com.ifpb.tccii.imogeo.sessionbeans.ImovelDao;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mano
 */
//@Named(value = "meusImoveisMB")
@ManagedBean
@ViewScoped
public class MeusImoveisMB implements Serializable {

    private String loc;
    private double lat;
    private double log;
    
    
    private boolean excluirImovelPermission = false;
    private boolean listarImoveis = true;
    private boolean informacoesCasa = false;
    private boolean informacoesApto = false;
    private boolean editarCasa = false;
    private boolean editarApartamento = false;
    private boolean imagemApartamento = false;
    private boolean imagemCasa = false;
    private boolean editarEndereco = false;
    private boolean editarEnderecoCasa = false;
    private boolean editarEnderecoApto = false;
    
    private Casa casa = new Casa();
    private Apartamento apto = new Apartamento();
    private Imovel imovel = new Imovel();
    private Imagem imagem = new Imagem();
    private Endereco endereco = new Endereco();
    
    @EJB
    private CasaDao casaDao;
    @EJB
    private ImovelDao imovelDao;
    @EJB
    private ApartamentoDao aptoDao;
    @EJB
    private ImagemDao imagemDao;

    public MeusImoveisMB() {
    }

    public void telaListarImoveis() {
        this.informacoesCasa = false;
        this.informacoesApto = false;
        this.listarImoveis = true;
        this.editarCasa = false;
        this.editarApartamento = false;
        this.imagemApartamento = false;
        this.imagemCasa = false;
        this.editarEnderecoCasa = false;
        this.excluirImovelPermission = false;
        this.editarEnderecoApto = false;
        this.editarEndereco = false;
    }

    public void telaEditarCasa() {
        this.informacoesCasa = false;
        this.informacoesApto = false;
        this.listarImoveis = false;
        this.editarCasa = true;
        this.editarApartamento = false;
        this.imagemApartamento = false;
        this.imagemCasa = false;
        this.editarEnderecoCasa = false;
        this.editarEnderecoApto = false;
        this.editarEndereco = false;
        this.excluirImovelPermission = false;
    }
    
    public void telaEditarEnderecoCasa(){
        this.endereco = this.casa.getEndereco();
        this.informacoesCasa = false;
        this.informacoesApto = false;
        this.listarImoveis = false;
        this.editarCasa = false;
        this.editarApartamento = false;
        this.imagemApartamento = false;
        this.imagemCasa = false;
        this.editarEnderecoCasa = true;
        this.editarEnderecoApto = false;
        this.editarEndereco = true;
        this.excluirImovelPermission = false;
    }
    public void telaEditarEnderecoApto(){
        this.endereco = this.apto.getEndereco();
        this.informacoesCasa = false;
        this.informacoesApto = false;
        this.listarImoveis = false;
        this.editarCasa = false;
        this.editarApartamento = false;
        this.imagemApartamento = false;
        this.imagemCasa = false;
        this.editarEnderecoCasa = false;
        this.editarEnderecoApto = true;
        this.editarEndereco = true;
        this.excluirImovelPermission = false;
    }

    public void telaEditarApartamento() {
        this.informacoesCasa = false;
        this.informacoesApto = false;
        this.listarImoveis = false;
        this.editarCasa = false;
        this.editarApartamento = true;
        this.imagemApartamento = false;
        this.imagemCasa = false;
         this.editarEnderecoCasa = false;
        this.editarEnderecoApto = false;
        this.editarEndereco = false;
         this.excluirImovelPermission = false;
    }

    public void telaImagemCasa() {
        this.informacoesCasa = false;
        this.informacoesApto = false;
        this.listarImoveis = false;
        this.editarCasa = false;
        this.editarApartamento = false;
        this.imagemApartamento = false;
        this.imagemCasa = true;
        this.editarEnderecoApto = false;
        this.editarEndereco = false;
        this.editarEnderecoCasa = false;
        this.excluirImovelPermission = false;
    }

    public void telaInformacoesCasa() {
        this.informacoesCasa = true;
        this.informacoesApto = false;
        this.listarImoveis = false;
        this.editarCasa = false;
        this.editarEnderecoApto = false;
        this.editarEndereco = false;
        this.editarApartamento = false;
        this.imagemApartamento = false;
        this.imagemCasa = false;
        this.editarEnderecoCasa = false;
        this.excluirImovelPermission = false;
        this.lat = this.casa.getEndereco().getLocalizacao().getCoordinate().x;
        this.log = this.casa.getEndereco().getLocalizacao().getCoordinate().y;
    }

    public void telaInformacoesApto() {
        this.informacoesCasa = false;
        this.informacoesApto = true;
        this.listarImoveis = false;
        this.editarCasa = false;
        this.editarEnderecoApto = false;
        this.editarEndereco = false;
        this.editarApartamento = false;
        this.imagemApartamento = false;
        this.imagemCasa = false;
        this.editarEnderecoCasa = false;
        this.excluirImovelPermission = false;
        this.lat = this.apto.getEndereco().getLocalizacao().getCoordinate().x;
        this.log = this.apto.getEndereco().getLocalizacao().getCoordinate().y;
    }

    public void telaExcluirImovelPemissaoTrue(){
        this.excluirImovelPermission = true;
    }
    
    public void telaExcluirImovelPemissaoFalse(){
        this.excluirImovelPermission = false;
    }
//    ----------------------------------------Imovel--------------------------
    public List<Imovel> getImoveis() {
        return imovelDao.listarImoveis();
    }

    public List<Imovel> getImoveisIdUser() {
        Usuario user = getUsuarioSession();
        return imovelDao.listarImoveisIdUser(user);
    }

    public void removerImovel() {
        if(informacoesCasa){
            removerCasa();
        }else if (informacoesApto){
            removerApto();
        }else{
            this.mensagemErro(null, "Erro ao excluir Imóvel");
        }
            
    }

    public void atualizarEndereco() throws ParseException{
        if(editarEnderecoCasa){
            this.atualizarCasaEndereco();
        } else if(editarEnderecoApto){
            this.atualizarAptoEndereco();
        }else{
            this.mensagemErro(null, "Erro ao editar Imóvel");
        }
    }
    public void editarImovel() {
        if (this.imovel instanceof Casa) {
            this.casa.setId(this.imovel.getId());
            this.casa = this.buscarCasaId();
            this.telaEditarCasa();
        } else if (this.imovel instanceof Apartamento) {
            this.apto.setId(this.imovel.getId());
            this.apto = this.buscarAptoId();
            this.telaEditarApartamento();
        } else {
            this.mensagemErro(null, "Erro ao editar Imóvel");
        }
    }

    public void maisInformacoes() {
        if (this.imovel instanceof Casa) {
            this.casa = casaDao.retornarCasa(this.imovel.getId());
            System.out.println(this.casa);
            this.telaInformacoesCasa();
        } else if (this.imovel instanceof Apartamento) {
            this.apto = aptoDao.retornarApartamento(this.imovel.getId());
            this.telaInformacoesApto();
        } else {
            this.mensagemErro(null, "Erro ao ver informações de Imovél, tente novamente");
        }

    }

    public void desanunciarImovel() {
        if (this.imovel instanceof Casa) {
            Casa c = (Casa) this.imovel;
            this.casa = c;
            this.desanunciarCasa();
        } else if (this.imovel instanceof Apartamento) {
            Apartamento ap = (Apartamento) this.imovel;
            this.apto = ap;
            this.apto.getAnuncio().setAnunciado(false);
            this.atualizarApto();
        } else {
            this.mensagemErro(null, "Erro ao desanunciar Imovél, tente novamente");
        }
        this.telaListarImoveis();
    }

    public void anunciarImovel() {
        if (this.imovel instanceof Casa) {
            Casa c = (Casa) this.imovel;
            this.casa = c;
            this.anunciarCasa();
        } else if (this.imovel instanceof Apartamento) {
            Apartamento ap = (Apartamento) this.imovel;
            this.apto = ap;
            this.apto.getAnuncio().setAnunciado(true);
            this.apto.getAnuncio().setDataPublicacao(new Date());
            this.atualizarApto();
        } else {
            this.mensagemErro(null, "Erro ao desanunciar Imovél, tente novamente");
        }
        this.telaListarImoveis();
    }
//    ----------------------------------------Casa--------------------------    

    public Casa buscarCasaId() {
        return casaDao.retornarCasa(casa.getId());
    }

    public void atualizarCasa() {
        casaDao.atualizarCasa(casa);
        this.telaListarImoveis();
        this.mensagemInformativa(null, "Casa atualizada com sucesso.");
    }
    
    public void atualizarCasaEndereco() throws ParseException {
        Geometry g1 = new WKTReader().read(this.loc.toString());
        this.endereco.setLocalizacao((Point) g1);
        this.casa.setEndereco(this.endereco);
        casaDao.atualizarCasa(casa);
        this.telaListarImoveis();
        this.mensagemInformativa(null, "Casa atualizada com sucesso.");
    }
    
    public void removerCasa() {
        casaDao.removerCasa(casa);
        this.telaExcluirImovelPemissaoFalse();
        this.telaListarImoveis();
        this.mensagemInformativa(null, "Casa excluida com sucesso.");
    }

    public void anunciarCasa() {
        this.casa.getAnuncio().setAnunciado(true);
        this.casa.getAnuncio().setDataPublicacao(new Date());
        this.atualizarCasa();
    }

    public void desanunciarCasa() {
        this.casa.getAnuncio().setAnunciado(false);
        this.atualizarCasa();
    }

//    ----------------------------------------Apto--------------------------
    public Apartamento buscarAptoId() {
        return aptoDao.retornarApartamento(apto.getId());
    }

    public void atualizarApto() {
        aptoDao.atualizarApartamento(apto);
        this.telaListarImoveis();
        this.mensagemInformativa(null, "Apartamento atualizado com sucesso.");
    }

     public void atualizarAptoEndereco() throws ParseException {
        Geometry g1 = new WKTReader().read(this.loc.toString());
        this.endereco.setLocalizacao((Point) g1);
        this.apto.setEndereco(this.endereco);
        aptoDao.atualizarApartamento(apto);
        this.telaListarImoveis();
        this.mensagemInformativa(null, "Apartamento atualizada com sucesso.");
    }
    
    public void removerApto() {
        aptoDao.removerApartamento(apto);
        this.telaListarImoveis();
        this.mensagemInformativa(null, "Apartamento excluido com sucesso.");
    }
    
     public void anunciarApto() {
        this.apto.getAnuncio().setAnunciado(true);
        this.apto.getAnuncio().setDataPublicacao(new Date());
        this.atualizarApto();
    }

    public void desanunciarApto() {
        this.apto.getAnuncio().setAnunciado(false);
        this.atualizarApto();
    }
//    <------------------------------------------------Get Set-------------->>>
    public boolean isListarImoveis() {
        return listarImoveis;
    }

    public void setListarImoveis(boolean listarImoveis) {
        this.listarImoveis = listarImoveis;
    }

    public boolean isEditarApartamento() {
        return editarApartamento;
    }

    public void setEditarApartamento(boolean editarApartamento) {
        this.editarApartamento = editarApartamento;
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

    public boolean isEditarCasa() {
        return editarCasa;
    }

    public void setEditarCasa(boolean editarCasa) {
        this.editarCasa = editarCasa;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public boolean isImagemApartamento() {
        return imagemApartamento;
    }

    public void setImagemApartamento(boolean imagemApartamento) {
        this.imagemApartamento = imagemApartamento;
    }

    public boolean isImagemCasa() {
        return imagemCasa;
    }

    public void setImagemCasa(boolean imagemCasa) {
        this.imagemCasa = imagemCasa;
    }

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    public boolean isInformacoesCasa() {
        return informacoesCasa;
    }

    public void setInformacoesCasa(boolean informacoesCasa) {
        this.informacoesCasa = informacoesCasa;
    }

    public boolean isInformacoesApto() {
        return informacoesApto;
    }

    public void setInformacoesApto(boolean informacoesApto) {
        this.informacoesApto = informacoesApto;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public boolean isEditarEnderecoCasa() {
        return editarEnderecoCasa;
    }

    public void setEditarEnderecoCasa(boolean enderecoCasa) {
        this.editarEnderecoCasa = enderecoCasa;
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
    
    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public boolean isEditarEndereco() {
        return editarEndereco;
    }

    public void setEditarEndereco(boolean editarEndereco) {
        this.editarEndereco = editarEndereco;
    }

    public boolean isEditarEnderecoApto() {
        return editarEnderecoApto;
    }

    public void setEditarEnderecoApto(boolean editarEnderecoApto) {
        this.editarEnderecoApto = editarEnderecoApto;
    }
    
    

    public boolean isExcluirImovelPermission() {
        return excluirImovelPermission;
    }

    public void setExcluirImovelPermission(boolean excluirImovelPermission) {
        this.excluirImovelPermission = excluirImovelPermission;
    }

    public Usuario getUsuarioSession() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        Usuario user = (Usuario) session.getAttribute("usuario");
        return user;
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

//    public void processFileUploadCasa(FileUploadEvent uploadEvent) {
//
//        try {
////            imagem.setImovel(casa);
//            imagem.setFoto(uploadEvent.getFile().getContents());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//    }
//    public void processFileUploadApartamento(FileUploadEvent uploadEvent) {
//
//        try {
////            imagem.setImovel(apto);
//            imagem.setFoto(uploadEvent.getFile().getContents());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//    }
//    public void inserirImagem() {
//        imagemDao.inserirImagem(imagem);
//        imagem = new Imagem();
//        mensagemInformativa(null, "Imagem adicionada com sucesso");
//    }
    //    public void addImagem(FileUploadEvent event) {
    //        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
    //        FacesContext.getCurrentInstance().addMessage(null, msg);
    //    }
    public void enderecoToString() {
        System.out.println(endereco.toString());
    }
    
    public void destroyWorld(ActionEvent actionEvent){  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "System Error",  "Please try again later.");  
          
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }  
}
