/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifpb.tccii.imogeo.managedbean;

import br.com.ifpb.tccii.imogeo.entidades.Comentario;
import br.com.ifpb.tccii.imogeo.entidades.Endereco;
import br.com.ifpb.tccii.imogeo.entidades.Imagem;
import br.com.ifpb.tccii.imogeo.entidades.Imovel;
import br.com.ifpb.tccii.imogeo.entidades.Usuario;
import br.com.ifpb.tccii.imogeo.entidades.especializacao.Apartamento;
import br.com.ifpb.tccii.imogeo.entidades.especializacao.Casa;
import br.com.ifpb.tccii.imogeo.sessionbeans.ApartamentoDao;
import br.com.ifpb.tccii.imogeo.sessionbeans.CasaDao;
import br.com.ifpb.tccii.imogeo.sessionbeans.ComentarioDao;
import br.com.ifpb.tccii.imogeo.sessionbeans.ImagemDao;
import br.com.ifpb.tccii.imogeo.sessionbeans.ImovelDao;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Germano
 */
@ManagedBean(name = "meusImoveisMB")
@ViewScoped
public class MeusImoveisMB implements Serializable {

    private String loc;
    private String comentarioTxt;
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

    private Usuario userSession = new Usuario();
    private Comentario comentario = new Comentario();
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
    private ComentarioDao comentarioDao;
//    @EJB
//    private ImagemDao imagemDao;

    public MeusImoveisMB() {
        this.CapturaUsuarioSession();
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

    public void telaEditarEnderecoCasa() {
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

    public void telaEditarEnderecoApto() {
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

    public void telaExcluirImovelPemissaoTrue() {
        this.excluirImovelPermission = true;
    }

    public void telaExcluirImovelPemissaoFalse() {
        this.excluirImovelPermission = false;
    }
//    ----------------------------------------Imovel--------------------------

    public List<Imovel> getImoveis() {
        return imovelDao.listarImoveis();
    }

    public List<Imovel> getImoveisIdUser() {
        return imovelDao.listarImoveisIdUser(userSession);
    }

    public void removerImovel() {
        if (informacoesCasa) {
            removerCasa();
        } else if (informacoesApto) {
            removerApto();
        } else {
            this.mensagemErro("Erro!", "Erro ao excluir Imóvel");
        }

    }
    public void atualizarEndereco() throws ParseException {
        if (editarEnderecoCasa) {
            this.atualizarCasaEndereco();
        } else if (editarEnderecoApto) {
            this.atualizarAptoEndereco();
        } else {
            this.mensagemErro("Erro!", "Erro ao editar Imóvel");
        }
    }

    public void maisInformacoes() {
        if (this.imovel instanceof Casa) {
            this.casa = casaDao.retornarCasa(this.imovel.getId());
            this.telaInformacoesCasa();
        } else if (this.imovel instanceof Apartamento) {
            this.apto = aptoDao.retornarApartamento(this.imovel.getId());
            this.telaInformacoesApto();
        } else {
            this.mensagemErro("Erro!", "Erro ao ver informações de Imovél, tente novamente");
        }

    }

    public void inserirComentario() {
        Comentario c = new Comentario();
        c.setComentario(this.comentarioTxt);
        c.setDataComentario(new Date());
        c.setUsuario(this.userSession);
        if (this.informacoesCasa) {
            c.setImovel(this.casa);
        } else if (this.informacoesApto) {
            c.setImovel(this.apto);
        }
        this.comentarioDao.inserirComentario(c);
        this.comentarioTxt = "";
    }

    public List<Comentario> listarComentarios() {
        List<Comentario> comentarios = new ArrayList<Comentario>();
        if (informacoesCasa) {
            comentarios = comentarioDao.listarComentariosIdImovel(this.casa);
        } else if (informacoesApto) {
            comentarios = comentarioDao.listarComentariosIdImovel(this.apto);
        }
        return comentarios;
    }

    public void removerComentario() {
        comentarioDao.removerComentario(this.comentario);
    }
//    ----------------------------------------Casa--------------------------    
    public Casa buscarCasaId() {
        return casaDao.retornarCasa(casa.getId());
    }

    public void atualizarCasa() {
        casaDao.atualizarCasa(casa);
        this.telaListarImoveis();
    }

    public void atualizarCasaEndereco() {
        Geometry g1;
        try {
            g1 = new WKTReader().read(this.loc.toString());
            this.endereco.setLocalizacao((Point) g1);
            this.casa.setEndereco(this.endereco);
            casaDao.atualizarCasa(casa);
            this.telaListarImoveis();
            this.mensagemInformativa("Sucesso!", "Casa atualizada com sucesso.");
        } catch (ParseException ex) {
            this.mensagemErro("Erro!", "Erro ao atualizar casa");
        }
    }

    public void removerCasa() {
        try {
            casaDao.removerCasa(casa);
            this.telaExcluirImovelPemissaoFalse();
            this.telaListarImoveis();
            this.mensagemInformativa("Sucesso", "Casa excluida com sucesso.");
        } catch (Exception e) {
            this.mensagemInformativa("Erro!", "Erro ao excluir casa.");
        }
    }

    public void anunciarCasa() {
        try {
            this.casa.getAnuncio().setAnunciado(true);
            this.casa.getAnuncio().setDataPublicacao(new Date());
            this.atualizarCasa();
            this.mensagemInformativa("Sucesso!", "Casa anunciada com sucesso.");
        } catch (Exception e) {
            this.mensagemErro("Erro!", "Erro ao anunciar casa");
        }
    }

    public void desanunciarCasa() {
        try {
            this.casa.getAnuncio().setAnunciado(false);
            this.atualizarCasa();
            this.mensagemInformativa("Sucesso!", "Casa desanunciada com sucesso.");
        } catch (Exception e) {
            this.mensagemErro("Erro!", "Erro ao desanunciar casa");
        }
    }

//    ----------------------------------------Apto--------------------------
    public Apartamento buscarAptoId() {
        return aptoDao.retornarApartamento(apto.getId());
    }

    public void atualizarApto() {
        aptoDao.atualizarApartamento(apto);
        this.telaListarImoveis();
    }

    public void atualizarAptoEndereco() throws ParseException {
        Geometry g1;
        try {
            g1 = new WKTReader().read(this.loc.toString());
            this.endereco.setLocalizacao((Point) g1);
            this.apto.setEndereco(this.endereco);
            aptoDao.atualizarApartamento(apto);
            this.telaListarImoveis();
            this.mensagemInformativa("Sucesso!", "Apartamento atualizado com sucesso.");
        } catch (ParseException ex) {
            this.mensagemErro("Erro!", "Erro ao atualizar apartamento");
        }
    }

    public void removerApto() {
        try {
            aptoDao.removerApartamento(apto);
            this.telaListarImoveis();
            this.mensagemInformativa("Sucesso", "Apartamento excluido com sucesso.");
        } catch (Exception e) {
            this.mensagemInformativa("Erro!", "Erro ao excluir apartamento.");
        }
    }

    public void anunciarApto() {
        try {
            this.apto.getAnuncio().setAnunciado(true);
            this.apto.getAnuncio().setDataPublicacao(new Date());
            this.atualizarApto();
            this.mensagemInformativa("Sucesso!", "Apartamento anunciado com sucesso.");
        } catch (Exception e) {
            this.mensagemErro("Erro!", "Erro ao anunciar apartamento");
        }
    }

    public void desanunciarApto() {
        try {
            this.apto.getAnuncio().setAnunciado(false);
            this.atualizarApto();
            this.mensagemInformativa("Sucesso!", "Apartamento desanunciada com sucesso.");
        } catch (Exception e) {
            this.mensagemErro("Erro!", "Erro ao desanunciar aparteamento");
        }
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

    public Usuario getUserSession() {
        return userSession;
    }

    public void setUserSession(Usuario userSession) {
        this.userSession = userSession;
    }

    public void CapturaUsuarioSession() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        this.userSession = (Usuario) session.getAttribute("usuario");
    }

    public String getComentarioTxt() {
        return comentarioTxt;
    }

    public void setComentarioTxt(String comentarioTxt) {
        this.comentarioTxt = comentarioTxt;
    }

    public Comentario getComentario() {
        return comentario;
    }

    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
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
    public void destroyWorld(ActionEvent actionEvent) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "System Error", "Please try again later.");

        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
