/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifpb.tccii.imogeo.controller.managedbean;

import br.com.ifpb.tccii.imogeo.controller.criptografia.Criptografia;
import br.com.ifpb.tccii.imogeo.model.Imagem;
import br.com.ifpb.tccii.imogeo.model.Usuario;
import br.com.ifpb.tccii.imogeo.controller.dao.ImagemDAO;
import br.com.ifpb.tccii.imogeo.controller.dao.UsuarioDAO;
import br.com.ifpb.tccii.imogeo.controller.utils.ImagemManager;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Germano
 */
@ManagedBean(name = "usuarioMB")
@SessionScoped
public class UsuarioMB implements Serializable {

    //telas
    private boolean exibePerfilUsuario = false;
    private boolean exibeEditarPerfilUsuario = false;
    private boolean exibeEditarSenhaUsuario = false;
    private boolean exibeExcluirPerfilUsuario = false;
    private boolean ativo = false;
    //senhas
    private String senhaAtual = null;
    private String novaSenha = null;
    private String confirmeSenha = null;
    private Usuario usuario = new Usuario();
    private Criptografia crip = new Criptografia();
    private Imagem imagem;
    @EJB
    UsuarioDAO usuarioDao;
    @EJB
    ImagemDAO imagemDao;

    public UsuarioMB() {
        this.telaPerfilUsuario();

    }

    public String cadastarUsuario() {
        String pagina = "cadastro-login.jsf";
        if (this.confirmeSenha.equals(this.usuario.getSenha())) {
            try {
                this.usuario.setSenha(this.crip.encriptar(this.usuario.getSenha()));
                this.usuarioDao.inserirUsuario(this.usuario);
                this.setAtivo(true);
                pagina = "minha-conta.jsf";
            } catch (Exception e) {
                this.mensagemErro("Erro: ", e.getMessage());
            }
        } else {
            this.mensagemAlerta("Alerta!", "Senhas não compativeis");
            pagina = "cadastro-login.jsf";
        }
        return pagina;
    }

    public String login() {
        String paginaRetorno = "/cadastro-login.jsf";
        Usuario usuarioNovo = null;
        try {
            this.usuario.setSenha(crip.encriptar(this.usuario.getSenha()));
            usuarioNovo = usuarioDao.loginUsuario(this.usuario);
        } catch (Exception e) {
            this.mensagemAlerta("Alerta!", "Usuário e/ou Senha Inválidos.");
        }
        if (usuarioNovo != null) {
            paginaRetorno = "/minha-conta.jsf";
            usuario = usuarioNovo;
            this.setAtivo(true);
        }
        return paginaRetorno;
    }

    public void atualizarUsuario() {
        try {
            usuarioDao.atualizarUsuario(usuario);
        } catch (Exception e) {
            this.mensagemErro("Erro!", "Erro ao atualizar usuário.");
        }
        this.mensagemInformativa("Sucesso!", "Usuário atualizado com Sucesso.");
        this.telaPerfilUsuario();
    }

    public void atualizarSenhaUsuario() {
        if (this.novaSenha != null && (this.novaSenha.equals(this.confirmeSenha)) && this.crip.encriptar(this.senhaAtual).equals(this.usuario.getSenha())) {
            this.usuario.setSenha(this.crip.encriptar(novaSenha));
            try {
                usuarioDao.atualizarUsuario(usuario);
            } catch (Exception e) {
                this.mensagemErro("Erro!", e.getMessage());
            }
            this.mensagemInformativa("Sucesso!", "Senha atulizada com Sucesso.");
            this.telaPerfilUsuario();
        } else {
            this.mensagemAlerta("Alerta!", "Senhas incompativeis");
        }
    }

    public String minhaConta() {
        if (this.ativo) {
            return "/minha-conta.jsf";
        }
        return "/cadastro-login.jsf";
    }

    public String removerUsuario() {
        try {
            usuarioDao.removerUsuario(usuario);
            setAtivo(false);
            this.usuario = new Usuario();
            this.mensagemInformativa("Sucesso!", "Usuário excluido com sucesso.");
            return "/index.jsf";
        } catch (Exception e) {
            this.mensagemErro("Erro!", e.getMessage());
        }
        return null;

    }

    public String logout() {
        this.usuario = new Usuario();
        this.setAtivo(false);
        return "index.jsf";
    }

    // Telas ---------------------------->>>>>>>>
    public void telaFavoritos() {
        this.exibePerfilUsuario = false;
        this.exibeEditarPerfilUsuario = false;
        this.exibeEditarSenhaUsuario = false;
        this.exibeExcluirPerfilUsuario = false;
    }

    public void telaPerfilUsuario() {
        this.exibePerfilUsuario = true;
        this.exibeEditarPerfilUsuario = false;
        this.exibeEditarSenhaUsuario = false;
        this.exibeExcluirPerfilUsuario = false;

    }

    public void telaEditarPerfilUsuario() {
        this.exibePerfilUsuario = false;
        this.exibeEditarPerfilUsuario = true;
        this.exibeEditarSenhaUsuario = false;
        this.exibeExcluirPerfilUsuario = false;
    }

    public void telaEditarSenhaUsuario() {
        this.exibePerfilUsuario = false;
        this.exibeEditarPerfilUsuario = false;
        this.exibeEditarSenhaUsuario = true;
        this.exibeExcluirPerfilUsuario = false;
    }

    public void telaExcluirPerfilUsuario() {
        this.exibePerfilUsuario = false;
        this.exibeEditarPerfilUsuario = false;
        this.exibeEditarSenhaUsuario = false;
        this.exibeExcluirPerfilUsuario = true;
    }

    // Get e Set ----------------------------->>>>>>>>
    public boolean isExibeEditarPerfilUsuario() {
        return exibeEditarPerfilUsuario;
    }

    public void setExibeEditarPerfilUsuario(boolean exibeEditarPerfilUsuario) {
        this.exibeEditarPerfilUsuario = exibeEditarPerfilUsuario;
    }

    public boolean isExibeExcluirPerfilUsuario() {
        return exibeExcluirPerfilUsuario;
    }

    public void setExibeExcluirPerfilUsuario(boolean ExibeExcluirPerfilUsuario) {
        this.exibeExcluirPerfilUsuario = ExibeExcluirPerfilUsuario;
    }

    public boolean isExibePerfilUsuario() {
        return exibePerfilUsuario;
    }

    public void setExibePerfilUsuario(boolean exibePerfilUsuario) {
        this.exibePerfilUsuario = exibePerfilUsuario;
    }

    public boolean isEditarPerfilUsuario() {
        return exibeEditarPerfilUsuario;
    }

    public void setEditarPerfilUsuario(boolean editarPerfilUsuario) {
        this.exibeEditarPerfilUsuario = editarPerfilUsuario;
    }

    public boolean isExibeEditarSenhaUsuario() {
        return exibeEditarSenhaUsuario;
    }

    public void setExibeEditarSenhaUsuario(boolean exibeEditarSenhaUsuario) {
        this.exibeEditarSenhaUsuario = exibeEditarSenhaUsuario;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getConfirmeSenha() {
        return confirmeSenha;
    }

    public void setConfirmeSenha(String confirmeSenha) {
        this.confirmeSenha = confirmeSenha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isExcluirPerfilUsuario() {
        return exibeExcluirPerfilUsuario;
    }

    public void setExcluirPerfilUsuario(boolean excluirUsuario) {
        this.exibeExcluirPerfilUsuario = excluirUsuario;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if (ativo) {
            session.setAttribute("usuario", this.usuario);
        } else {
//            session.removeValue("usuario");
            session.invalidate();
        }
    }

    //Tratando imagem-----------------------------------------
    public String getFotoExibir() throws IOException {
        ImagemManager imagemManager = new ImagemManager();
        if (imagem != null && imagem.getNome() != null) {
            imagemManager.criarImagens(null, imagem);
            return "/temp/" + imagem.getNome();

        } else if (usuario.getImagem() != null && usuario.getImagem().getNome() != null) {
            imagem = usuario.getImagem();
            imagemManager.criarImagens(null, imagem);
            return "/temp/" + imagem.getNome();
        }

        return "/img/foto_user.png";
    }

    public void fileUpload(FileUploadEvent event) {

        UploadedFile arq = event.getFile();
        byte[] bimagem = event.getFile().getContents();

        ImagemManager imagemManager = new ImagemManager();

        if (imagem == null) {
            imagem = new Imagem();
        }

        imagem.setFoto(bimagem);
        imagem.setNome(imagemManager.nomeImagem(usuario, arq));

        try {
            if (usuario.getImagem() == null || usuario.getImagem().getFoto() == null) {
                usuario.setImagem(imagem);
                imagemDao.inserirImagem(imagem);
                usuarioDao.atualizarUsuario(usuario);
                mensagemInformativa("Sucesso!", "Imagem enviada com sucesso!");
            } else {
                imagem.setId(usuario.getImagem().getId());
                usuario.setImagem(imagem);
                imagemDao.atualizarImagem(imagem);
                mensagemInformativa("Sucesso!", "Imagem enviada com sucesso!");
            }
            telaPerfilUsuario();
        } catch (Exception e) {
            this.mensagemErro("Erro!", e.getMessage());
        }



    }

    //Mensagens------------------------------>>>>>>>>>>>>>>>
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
