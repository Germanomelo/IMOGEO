/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifpb.tccii.imogeo.controller.utils;

import br.com.ifpb.tccii.imogeo.model.Endereco;
import br.com.ifpb.tccii.imogeo.model.Imovel;
import br.com.ifpb.tccii.imogeo.model.especializacao.Apartamento;
import br.com.ifpb.tccii.imogeo.model.especializacao.Casa;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author germano
 */
public class ImovelCompareFactory {
    
    public List<Imovel> imovelValorASC(List<Imovel> imoveis){
        Collections.sort(imoveis, new Comparator() {
            public int compare(Object o1, Object o2) {
                Imovel imv1 = (Imovel) o1;
                Imovel imv2 = (Imovel) o2;
                return imv1.getValor() < imv2.getValor() ? -1 : (imv1.getValor() > imv2.getValor() ? +1 : 0);
            }
        });
        return imoveis;
    }
    
    public List<Imovel> imovelValorDESC(List<Imovel> imoveis){
        Collections.sort(imoveis, new Comparator() {
            public int compare(Object o1, Object o2) {
                Imovel imv1 = (Imovel) o1;
                Imovel imv2 = (Imovel) o2;
                return imv1.getValor() > imv2.getValor() ? -1 : (imv1.getValor() < imv2.getValor() ? +1 : 0);
            }
        });
        return imoveis;
    }    
    
    public List<Imovel> imovelAreaTotalASC(List<Imovel> imoveis){
        Collections.sort(imoveis, new Comparator() {
            public int compare(Object o1, Object o2) {
                Imovel imv1 = (Imovel) o1;
                Imovel imv2 = (Imovel) o2;
                return imv1.getAreaTotal() < imv2.getAreaTotal() ? -1 : (imv1.getAreaTotal() > imv2.getAreaTotal() ? +1 : 0);
            }
        });
        return imoveis;
    }
    
    public List<Imovel> imovelAreaTotalDESC(List<Imovel> imoveis){
        Collections.sort(imoveis, new Comparator() {
            public int compare(Object o1, Object o2) {
                Imovel imv1 = (Imovel) o1;
                Imovel imv2 = (Imovel) o2;
                return imv1.getAreaTotal() > imv2.getAreaTotal() ? -1 : (imv1.getAreaTotal() < imv2.getAreaTotal() ? +1 : 0);
            }
        });
        return imoveis;
    }
    
    public List<Casa> casaValorASC(List<Casa> c){
        Collections.sort(c, new Comparator() {
            public int compare(Object o1, Object o2) {
                Casa imv1 = (Casa) o1;
                Casa imv2 = (Casa) o2;
                return imv1.getValor() < imv2.getValor() ? -1 : (imv1.getValor() > imv2.getValor() ? +1 : 0);
            }
        });
        return c;
    }
    
    public List<Casa> casaValorDESC(List<Casa> c){
        Collections.sort(c, new Comparator() {
            public int compare(Object o1, Object o2) {
                Casa imv1 = (Casa) o1;
                Casa imv2 = (Casa) o2;
                return imv1.getValor() > imv2.getValor() ? -1 : (imv1.getValor() < imv2.getValor() ? +1 : 0);
            }
        });
        return c;
    }
    
    public List<Casa> casaAreaTotalASC(List<Casa> casas){
        Collections.sort(casas, new Comparator() {
            public int compare(Object o1, Object o2) {
                Casa imv1 = (Casa) o1;
                Casa imv2 = (Casa) o2;
                return imv1.getAreaTotal() < imv2.getAreaTotal() ? -1 : (imv1.getAreaTotal() > imv2.getAreaTotal() ? +1 : 0);
            }
        });
        return casas;
    }
    
    public List<Casa> casaAreaTotalDESC(List<Casa> casas){
        Collections.sort(casas, new Comparator() {
            public int compare(Object o1, Object o2) {
                Apartamento imv1 = (Apartamento) o1;
                Apartamento imv2 = (Apartamento) o2;
                return imv1.getAreaTotal() > imv2.getAreaTotal() ? -1 : (imv1.getAreaTotal() < imv2.getAreaTotal() ? +1 : 0);
            }
        });
        return casas;
    }
    
    public List<Apartamento> aptoValorASC(List<Apartamento> a){
        Collections.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                Apartamento imv1 = (Apartamento) o1;
                Apartamento imv2 = (Apartamento) o2;
                return imv1.getValor() < imv2.getValor() ? -1 : (imv1.getValor() > imv2.getValor() ? +1 : 0);
            }
        });
        return a;
    }
    
    public List<Apartamento> aptoValorDESC(List<Apartamento> a){
        Collections.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                Apartamento imv1 = (Apartamento) o1;
                Apartamento imv2 = (Apartamento) o2;
                return imv1.getValor() > imv2.getValor() ? -1 : (imv1.getValor() < imv2.getValor() ? +1 : 0);
            }
        });
        return a;
    }
    
    public List<Apartamento> aptoAreaTotalASC(List<Apartamento> aptos){
        Collections.sort(aptos, new Comparator() {
            public int compare(Object o1, Object o2) {
                Apartamento imv1 = (Apartamento) o1;
                Apartamento imv2 = (Apartamento) o2;
                return imv1.getAreaTotal() < imv2.getAreaTotal() ? -1 : (imv1.getAreaTotal() > imv2.getAreaTotal() ? +1 : 0);
            }
        });
        return aptos;
    }
    
    public List<Apartamento> aptoAreaTotalDESC(List<Apartamento> aptos){
        Collections.sort(aptos, new Comparator() {
            public int compare(Object o1, Object o2) {
                Apartamento imv1 = (Apartamento) o1;
                Apartamento imv2 = (Apartamento) o2;
                return imv1.getAreaTotal() > imv2.getAreaTotal() ? -1 : (imv1.getAreaTotal() < imv2.getAreaTotal() ? +1 : 0);
            }
        });
        return aptos;
    }
    
    public List<Endereco> enderecoDistanciaASC(List<Endereco> enderecos){
        Collections.sort(enderecos, new Comparator() {
            public int compare(Object o1, Object o2) {
                Endereco end = (Endereco) o1;
                Endereco end2 = (Endereco) o2;
                    
                return end.getDistancia() < end2.getDistancia() ? -1 : (end.getDistancia() > end2.getDistancia() ? +1 : 0);
            }
        });
        return enderecos;
    }
    
    public List<Endereco> enderecoDistanciaDESC(List<Endereco> enderecos){
        Collections.sort(enderecos, new Comparator() {
            public int compare(Object o1, Object o2) {
                Endereco end = (Endereco) o1;
                Endereco end2 = (Endereco) o2;
                    
                return end.getDistancia() > end2.getDistancia() ? -1 : (end.getDistancia() < end2.getDistancia() ? +1 : 0);
            }
        });
        return enderecos;
    }
    
    public List<Endereco> enderecoValorASC(List<Endereco> imoveis){
        Collections.sort(imoveis, new Comparator() {
            public int compare(Object o1, Object o2) {
                Endereco imv1 = (Endereco) o1;
                Endereco imv2 = (Endereco) o2;
                return imv1.getImovel().getValor() < imv2.getImovel().getValor() ? -1 : (imv1.getImovel().getValor() > imv2.getImovel().getValor() ? +1 : 0);
            }
        });
        return imoveis;
    }
    
    public List<Endereco> enderecoValorDESC(List<Endereco> imoveis){
        Collections.sort(imoveis, new Comparator() {
            public int compare(Object o1, Object o2) {
                Endereco imv1 = (Endereco) o1;
                Endereco imv2 = (Endereco) o2;
                return imv1.getImovel().getValor() > imv2.getImovel().getValor() ? -1 : (imv1.getImovel().getValor() < imv2.getImovel().getValor() ? +1 : 0);
            }
        });
        return imoveis;
    }    
    
    public List<Endereco> enderecoAreaTotalASC(List<Endereco> imoveis){
        Collections.sort(imoveis, new Comparator() {
            public int compare(Object o1, Object o2) {
                Endereco imv1 = (Endereco) o1;
                Endereco imv2 = (Endereco) o2;
                return imv1.getImovel().getAreaTotal() < imv2.getImovel().getAreaTotal() ? -1 : (imv1.getImovel().getAreaTotal() > imv2.getImovel().getAreaTotal() ? +1 : 0);
            }
        });
        return imoveis;
    }
    
    public List<Endereco> enderecoAreaTotalDESC(List<Endereco> imoveis){
        Collections.sort(imoveis, new Comparator() {
            public int compare(Object o1, Object o2) {
                Endereco imv1 = (Endereco) o1;
                Endereco imv2 = (Endereco) o2;
                return imv1.getImovel().getAreaTotal() > imv2.getImovel().getAreaTotal() ? -1 : (imv1.getImovel().getAreaTotal() < imv2.getImovel().getAreaTotal() ? +1 : 0);
            }
        });
        return imoveis;
    }
}
