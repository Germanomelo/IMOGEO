/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.ifpb.tccii.imogeo.sessionbeans;

import br.com.ifpb.tccii.imogeo.entidades.Comentario;
import br.com.ifpb.tccii.imogeo.entidades.Imovel;
import br.com.ifpb.tccii.imogeo.entidades.Usuario;
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
public class ComentarioDao {
    
    @PersistenceContext(unitName = "IMOGEOPU-JTA")
    private EntityManager manager;
    
    public List<Comentario> listarComentarios() {
        Query query = manager.createQuery("SELECT c FROM Cometario c");
        List<Comentario> comentarios = query.getResultList();
        return comentarios;
    }
    
    public List<Comentario> listarComentariosIdUser(Usuario user) {
        Query query = manager.createQuery("SELECT c FROM Cometario c WHERE c.usuario_id = :id");
        query.setParameter("id", user.getId());
        List<Comentario> comentarios = query.getResultList();
        return comentarios;
    }
    
    public List<Comentario> listarComentariosIdImovel(Imovel imovel) {
//        Query query = manager.createQuery("SELECT i FROM Comentario c JOIN c.imovel i WHERE i.id = :id");
        Query query = manager.createQuery("SELECT c FROM Comentario c WHERE c.imovel.imovelId = :id");
        query.setParameter("id", imovel.getId());
        List<Comentario> comentarios = query.getResultList();
        return comentarios;
    }
    
    public void inserirComentario(Comentario coment) {
        manager.persist(coment);
    }

    public void atualizarComentario(Comentario coment) {
        manager.merge(coment);
    }

    public void removerComentario(Comentario coment) {
        manager.remove(manager.getReference(Comentario.class, coment.getId()));
    }
}
