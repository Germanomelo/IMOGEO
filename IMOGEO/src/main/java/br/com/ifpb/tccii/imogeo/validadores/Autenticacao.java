/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifpb.tccii.imogeo.validadores;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Germano
 */
public class Autenticacao implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();

        //pegar objeto da sessão
        Object usuario = session.getAttribute("usuario");
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        String page = event.getFacesContext().getViewRoot().getViewId();
        //verificar as paginas de acesso
        if (usuario == null && !page.equals("/index.xhtml")
                && !page.equals("/cadastro-login.xhtml") 
                && !page.equals("/imagem.xhtml")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("cadastro-login.jsf");
            } catch (IOException ex) {
                Logger.getLogger(Autenticacao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
