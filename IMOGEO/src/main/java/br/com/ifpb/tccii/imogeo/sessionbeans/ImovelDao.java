/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifpb.tccii.imogeo.sessionbeans;

import br.com.ifpb.tccii.imogeo.entidades.Imovel;
import br.com.ifpb.tccii.imogeo.entidades.Usuario;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import java.util.ArrayList;
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
public class ImovelDao {

    @PersistenceContext(unitName = "IMOGEOPU-JTA")
    EntityManager manager;

    public Imovel retornarImovel(Long id) {
        return manager.find(Imovel.class, id);
    }
    
    public List<Imovel> listarImoveisAnunciados() {
        Query query = manager.createQuery("Select i from Imovel i");
        List<Imovel> imoveis = query.getResultList();
//        query.setParameter("anunciado", true);
        List<Imovel> result = new ArrayList<Imovel>();
        for(int i = 0; i < imoveis.size();i++){
            if(imoveis.get(i).getAnuncio().getAnunciado()== true){
                result.add(imoveis.get(i)); 
            }
        }
        return result;
    }

    public List<Imovel> listarImoveis() {
        Query query = manager.createQuery("Select i from Imovel i");
        List<Imovel> imoveis = query.getResultList();
        return imoveis;
    }

    public List<Imovel> listarSimplesDescricao(String consulta) {
        Query query = manager.createQuery("Select i from Imovel i where i.descricao like :valor ");
        query.setParameter("valor", consulta);
        List<Imovel> imoveis = query.getResultList();
        return imoveis;
    }

    public List<Imovel> listarImoveisFinalidadeAnunciados(String finalidade) {
        Query query = manager.createQuery("Select i from Imovel i where i.finalidade like :finalidade");
        query.setParameter("finalidade", finalidade);
        List<Imovel> imoveis = query.getResultList();
        List<Imovel> result = new ArrayList<Imovel>();
        for(int i = 0; i < imoveis.size();i++){
            if(imoveis.get(i).getAnuncio().getAnunciado()== true){
                result.add(imoveis.get(i)); 
            }
        }
        return result;
    }
    
    public List<Imovel> listarImoveisFinalidade(String finalidade) {
        Query query = manager.createQuery("Select i from Imovel i where i.finalidade like :finalidade");
        query.setParameter("finalidade", finalidade);
        List<Imovel> imoveis = query.getResultList();
        return imoveis;
    }

    public List<Imovel> listarImoveisIdUser(Usuario usuario) {
//        Query query = manager.createQuery("Select i from Imovel i");
        Query query = manager.createQuery("Select i from Usuario u join u.imoveis i where u.id = :id");
//        Query query = manager.createQuery("Select i from Usuario.imoveis i");
        query.setParameter("id", usuario.getId());
        List<Imovel> imoveis = query.getResultList();
        return imoveis;
    }

    public void inserirImovel(Imovel imovel) {
        manager.persist(imovel);
    }

    public void atualizarImovel(Imovel imovel) {
        manager.merge(imovel);
    }

    public void removerImovel(Imovel imovel) {
//        manager.flush();
        manager.remove(manager.getReference(Imovel.class, imovel.getId()));
    }

}
