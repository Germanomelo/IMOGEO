package br.com.ifpb.tccii.imogeo.entidades;

///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package entidades;
//
//import java.io.Serializable;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//
///**
// *
// * @author germano
// */
//@Entity
//public class Qualificacao implements Serializable {
//    private static final long serialVersionUID = 1L;
//    
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//    
//    @Column(columnDefinition="text")
//    private String mensagem;
//   
//    private Integer qualificacao;
//   
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getMensagem() {
//        return mensagem;
//    }
//
//    public void setMensagem(String mensagem) {
//        this.mensagem = mensagem;
//    }
//
//    public Integer getQualificacao() {
//        return qualificacao;
//    }
//
//    public void setQualificacao(Integer qualificacao) {
//        this.qualificacao = qualificacao;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (id != null ? id.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof Qualificacao)) {
//            return false;
//        }
//        Qualificacao other = (Qualificacao) object;
//        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "Entide.Qualificacao[ id=" + id + " ]";
//    }
//    
//}
