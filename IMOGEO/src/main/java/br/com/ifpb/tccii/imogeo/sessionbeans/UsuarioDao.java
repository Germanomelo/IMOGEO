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
 * @author Mano
 */
@Stateless
//@RolesAllowed(value = "usuarioDao") 
public class UsuarioDao {

    @PersistenceContext(unitName = "IMOGEOPU-JTA")
    private EntityManager manager;

    public void cadastrarUsuario(Usuario usuario) {
        manager.persist(usuario);
    }

    public void atualizarUsuario(Usuario usuario) {
        manager.merge(usuario);
    }
    
      public Usuario listarUsuarioPorImovel(Imovel imovel) {
        Query query = manager.createQuery("Select u from Imovel i join i.usuario u where i.id = :id");
        query.setParameter("id", imovel.getId());
        Usuario user = (Usuario) query.getSingleResult();
        return user;
    }

    public Usuario loginUsuarios(Usuario usuario) {
        Usuario usuarioRecuperado = null;
            Query query = manager.createQuery("SELECT u FROM Usuario u WHERE u.email=:email and u.senha =:senha");
            query.setParameter("email", usuario.getEmail());
            query.setParameter("senha", usuario.getSenha());
            usuarioRecuperado = (Usuario) query.getSingleResult();
        
        return usuarioRecuperado;
    }
    
    public void removerUsuario(Usuario usuario) {        
        manager.remove(manager.getReference(Usuario.class, usuario.getId()));
    }
}
