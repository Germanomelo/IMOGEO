/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifpb.tccii.imogeo.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author germano
 */
@Embeddable
public class Anuncio implements Serializable{
    
    private Boolean anunciado = false;
    
    @Temporal(TemporalType.DATE)
    private Date dataPublicacao = new Date();
    
    @Temporal(TemporalType.DATE)
    private Date dataFinalizacao;

    public Boolean getAnunciado() {
        return anunciado;
    }

    public void setAnunciado(Boolean anunciado) {
        this.anunciado = anunciado;
    }

    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public Date getDataFinalizacao() {
        return dataFinalizacao;
    }

    public void setDataFinalizacao(Date dataFinalizacao) {
        this.dataFinalizacao = dataFinalizacao;
    }

}
