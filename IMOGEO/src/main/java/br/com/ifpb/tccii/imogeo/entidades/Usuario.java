/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifpb.tccii.imogeo.entidades;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author germano
 */
@Entity
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @OneToMany(/*fetch= FetchType.EAGER,*/ cascade= CascadeType.REMOVE)
//    private List<Qualificacao> qualificacoes = new ArrayList<Qualificacao>();
//    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = " Imoveis_Favoritos",
            joinColumns = @JoinColumn(name = " User_ID "),
            inverseJoinColumns = @JoinColumn(name = " Imovel_ID "))
    private List<Imovel> favoritos;
//    
    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Imovel> imoveis;

    @OneToOne(cascade = CascadeType.ALL)
    private Imagem imagem;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 90)
    private String senha;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @Column(length = 15)
    private String telefoneResidencial;

    @Column(length = 15)
    private String telefoneComercial;

    @Column(length = 15)
    private String telefoneCelular;

    @Temporal(TemporalType.DATE)
    private Date dataCriacao = new Date();

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

//    public List<Qualificacao> getQualificacoes() {
//        return qualificacoes;
//    }
//
//    public void setQualificacoes(List<Qualificacao> qualificacoes) {
//        this.qualificacoes = qualificacoes;
//    }
//
    public List<Imovel> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Imovel> favoritos) {
        this.favoritos = favoritos;
    }

    public List<Imovel> getImoveis() {
        return imoveis;
    }

    public void setImoveis(List<Imovel> imoveis) {
        this.imoveis = imoveis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

//    public List<Imovel> getProcuras() {
//        return procuras;
//    }
//
//    public void setProcuras(List<Imovel> procuras) {
//        this.procuras = procuras;
//    }
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefoneResidencial() {
        return telefoneResidencial;
    }

    public void setTelefoneResidencial(String telefoneResidencial) {
        this.telefoneResidencial = telefoneResidencial;
    }

    public String getTelefoneComercial() {
        return telefoneComercial;
    }

    public void setTelefoneComercial(String telefoneComercial) {
        this.telefoneComercial = telefoneComercial;
    }

    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entide.Usuario[ id=" + id + " nome=" + nome + " ]";
    }
}
