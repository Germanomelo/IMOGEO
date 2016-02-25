/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifpb.tccii.imogeo.managedbean;

import br.com.ifpb.tccii.imogeo.entidades.Imovel;
import br.com.ifpb.tccii.imogeo.sessionbeans.ImovelDao;
import com.vividsolutions.jts.io.ParseException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Mano
 */
@ManagedBean
@ViewScoped
public class LocalizacaoMB implements Serializable {

    private String loc;
    private int distancia = 5;
    private boolean pesquisaLocalizacao = true;
    private boolean resultLocalizacao = false;

    private Imovel imovel = new Imovel();

    @EJB
    private ImovelDao imovelDao;

    public LocalizacaoMB() {
    }

    public List<Imovel> imoveisLocalizacao() throws ParseException {
//        System.out.println(this.loc);
//        Imovel local = new Imovel();
//        Geometry g1 = new WKTReader().read(this.loc.toString());
//        local.getEndereco().setLocalizacao((Point) g1);
        return imovelDao.listarImoveisLocalizacao(loc , distancia);
//        double distancia;
//        List<Imovel> imoveis = imovelDao.listarImoveis();
//
//        for (Imovel i : imoveis) {
//            System.out.println(loc.distance(i.getEndereco().getLocalizacao()));
//        }
//        return null;

    }

    public void telaPesquisaLocalizacao() {
        pesquisaLocalizacao = true;
        resultLocalizacao = false;
    }

    public void telaResultLocalizacao() {
        pesquisaLocalizacao = false;
        resultLocalizacao = true;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public boolean isPesquisaLocalizacao() {
        return pesquisaLocalizacao;
    }

    public void setPesquisaLocalizacao(boolean pesquisaLocalizacao) {
        this.pesquisaLocalizacao = pesquisaLocalizacao;
    }

    public boolean isResultLocalizacao() {
        return resultLocalizacao;
    }

    public void setResultLocalizacao(boolean resultLocalizacao) {
        this.resultLocalizacao = resultLocalizacao;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

}
