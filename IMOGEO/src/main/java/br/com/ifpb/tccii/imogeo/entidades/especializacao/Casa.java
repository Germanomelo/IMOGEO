/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifpb.tccii.imogeo.entidades.especializacao;

import br.com.ifpb.tccii.imogeo.entidades.Imovel;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author germano
 */
@Entity
@DiscriminatorValue("CASA")
public class Casa extends Imovel{
    
    @Column(length=2)
    private String quarto;
    
    @Column(length=2)
    private String suite;
    
    @Column(length=2)        
    private String vagaGaragem;
    
    @Column(length=2)        
    private String banheiroSocial;
    
    private Boolean piscina = false;
    
    private Boolean despensa = false;
    
    private Boolean areaServico = false;
    
    private Boolean churrasqueira = false;
    
    private Boolean jardim = false;

    public String getQuarto() {
        return quarto;
    }

    public void setQuarto(String quarto) {
        this.quarto = quarto;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getVagaGaragem() {
        return vagaGaragem;
    }

    public void setVagaGaragem(String vagaGaragem) {
        this.vagaGaragem = vagaGaragem;
    }

    public String getBanheiroSocial() {
        return banheiroSocial;
    }

    public void setBanheiroSocial(String banheiroSocial) {
        this.banheiroSocial = banheiroSocial;
    }

    public Boolean getPiscina() {
        return piscina;
    }

    public void setPiscina(Boolean piscina) {
        this.piscina = piscina;
    }

    public Boolean getDespensa() {
        return despensa;
    }

    public void setDespensa(Boolean despensa) {
        this.despensa = despensa;
    }

    public Boolean getAreaServico() {
        return areaServico;
    }

    public void setAreaServico(Boolean areaServico) {
        this.areaServico = areaServico;
    }

    public Boolean getChurrasqueira() {
        return churrasqueira;
    }

    public void setChurrasqueira(Boolean churrasqueira) {
        this.churrasqueira = churrasqueira;
    }

    public Boolean getJardim() {
        return jardim;
    }

    public void setJardim(Boolean jardim) {
        this.jardim = jardim;
    }
    
}
