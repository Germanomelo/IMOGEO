/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifpb.tccii.imogeo.entidades;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;



/**
 *
 * @author germano
 */
@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public class Imovel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long imovelId;

//    @OneToMany(fetch= FetchType.LAZY, cascade= CascadeType.REMOVE)
//    private List<Comentario> comentarios = new ArrayList<Comentario>();
    
    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;
    
//    @OneToMany(mappedBy="imovel", cascade= CascadeType.ALL)
//    private List<Imagem> imagens = new ArrayList<Imagem>();
    
    @Embedded
    private Anuncio anuncio;
    
    @ManyToOne
    private Usuario usuario;
  
    @Column(nullable = false, precision=2)
    private Double valor;
    
    @Column(nullable = false, columnDefinition="text")
    private String descricao;
    
    @Column( nullable = false, length=5)
    private String areaTotal;
    
    @Column(nullable = false, length=5)
    private String areaConstruida;
    
    @Column(length = 10)
    private String finalidade;

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

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(String areaTotal) {
        this.areaTotal = areaTotal;
    }

    public String getAreaConstruida() {
        return areaConstruida;
    }

    public void setAreaConstruida(String areaConstruida) {
        this.areaConstruida = areaConstruida;
    }

//    public List<Comentario> getComentarios() {
//        return comentarios;
//    }

//    public void setComentarios(List<Comentario> comentarios) {
//        this.comentarios = comentarios;
//    }

//    public List<Imagem> getImagens() {
//        return imagens;
//    }
//
//    public void setImagens(List<Imagem> imagens) {
//        this.imagens = imagens;
//    }

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
        return "Entide.Imovel[ id=" + imovelId + " descrição: "+descricao+ " finalidade: "+finalidade+"]";
    }
    
}
