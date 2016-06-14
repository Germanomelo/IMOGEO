package br.com.ifpb.tccii.imogeo.sessionbeans;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import br.com.ifpb.tccii.imogeo.entidades.Endereco;
import br.com.ifpb.tccii.imogeo.entidades.Imovel;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
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
    
     public List<Endereco> listarEnderecosPorDistancia(String ponto, int distancia) throws ParseException {
        Query query = manager.createQuery("Select e from Endereco e WHERE ST_DISTANCE(GEOMFROMTEXT(:ponto), e.localizacao) * 111.32 <=:distancia");
        Geometry g1 = new WKTReader().read(ponto);
        query.setParameter("ponto", g1);
        query.setParameter("distancia", Double.parseDouble(String.valueOf(distancia)));
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
