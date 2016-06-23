/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifpb.tccii.imogeo.sessionbeans;

import br.com.ifpb.tccii.imogeo.entidades.Imovel;
import br.com.ifpb.tccii.imogeo.entidades.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Germano
 */
@Stateless
public class UsuarioDao {

    @PersistenceContext(unitName = "IMOGEOPU-JTA")
    private EntityManager manager;

    public void inserirUsuario(Usuario usuario) {
        manager.persist(usuario);
    }

    public void atualizarUsuario(Usuario usuario) {
        manager.merge(usuario);
    }

    public void removerUsuario(Usuario usuario) {
        manager.remove(manager.getReference(Usuario.class, usuario.getId()));
    }

    public Usuario usuarioPorImovel(Imovel imovel) {
        Query query = manager.createQuery("Select u from Imovel i join i.usuario u where i.id = :id");
        query.setParameter("id", imovel.getId());
        Usuario user = (Usuario) query.getSingleResult();
        return user;
    }

    public Usuario loginUsuario(Usuario usuario) {
        Usuario usuarioRecuperado = null;
        Query query = manager.createQuery("SELECT u FROM Usuario u WHERE u.email=:email and u.senha =:senha");
        query.setParameter("email", usuario.getEmail());
        query.setParameter("senha", usuario.getSenha());
        usuarioRecuperado = (Usuario) query.getSingleResult();

        return usuarioRecuperado;
    }

    public void removerElemetoDaAssociacaoImoveisFavoritos(Usuario user, Imovel imovel) {
        Query query = manager.createQuery(
                "DELETE FROM imoveis_favoritos f WHERE f.user_id = :user_id and f.imovel_id = :imovel_id");
        int deletedCount = query
        .setParameter("user_id", user.getId())
        .setParameter("imovel_id", imovel.getId())
        .executeUpdate();
    }

//    public List<Imovel> listarFavoritosIdUser(Usuario usuario) {
////        Query query = manager.createQuery("Select i from Usuario u join u.favoritos i where u.user_id = :id");
//        Query query = manager.createQuery("Select f from imoveis_favoritos f where f.user_id = :id");
//        query.setParameter("id", usuario.getId());
//        List<Imovel> imoveis = query.getResultList();
//        return imoveis;
//    }
    
}
