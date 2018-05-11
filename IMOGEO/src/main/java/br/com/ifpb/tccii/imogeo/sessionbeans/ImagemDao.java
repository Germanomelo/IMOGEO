/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifpb.tccii.imogeo.sessionbeans;

import br.com.ifpb.tccii.imogeo.entidades.Imagem;
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
public class ImagemDao {

    @PersistenceContext(unitName = "IMOGEOPU-JTA")
    private EntityManager manager;

    public Imagem retornarImagem(Long id) {
        return manager.find(Imagem.class, id);
    }

    public List<Imagem> listarImagens() {
        Query query = manager.createQuery("Select i from Imagem i");
        return query.getResultList();
    }

    public List<Imagem> listarImagens(String consulta) {
        Query query = manager.createQuery(consulta);
        return query.getResultList();
    }
    
    public void inserirImagem(Imagem img) {
        manager.persist(img);
    }

    public void atualizarImagem(Imagem img) {
        manager.merge(img);
    }

    public void removerImagem(Imagem img) {
        manager.remove(img);
    }
}
