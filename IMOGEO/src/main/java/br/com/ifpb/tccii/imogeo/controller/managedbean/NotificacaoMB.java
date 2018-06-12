/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifpb.tccii.imogeo.controller.managedbean;

import br.com.ifpb.tccii.imogeo.controller.dao.NotificacaoDAO;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author germano
 */
@ManagedBean(name = "notificacaoMB")
@SessionScoped
public class NotificacaoMB {
    
    @EJB
    NotificacaoDAO notificacaoDAO;
}
