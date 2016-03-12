/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifpb.tccii.imogeo.sessionbeans;

import br.com.ifpb.tccii.imogeo.entidades.Imovel;
import br.com.ifpb.tccii.imogeo.entidades.especializacao.Apartamento;
import java.util.ArrayList;
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
public class ApartamentoDao {
    
    @PersistenceContext(unitName = "IMOGEOPU-JTA")
    EntityManager manager;
    
    public Apartamento retornarApartamento(Long id) {
        return manager.find(Apartamento.class, id);
    }

    public List<Apartamento> listarApartamentosAnunciados() {
        Query query = manager.createQuery("Select a from Apartamento a");
        List<Apartamento> aptos = query.getResultList();
        List<Apartamento> result = new ArrayList<Apartamento>();
        for(int i = 0; i < aptos.size();i++){
            if(aptos.get(i).getAnuncio().getAnunciado()== true){
                result.add(aptos.get(i)); 
            }
        }
        return result;
    }
    
    public List<Apartamento> listarApartamentos() {
        Query query = manager.createQuery("Select a from Apartamento a");
        return query.getResultList();
    }
    
    public List<Apartamento> listarApartamentos(String consulta) {
        Query query = manager.createQuery(consulta);
        return query.getResultList();
    }

    public void inserirApartamento(Apartamento ap) {
        manager.persist(ap);
    }

    public void atualizarApartamento(Apartamento ap){
        manager.merge(ap);
    }

    public void removerApartamento(Apartamento ap) {
        manager.remove(manager.getReference(Apartamento.class, ap.getId()));
    }
    
}
