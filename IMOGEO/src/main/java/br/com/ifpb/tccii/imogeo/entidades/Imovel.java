/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifpb.tccii.imogeo.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author germano
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Imovel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long imovelId;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;
    
    @OneToMany(mappedBy = "imovel", fetch = FetchType.LAZY,cascade=CascadeType.ALL)
    private List<Imagem> imagens = new ArrayList<>();
    
    @Embedded
    private Anuncio anuncio;

    @ManyToOne
    private Usuario usuario;

    @Column(nullable = false, precision = 2)
    private Double valor;

    @Column(columnDefinition = "text")
    private String observacao;

    @Column(nullable = false, length = 5)
    private int areaTotal;

    @Column(nullable = false, length = 5)
    private int areaConstruida;

    @Column(length = 10)
    private String finalidade;

    Imagem imagem;
    
    public String getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(String finalidade) {
        this.finalidade = finalidade;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return imovelId;
    }

    public void setId(Long imovelId) {
        this.imovelId = imovelId;
    }

    public Double getValor() {
        return valor;
    }

    public String getMascaraValor(){
        StringBuilder masc = new StringBuilder();
        masc.append(String.valueOf(valor));
        int tam = masc.indexOf(".");
        masc.delete(tam, tam+1);
	masc.insert(tam, ",");
        if (tam >= 4) {
            int repet = tam / 3;
            for (int i = 0; i < repet; i++) {
                tam = tam - 3;

                if (tam >= 1) {
                    masc.insert(tam, ".");
                }
            }
        }
        return masc.toString();
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(int areaTotal) {
        this.areaTotal = areaTotal;
    }

    public int getAreaConstruida() {
        return areaConstruida;
    }

    public void setAreaConstruida(int areaConstruida) {
        this.areaConstruida = areaConstruida;
    }
    
    public List<Imagem> getImagens() {
        return imagens;
    }

    public void setImagens(List<Imagem> imagens) {
        this.imagens = imagens;
    }
    
    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (imovelId != null ? imovelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Imovel)) {
            return false;
        }
        Imovel other = (Imovel) object;
        if ((this.imovelId == null && other.imovelId != null) || (this.imovelId != null && !this.imovelId.equals(other.imovelId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entide.Imovel[ id=" + imovelId + " descrição: " + observacao + " finalidade: " + finalidade + "]";
    }

}
