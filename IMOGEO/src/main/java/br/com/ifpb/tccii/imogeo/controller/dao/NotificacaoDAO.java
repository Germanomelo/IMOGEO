/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifpb.tccii.imogeo.controller.dao;

import br.com.ifpb.tccii.imogeo.model.Notificacao;
import br.com.ifpb.tccii.imogeo.model.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author germano
 */
@Stateless
public class NotificacaoDAO {
    
@PersistenceContext(unitName = "IMOGEOPU-JTA")
EntityManager manager;


public Notificacao retornarNotificacao(Long id) {
        return manager.find(Notificacao.class, id);
    }

    public List<Notificacao> listarNotificacaes() {
        Query query = manager.createQuery("Select i from Imagem i");
        return query.getResultList();
    }

    public List<Notificacao> listarNotificacaoes(String consulta) {
        Query query = manager.createQuery(consulta);
        return query.getResultList();
    }
    
        public List<Notificacao> listarNotificacaoesPorUsuario(Usuario user) {
        Query query = manager.createQuery("SELECT n FROM Notificacao n where n.usuario = :id");
        query.setParameter("id", user.getId());
        List<Notificacao> notificacoes = query.getResultList();
        return notificacoes;
    }
    
    public void inserirNotificacao(Notificacao notificacao) {
        manager.persist(notificacao);
    }

    public void atualizarNotificacao(Notificacao notificacao) {
        manager.merge(notificacao);
    }

    public void removerNotificacao(Notificacao notificacao) {
        manager.remove(notificacao);
    }
}
