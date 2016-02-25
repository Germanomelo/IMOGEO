/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifpb.tccii.imogeo.managedbean;

import br.com.ifpb.tccii.imogeo.criptografia.Criptografia;
import br.com.ifpb.tccii.imogeo.entidades.Usuario;
import br.com.ifpb.tccii.imogeo.sessionbeans.UsuarioDao;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mano
 */
//@Named(value = "usuarioMB")
@ManagedBean(name = "usuarioMB")
@SessionScoped
public class UsuarioMB implements Serializable {

    //telas
    private boolean perfilUsuario = true;
    private boolean editarPerfilUsuario = false;
    private boolean editarSenhaUsuario = false;
    private boolean excluirPerfilUsuario = false;
    private boolean ativo = false;
    //senhas
    private String senhaAtual = null;
    private String novaSenha = null;
    private String confirmeSenha = null;
    private Usuario usuario = new Usuario();
    @EJB
    private UsuarioDao users;

    public UsuarioMB() {
    }

    public String cadastarUsuario() {
        String pagina;
        Criptografia crip = new Criptografia();
        if (this.confirmeSenha.equals(this.usuario.getSenha())) {
            this.usuario.setSenha(crip.encriptar(this.usuario.getSenha()));
            System.out.println(usuario.getSenha());
            users.cadastrarUsuario(usuario);
            this.setAtivo(true);
//            }catch(Exception e){
//                this.mensagemErro("fromcadastrouser:email", "Erro: Usuario já existe");
//            }
            pagina = "minha-conta.jsf";
        } else {
            this.mensagemErro("fromcadastrouser:senha1", "Erro: Senhas não conferem");
            pagina = "cadastro-login.jsf";
        }
        return pagina;
    }

    public String login() {
        String paginaRetorno = "/cadastro-login.jsf";
        Usuario usuarioNovo = null;
        try {
            Criptografia crip = new Criptografia();
            this.usuario.setSenha(crip.encriptar(this.usuario.getSenha()));
            usuarioNovo = users.loginUsuarios(this.usuario);
        } catch (Exception e) {
            this.mensagemErro(null, "Erro: Usuário e/ou Senha Inválidos.");
        }
        if (usuarioNovo != null) {
            paginaRetorno = "/minha-conta.jsf";
            usuario = usuarioNovo;
            this.setAtivo(true);
        }
        return paginaRetorno;
    }

    public void atualizarUsuario() {
        users.atualizarUsuario(usuario);
        this.mensagemInformativa(null, "Editado com Sucesso.");
    }

    public String atualizarSenhaUsuario() {

        FacesContext fc = FacesContext.getCurrentInstance();
        if (this.novaSenha != null && (this.novaSenha.equals(this.confirmeSenha)) && this.senhaAtual.equals(this.usuario.getSenha())) {
            this.usuario.setSenha(novaSenha);
            users.atualizarUsuario(usuario);
            this.mensagemInformativa(null, "Editado com Sucesso.");
        } else {
            this.mensagemErro(null, "ERRO: senhas incompativeis");
        }
        return "/minha-conta.jsf";
    }

    public String minhaConta(){
        if(this.ativo){
            return "/minha-conta.jsf";
        }
        return "/cadastro-login.jsf";
    }
    public String removerUsuario() {
        users.removerUsuario(usuario);
        setAtivo(false);
        this.usuario = new Usuario();
        this.mensagemInformativa(null, "Excluido com Sucesso!");
        return "/index.jsf";
    }

    public String logout() {
        this.usuario = new Usuario();
        this.setAtivo(false);
        return "index.jsf";
    }

    // Telas ---------------------------->>>>>>>>
    public void telaPerfilUsuario() {
        this.perfilUsuario = true;
        this.editarPerfilUsuario = false;
        this.editarSenhaUsuario = false;
        this.excluirPerfilUsuario = false;
    }

    public void telaEditarPerfilUsuario() {
        this.perfilUsuario = false;
        this.editarPerfilUsuario = true;
        this.editarSenhaUsuario = false;
        this.excluirPerfilUsuario = false;
    }

    public void telaEditarSenhaUsuario() {
        this.perfilUsuario = false;
        this.editarPerfilUsuario = false;
        this.editarSenhaUsuario = true;
        this.excluirPerfilUsuario = false;
    }

    public void telaExcluirPerfilUsuario() {
        this.perfilUsuario = false;
        this.editarPerfilUsuario = false;
        this.editarSenhaUsuario = false;
        this.excluirPerfilUsuario = true;
    }

    // Get e Set ----------------------------->>>>>>>>
    public boolean isPerfilUsuario() {
        return perfilUsuario;
    }

    public void setPerfilUsuario(boolean perfilUsuario) {
        this.perfilUsuario = perfilUsuario;
    }

    public boolean isEditarPerfilUsuario() {
        return editarPerfilUsuario;
    }

    public void setEditarPerfilUsuario(boolean editarPerfilUsuario) {
        this.editarPerfilUsuario = editarPerfilUsuario;
    }

    public boolean isEditarSenhaUsuario() {
        return editarSenhaUsuario;
    }

    public void setEditarSenhaUsuario(boolean editarSenhaUsuario) {
        this.editarSenhaUsuario = editarSenhaUsuario;
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
        return excluirPerfilUsuario;
    }

    public void setExcluirPerfilUsuario(boolean excluirUsuario) {
        this.excluirPerfilUsuario = excluirUsuario;
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

    //Mensagens------------------------------>>>>>>>>>>>>>>>
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