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

    public List<Imovel> listarImoveisFinalidade(String finalidade) {
        Query query = manager.createQuery("Select i from Imovel i where i.finalidade like :finalidade");
        query.setParameter("finalidade", finalidade);
        List<Imovel> imoveis = query.getResultList();
        return imoveis;
    }

    public List<Imovel> listarImoveisLocalizacao(String ponto, int distancia) throws ParseException {
        double lat;
        double log;
//        Query query = manager.createQuery("Select i from Imovel i");
//        Query query = manager.createQuery("select i from imovel i join i.endereco e ST_DISTANCE(:ponto, e.localizacao) * 111.32 <=:distancia");
//        Query query = manager.createQuery("select i from Imovel i where ST_Distance_Sphere(GeomFromText(:ponto), GeomFromText(i.endereco.localizacao)) * 111.32 <=:distancia");
        Query query = manager.createQuery("select i from Imovel i where ST_Distance_Sphere(i.endereco.localizacao, ST_MakePoint(:log,:lat)) * 111.32 <=:distancia");
//        Query query = manager.createQuery("select i from imovel i join i.endereco e ST_Distance(ST_Point(ST_X(ST_Centroid(e.localizacao)), ST_Y(ST_Centroid(e.localizacao))),ST_MakePoint(:log,:lat)) * 111.32 <=:distancia");
//        Query query = manager.createQuery("select i from Imovel i where ST_INTERSERTS(ST_Point(ST_X(ST_Centroid(i.endereco.localizacao)), ST_Y(ST_Centroid(i.endereco.localizacao))), ST_BUFFER(ST_MakePoint(:log,:lat),:distancia,8))");
//        Query query = manager.createQuery("select i from imovel i join i.endereco e ST_Distance(GeomFromText(:ponto), e.localizacao) * 111.32 <=:distancia");
//        Query query = manager.createQuery("select i from imovel i join i.endereco e ST_Distance(GeomFromText(':ponto'), e.localizacao) * 111.32 <=:distancia");
//        Query query = manager.createQuery("select i from imovel i join i.endereco e ST_Distance(GeomFromText(':ponto'), GeomFromText(e.localizacao)) * 111.32 <=:distancia");
//        query.setParameter("distancia", distancia);
        Geometry g1 = new WKTReader().read(ponto);
        lat = g1.getCoordinate().x;
        log = g1.getCoordinate().y;
//        local.getEndereco().setLocalizacao((Point) g1);
        query.setParameter("lat", lat);
        query.setParameter("log", log);
//        query.setParameter("ponto", g1);
        query.setParameter("distancia", Double.parseDouble(String.valueOf(distancia)));
        List<Imovel> imoveis = query.getResultList();
        
        System.out.println(imoveis);

//        g1.buffer(, distancia)
        return imoveis;
    }

    public List<Imovel> listarImoveisIdUser(Usuario usuario) {
        Query query = manager.createQuery("Select i from Usuario u join u.imoveis i where u.id = :id");
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
