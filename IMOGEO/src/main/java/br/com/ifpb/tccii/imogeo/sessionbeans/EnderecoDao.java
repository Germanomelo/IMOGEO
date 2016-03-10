package br.com.ifpb.tccii.imogeo.sessionbeans;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import br.com.ifpb.tccii.imogeo.entidades.Endereco;
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
public class EnderecoDao {

   @PersistenceContext(unitName = "IMOGEOPU-JTA")
    EntityManager manager;
    
    public Endereco retornarEndereco(Long id) {
        return manager.find(Endereco.class, id);
    }

     public Endereco enderecoIdImovel(Long id) {
        Query query = manager.createQuery("Select e from Imovel i join i.endereco e where i.id = :id");
        query.setParameter("id", id);
        Endereco endereco = (Endereco) query.getSingleResult();
        return endereco;
    }
     
    public List<Endereco> listarEnderecos() {
        Query query = manager.createQuery("Select i from Endereco i");
        List<Endereco> enderecos = query.getResultList();
        return enderecos;
    }
    
    public void inserirEndereco(Endereco endereco) {
        manager.persist(endereco);
    }

    public void atualizarEndereco(Endereco endereco){
        manager.merge(endereco);
    }

    public void removerEndereco(Endereco endereco) {
        manager.remove(endereco);
    }

}
