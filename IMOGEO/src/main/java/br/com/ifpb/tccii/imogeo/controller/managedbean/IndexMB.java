package br.com.ifpb.tccii.imogeo.controller.managedbean;

import br.com.ifpb.tccii.imogeo.model.Comentario;
import br.com.ifpb.tccii.imogeo.model.Endereco;
import br.com.ifpb.tccii.imogeo.model.Imagem;
import br.com.ifpb.tccii.imogeo.model.Imovel;
import br.com.ifpb.tccii.imogeo.model.Usuario;
import br.com.ifpb.tccii.imogeo.model.especializacao.Apartamento;
import br.com.ifpb.tccii.imogeo.model.especializacao.Casa;
import br.com.ifpb.tccii.imogeo.controller.dao.ApartamentoDao;
import br.com.ifpb.tccii.imogeo.controller.dao.CasaDao;
import br.com.ifpb.tccii.imogeo.controller.dao.ComentarioDao;
import br.com.ifpb.tccii.imogeo.controller.dao.EnderecoDao;
import br.com.ifpb.tccii.imogeo.controller.dao.ImagemDao;
import br.com.ifpb.tccii.imogeo.controller.dao.ImovelDao;
import br.com.ifpb.tccii.imogeo.controller.dao.UsuarioDao;
import br.com.ifpb.tccii.imogeo.controller.utils.ImagemManager;
import br.com.ifpb.tccii.imogeo.controller.utils.ImovelCompareFactory;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Germano
 */
@ManagedBean(name = "indexMB")
@ViewScoped
public class IndexMB implements Serializable {

    private String comentarioTxt;
    private String loc;
    private double lat;
    private double log;
    private int distancia = 5;
    private float pesqValorDe = 0;
    private float pesqValorAte = 0;
    private int pesqQtdeQuarto = 0;
    private int pesqQtdeSuite = 0;
    private int pesqQtdeVagaGaragem = 0;
    private int pesqAreaDe = 0;
    private int pesqAreaAte = 0;
    private String pesqOrdenacao = "MenorPreco";
    private String pesqFinalidade = "TODOS";
    private String pesqTipo = "TODOS";
    private String tituloH3 = "Imóveis";
    private String finalidade = null;
    private boolean exibeTodosImoveis = true;
    private boolean exibePesquisaLocalizacao = false;
    private boolean exibeResultadoPesquisaLocalizacao = false;
    private boolean exibeCasas = false;
    private boolean exibeAptos = false;
    private boolean exibeImovelFinalidade = false;
    private boolean exibebuscaSimples = false;
    private boolean exibeDetalhesCasa = false;
    private boolean exibeDetalhesApto = false;
    private boolean exibeAdicionarFavoritos = false;
    private boolean exibeRemoverFavoritos = false;
    private boolean exibeBuscaLocalizacaoAvancada = false;
    private boolean exibeMedidaDeDistanciaKm = false;
    private boolean exibeMapMinhaLocalizacao = true;
    private String busca = null;
    private Comentario comentario = new Comentario();
    private Casa casa = new Casa();
    private Imovel imovel = new Imovel();
    private Apartamento apto = new Apartamento();
    private Usuario user = new Usuario();
    private Usuario userSession = new Usuario();
    private Endereco endereco = new Endereco();
    List<Imovel> favoritos;
    @EJB
    private UsuarioDao userDao;
    @EJB
    private EnderecoDao enderecoDao;
    @EJB
    private CasaDao casaDao;
    @EJB
    private ImovelDao imovelDao;
    @EJB
    private ApartamentoDao aptoDao;
    @EJB
    private ComentarioDao comentarioDao;
    @EJB
    private ImagemDao imagemDao;

    public IndexMB() {
    }

    public float geometriaLocalUser(float distancia) {
        if (distancia >= 1000) {
            this.exibeMedidaDeDistanciaKm = true;
            return distancia / 1000;
        } else {
            this.exibeMedidaDeDistanciaKm = false;
            return Math.round(distancia);
        }

    }

    private float geometriaMetros(Point localizacaoImovel) {
        Geometry g1 = null;
        try {
            g1 = new WKTReader().read(this.loc);
        } catch (ParseException ex) {
            this.mensagemErro("Erro!", ex.getMessage());
        }
        float disnaciaMetros = (float) (g1.distance(localizacaoImovel) * 111111.32);

        return disnaciaMetros;

    }

    public List<Endereco> enderecoPorLocalizacao() {
        if (this.exibeBuscaLocalizacaoAvancada) {
            try {

                List<Endereco> enderecos = this.buscaAvancadaEnderecoPorLocalizacao();

                for (Endereco e : enderecos) {
                    e.setDistancia(geometriaMetros(e.getLocalizacao()));
                }
                return ordenarListEndereco(enderecos);
            } catch (ParseException ex) {
                Logger.getLogger(IndexMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            List<Endereco> enderecos = this.buscaSimplesEnderecoPorLocalizacao();
            for (Endereco e : enderecos) {
                e.setDistancia(geometriaMetros(e.getLocalizacao()));
            }
            return ordenarListEndereco(enderecos);
        }

        return null;
    }

    public List<Endereco> buscaAvancadaEnderecoPorLocalizacao() throws ParseException {
        List<Endereco> result = new ArrayList<>();
        List<Endereco> enderecos = null;
        Imovel imovelPesq = null;
        Casa casaPesq = null;
        Apartamento aptoPesq = null;
        try {
//            if (exibeBuscaLocalizacaoAvancada) {
            enderecos = enderecoDao.listarEnderecosPorDistancia(this.loc, this.distancia);
            for (int i = 0; i < enderecos.size(); i++) {
                imovelPesq = enderecos.get(i).getImovel();
                if (enderecos.get(i).getImovel().getAnuncio().getAnunciado() == true) {
                    System.out.println("Anunciado!");
                    if (("TODOS".equals(this.getPesqFinalidade())) || (this.getPesqFinalidade() == null ? imovelPesq.getFinalidade() == null : this.getPesqFinalidade().equals(imovelPesq.getFinalidade()))) {
                        System.out.println("Finalidade");
                        if ((this.getPesqAreaDe() == 0) || (this.getPesqAreaDe() <= imovelPesq.getAreaTotal())) {
                            System.out.println("Area de");
                            if ((this.getPesqAreaAte() == 0) || (this.getPesqAreaAte() >= imovelPesq.getAreaTotal())) {
                                System.out.println("Area até");
                                if ((this.getPesqValorDe() == 0) || (this.getPesqValorDe() <= imovelPesq.getValor())) {
                                    System.out.println("Valor de");
                                    if ((this.getPesqValorAte() == 0) || (this.getPesqValorAte() >= imovelPesq.getValor())) {
                                        System.out.println("Valor até");
                                        if (("CASA".equals(this.getPesqTipo()) || "TODOS".equals(this.getPesqTipo())) && (imovelPesq instanceof Casa)) {
                                            System.out.println("tipo casa ou todos");
                                            casaPesq = (Casa) imovelPesq;
                                            if ((this.getPesqQtdeQuarto() == 0) || (casaPesq.getQuarto() == this.getPesqQtdeQuarto())) {
                                                if ((this.getPesqQtdeSuite() == 0) || (casaPesq.getSuite() == this.getPesqQtdeSuite())) {
                                                    if ((this.getPesqQtdeVagaGaragem() == 0) || (casaPesq.getVagaGaragem() == this.getPesqQtdeVagaGaragem())) {
                                                        enderecos.get(i).getImovel().setImagem(buscarCriarImagemTemp(enderecos.get(i).getImovel()));
                                                        result.add(enderecos.get(i));
                                                    }
                                                }
                                            }
                                        }
                                        if (("APTO".equals(this.getPesqTipo()) || "TODOS".equals(this.getPesqTipo())) && (imovelPesq instanceof Apartamento)) {
                                            aptoPesq = (Apartamento) imovelPesq;
                                            if ((this.getPesqQtdeQuarto() == 0) || (aptoPesq.getQuarto() == this.getPesqQtdeQuarto())) {
                                                if ((this.getPesqQtdeSuite() == 0) || (aptoPesq.getSuite() == this.getPesqQtdeSuite())) {
                                                    if ((this.getPesqQtdeVagaGaragem() == 0) || (aptoPesq.getVagaGaragem() == this.getPesqQtdeVagaGaragem())) {
                                                        enderecos.get(i).getImovel().setImagem(buscarCriarImagemTemp(enderecos.get(i).getImovel()));
                                                        result.add(enderecos.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return result;
        } catch (Exception e) {
            this.mensagemErro("Erro!", e.getMessage());
        }
        return null;
    }

    public List<Endereco> buscaSimplesEnderecoPorLocalizacao() {
        List<Endereco> result = new ArrayList<>();
        List<Endereco> enderecos;
        try {
            enderecos = enderecoDao.listarEnderecosPorDistancia(this.loc, this.distancia);
            for (int i = 0; i < enderecos.size(); i++) {
                if (enderecos.get(i).getImovel().getAnuncio().getAnunciado() == true) {
                    enderecos.get(i).getImovel().setImagem(buscarCriarImagemTemp(enderecos.get(i).getImovel()));
                    result.add(enderecos.get(i));
                }
            }
            return result;
        } catch (Exception e) {
            this.mensagemErro("Erro!", e.getMessage());
        }

        return null;
    }

    private Imagem buscarCriarImagemTemp(Imovel i) {
        Imagem img = imagemDao.retornarPrimeriaImagemPorImovel(i);
        if (img == null) {
            img = new Imagem();
            img.setNome("/img/semimagem.png");
        } else {
            ImagemManager im = new ImagemManager();
            im.criarImagens(null, img);
            img.setNome("/temp/" + img.getNome());
        }
        return img;
    }

    public boolean isFavoritoExiste() {
        boolean status = false;

        if (userSession != null) {
            this.favoritos = imovelDao.listarImoveisFavoritos(this.userSession);
            for (int i = 0; i < this.favoritos.size(); i++) {
                if (this.favoritos.get(i).getId() == this.imovel.getId()) {
                    status = true;
                }
            }
        }
        return status;
    }

    public void inserirImovelFavorito() {
        if (this.exibeDetalhesApto) {
            this.favoritos.add(this.apto);
        } else if (this.exibeDetalhesCasa) {
            this.favoritos.add(this.casa);
        } else {
            mensagemErro("Erro!", "erro ao tentar inserir imóvel a favoritos");
        }
        this.userSession.setFavoritos(this.favoritos);
        userDao.atualizarUsuario(this.userSession);
    }

    public void removerImovelFavorito() {
//        System.out.println("tamanho do array favoritos " + this.favoritos.size());
        if (this.exibeDetalhesCasa) {
            for (int i = 0; i < this.favoritos.size(); i++) {
                if (this.favoritos.get(i).getId() == this.casa.getId()) {
                    this.favoritos.remove(i);
                }

            }
        } else if (this.exibeDetalhesApto) {
            for (int i = 0; i < this.favoritos.size(); i++) {
                if (this.favoritos.get(i).getId() == this.casa.getId()) {
                    this.favoritos.remove(i);
                }

            }
        } else {
            mensagemErro("Erro!", "erro ao tentar remover imóvel de favoritos");
        }
        this.userSession.setFavoritos(this.favoritos);
        userDao.atualizarUsuario(userSession);

        if (this.exibeDetalhesApto) {
            this.telaDetalhesApto();
        } else {
            this.telaDetalhesCasa();
        }
    }

    public List<Imovel> buscaSimplesPorPalavraChave() {
        List<Imovel> result = new ArrayList<>();
        List<Imovel> imoveis;
        try {
            imoveis = this.imovelDao.listarImoveisPorPalavraChaveAnunciados(busca);
            for (int i = 0; i < imoveis.size(); i++) {
                if (imoveis.get(i).getAnuncio().getAnunciado() == true) {
                    imoveis.get(i).setImagem(buscarCriarImagemTemp(imoveis.get(i)));
                    result.add(imoveis.get(i));
                }
            }
            return ordenarListImovel(result);

        } catch (Exception e) {
            this.mensagemErro("Erro!", e.getMessage());
        }
        return null;
    }

    public List<Apartamento> listarApartamentosAnunciados() {
        try {
            List<Apartamento> imoveis = aptoDao.listarApartamentosAnunciados();
            for (Imovel i : imoveis) {
                i.setImagem(buscarCriarImagemTemp(i));
            }
            return ordenarListApto(imoveis);
        } catch (Exception e) {
            this.mensagemErro("Erro!", e.getMessage());
        }
        return null;
    }

    public List<Casa> listarCasasAnunciadas() {
        try {
            List<Casa> imoveis = casaDao.listarCasasAnunciadas();
            for (Imovel i : imoveis) {
                i.setImagem(buscarCriarImagemTemp(i));
            }
            return ordenarListCasa(imoveis);
        } catch (Exception e) {
            this.mensagemErro("Erro!", e.getMessage());
        }
        return null;
    }

    public List<Imovel> listarImoveisAnunciados() {
        try {
            List<Imovel> imoveis = imovelDao.listarImoveisAnunciados();
            for (Imovel i : imoveis) {
                i.setImagem(buscarCriarImagemTemp(i));
            }
            return ordenarListImovel(imoveis);
        } catch (Exception e) {
            this.mensagemErro("Erro!", e.getMessage());
        }
        return null;
    }

    public List<Imovel> listarImoveisFinalidadeAnunciados() {
        try {
            List<Imovel> imoveis = imovelDao.listarImoveisFinalidadeAnunciados(finalidade);
            for (Imovel i : imoveis) {
                i.setImagem(buscarCriarImagemTemp(i));
            }
            return ordenarListImovel(imoveis);
        } catch (Exception e) {
            this.mensagemErro("Erro!", e.getMessage());
        }
        return null;
    }

    public void detalhesImovel() {
        if (this.imovel instanceof Casa) {
            this.detalhesCasa();
        } else if (this.imovel instanceof Apartamento) {
            this.detalhesApto();
        } else {
            this.mensagemErro("Erro!", "Erro ao ver informações de Imovél, tente mais tarde");
        }
        this.capturarUserSession();
    }

    public void detalhesCasa() {
        try {
            this.casa = casaDao.retornarCasa(this.imovel.getId());
        } catch (Exception e) {
            this.mensagemErro("Erro!", e.getMessage());
        }
        this.telaDetalhesCasa();
    }

    public void detalhesApto() {
        try {
            this.apto = aptoDao.retornarApartamento(this.imovel.getId());
        } catch (Exception e) {
            this.mensagemErro("Erro!", e.getMessage());
        }
        this.telaDetalhesApto();
    }

    public void inserirComentario() {
        Comentario c = new Comentario();
        c.setComentario(this.comentarioTxt);
        c.setDataComentario(new Date());
        c.setUsuario(userSession);
        if (exibeDetalhesCasa) {
            c.setImovel(this.casa);
        } else if (exibeDetalhesApto) {
            c.setImovel(this.apto);
        }
        this.comentarioDao.inserirComentario(c);
        this.comentarioTxt = "";
    }

    public List<Comentario> listarComentarios() {
        List<Comentario> comentarios = new ArrayList<>();
        if (exibeDetalhesCasa) {
            comentarios = comentarioDao.listarComentariosIdImovel(this.casa);
        } else if (exibeDetalhesApto) {
            comentarios = comentarioDao.listarComentariosIdImovel(this.apto);
        }
        return comentarios;
    }

    public void removerComentario() {
        comentarioDao.removerComentario(this.comentario);
    }

    public void capturarUserSession() {
        HttpSession session;
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        this.userSession = (Usuario) session.getAttribute("usuario");
    }

    private List<Imovel> ordenarListImovel(List<Imovel> i) {
        ImovelCompareFactory icf = new ImovelCompareFactory();
        switch (this.pesqOrdenacao) {
            case "MenorPreco":
                return icf.imovelValorASC(i);
            case "MaiorPreco":
                return icf.imovelValorDESC(i);
            case "MenorArea":
                return icf.imovelAreaTotalASC(i);
            case "MaiorArea":
                return icf.imovelAreaTotalDESC(i);
        }
        return i;
    }

    private List<Apartamento> ordenarListApto(List<Apartamento> a) {
        ImovelCompareFactory icf = new ImovelCompareFactory();
        switch (this.pesqOrdenacao) {
            case "MenorPreco":
                return icf.aptoValorASC(a);
            case "MaiorPreco":
                return icf.aptoValorDESC(a);
            case "MenorArea":
                return icf.aptoAreaTotalASC(a);
            case "MaiorArea":
                return icf.aptoAreaTotalDESC(a);
        }
        return a;
    }

    private List<Casa> ordenarListCasa(List<Casa> c) {
        ImovelCompareFactory icf = new ImovelCompareFactory();
        switch (this.pesqOrdenacao) {
            case "MenorPreco":
                return icf.casaValorASC(c);
            case "MaiorPreco":
                return icf.casaValorDESC(c);
            case "MenorArea":
                return icf.casaAreaTotalASC(c);
            case "MaiorArea":
                return icf.casaAreaTotalDESC(c);
        }
        return c;
    }

    private List<Endereco> ordenarListEndereco(List<Endereco> e) {
        ImovelCompareFactory icf = new ImovelCompareFactory();
        switch (this.pesqOrdenacao) {
            case "MenorDistancia":
                return icf.enderecoDistanciaASC(e);
            case "MaiorDistancia":
                return icf.enderecoDistanciaDESC(e);
            case "MenorPreco":
                return icf.enderecoValorASC(e);
            case "MaiorPreco":
                return icf.enderecoValorDESC(e);
            case "MenorArea":
                return icf.enderecoAreaTotalASC(e);
            case "MaiorArea":
                return icf.enderecoAreaTotalDESC(e);
        }
        return e;
    }

//    Telas de exibição
    public void telaDetalhesApto() {
        this.exibePesquisaLocalizacao = false;
        this.exibeResultadoPesquisaLocalizacao = false;
        this.exibeDetalhesApto = true;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = false;
        this.tituloH3 = "Apartamento";
        this.lat = this.apto.getEndereco().getLocalizacao().getCoordinate().x;
        this.log = this.apto.getEndereco().getLocalizacao().getCoordinate().y;
        this.capturarUserSession();
    }

    public void telaDetalhesCasa() {
        this.exibePesquisaLocalizacao = false;
        this.exibeResultadoPesquisaLocalizacao = false;
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = true;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = false;
        this.tituloH3 = "Casa";
        this.lat = this.casa.getEndereco().getLocalizacao().getCoordinate().x;
        this.log = this.casa.getEndereco().getLocalizacao().getCoordinate().y;
        this.capturarUserSession();
    }

    public void telaExibebuscaSimples() {
        this.exibePesquisaLocalizacao = false;
        this.exibeResultadoPesquisaLocalizacao = false;
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = true;
        this.tituloH3 = "Busca Simples";
    }

    public void telaExibeTodosImoveis() {
        this.exibePesquisaLocalizacao = false;
        this.exibeResultadoPesquisaLocalizacao = false;
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = true;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = false;
        this.tituloH3 = "Imóveis";
    }

    public void telaExibeCasas() {
        this.exibePesquisaLocalizacao = false;
        this.exibeResultadoPesquisaLocalizacao = false;
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = true;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = false;
        this.tituloH3 = "Casas";
    }

    public void telaExibeAptos() {
        this.exibePesquisaLocalizacao = false;
        this.exibeResultadoPesquisaLocalizacao = false;
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = true;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = false;
        this.tituloH3 = "Apartamentos";
    }

    public void telaExibeImovelTemporada() {
        this.exibePesquisaLocalizacao = false;
        this.exibeResultadoPesquisaLocalizacao = false;
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = true;
        this.exibebuscaSimples = false;
        this.finalidade = "TEMPORADA";
        this.tituloH3 = "Temporada";
    }

    public void telaExibeImovelComprar() {
        this.exibePesquisaLocalizacao = false;
        this.exibeResultadoPesquisaLocalizacao = false;
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = true;
        this.exibebuscaSimples = false;
        this.finalidade = "VENDER";
        this.tituloH3 = "Comprar ";
    }

    public void telaExibeImovelAlugar() {
        this.exibePesquisaLocalizacao = false;
        this.exibeResultadoPesquisaLocalizacao = false;
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = true;
        this.exibebuscaSimples = false;
        this.finalidade = "ALUGAR";
        this.tituloH3 = "Alugar";
    }

    // exibe imoveis de acordo com a distacincia da localização
    public void telaResultadoPesquisaLocalizacao() {
        this.exibePesquisaLocalizacao = false;
        this.exibeResultadoPesquisaLocalizacao = true;
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = false;
        this.tituloH3 = "Raio de atuação da busca: " + this.distancia + "Km";
    }

    // exibe o ponto(lat, long) de onde parte a busca
    public void telaPesquisaLocalizacao() {
        this.exibePesquisaLocalizacao = true;
        this.exibeResultadoPesquisaLocalizacao = false;
        this.exibeDetalhesApto = false;
        this.exibeDetalhesCasa = false;
        this.exibeTodosImoveis = false;
        this.exibeAptos = false;
        this.exibeCasas = false;
        this.exibeImovelFinalidade = false;
        this.exibebuscaSimples = false;
        this.loc = "POINT(-15.776487056813053, -47.79662229999997)";
    }

    public void ordenarEvent() {
        if (this.exibeResultadoPesquisaLocalizacao) {
            RequestContext.getCurrentInstance().update("tablebuscalocalizacao");
        } else if (this.exibeTodosImoveis) {
            RequestContext.getCurrentInstance().update("tabletodosimoveis");
        } else if (this.exibeAptos) {
            RequestContext.getCurrentInstance().update("tableapto");
        } else if (this.exibeCasas) {
            RequestContext.getCurrentInstance().update("tablecasas");
        } else if (this.exibeImovelFinalidade) {
            RequestContext.getCurrentInstance().update("tableimoveisfinalidade");
        } else if (this.exibebuscaSimples) {
            RequestContext.getCurrentInstance().update("tablebuscasimples");
        }
    }

    public void telaBuscaLocalizacaoAvancada() {
        this.exibeBuscaLocalizacaoAvancada = true;
    }

    public void telaBuscaLocalizacaoSimples() {
        this.exibeBuscaLocalizacaoAvancada = false;
    }

    public void telaMapMinhaLocalizacao(boolean status) {
        this.exibeMapMinhaLocalizacao = status;
    }

    public String getPesqOrdenacao() {
        return pesqOrdenacao;
    }

    public void setPesqOrdenacao(String pesqOrdenacao) {
        this.pesqOrdenacao = pesqOrdenacao;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLog() {
        return log;
    }

    public void setLog(double log) {
        this.log = log;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public String getTituloH3() {
        return tituloH3;
    }

    public void setTituloH3(String tituloH3) {
        this.tituloH3 = tituloH3;
    }

    public String getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(String finalidade) {
        this.finalidade = finalidade;
    }

    public boolean isExibeTodosImoveis() {
        return exibeTodosImoveis;
    }

    public void setExibeTodosImoveis(boolean exibeTodosImoveis) {
        this.exibeTodosImoveis = exibeTodosImoveis;
    }

    public boolean isExibePesquisaLocalizacao() {
        return exibePesquisaLocalizacao;
    }

    public void setExibePesquisaLocalizacao(boolean exibePesquisaLocalizacao) {
        this.exibePesquisaLocalizacao = exibePesquisaLocalizacao;
    }

    public boolean isExibeCasas() {
        return exibeCasas;
    }

    public void setExibeCasas(boolean exibeCasas) {
        this.exibeCasas = exibeCasas;
    }

    public boolean isExibeAptos() {
        return exibeAptos;
    }

    public void setExibeAptos(boolean exibeAptos) {
        this.exibeAptos = exibeAptos;
    }

    public boolean isExibeImovelFinalidade() {
        return exibeImovelFinalidade;
    }

    public void setExibeImovelFinalidade(boolean exibeImovelFinalidade) {
        this.exibeImovelFinalidade = exibeImovelFinalidade;
    }

    public boolean isExibebuscaSimples() {
        return exibebuscaSimples;
    }

    public void setExibebuscaSimples(boolean exibebuscaSimples) {
        this.exibebuscaSimples = exibebuscaSimples;
    }

    public boolean isExibeDetalhesCasa() {
        return exibeDetalhesCasa;
    }

    public void setExibeDetalhesCasa(boolean exibeDetalhesCasa) {
        this.exibeDetalhesCasa = exibeDetalhesCasa;
    }

    public boolean isExibeDetalhesApto() {
        return exibeDetalhesApto;
    }

    public void setExibeDetalhesApto(boolean exibeDetalhesApto) {
        this.exibeDetalhesApto = exibeDetalhesApto;
    }

    public String getBusca() {
        return busca;
    }

    public void setBusca(String busca) {
        this.busca = busca;
    }

    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public Apartamento getApto() {
        return apto;
    }

    public void setApto(Apartamento apto) {
        this.apto = apto;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public boolean isExibeResultadoPesquisaLocalizacao() {
        return exibeResultadoPesquisaLocalizacao;
    }

    public void setExibeResultadoPesquisaLocalizacao(boolean exibeResultadoPesquisaLocalizacao) {
        this.exibeResultadoPesquisaLocalizacao = exibeResultadoPesquisaLocalizacao;
    }

    public boolean isExibeAdicionarFavoritos() {
        return exibeAdicionarFavoritos;
    }

    public void setExibeAdicionarFavoritos(boolean exibeAdicionarFavoritos) {
        this.exibeAdicionarFavoritos = exibeAdicionarFavoritos;
    }

    public boolean isExibeRemoverFavoritos() {
        return exibeRemoverFavoritos;
    }

    public void setExibeRemoverFavoritos(boolean exibeRemoverFavoritos) {
        this.exibeRemoverFavoritos = exibeRemoverFavoritos;
    }

    public List<Imovel> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Imovel> favoritos) {
        this.favoritos = favoritos;
    }

    public Usuario getUserSession() {
        return userSession;
    }

    public void setUserSession(Usuario userSession) {
        this.userSession = userSession;
    }

    public float getPesqValorDe() {
        return pesqValorDe;
    }

    public void setPesqValorDe(float pesqValorDe) {
        this.pesqValorDe = pesqValorDe;
    }

    public float getPesqValorAte() {
        return pesqValorAte;
    }

    public void setPesqValorAte(float pesqValorAte) {
        this.pesqValorAte = pesqValorAte;
    }

    public int getPesqQtdeQuarto() {
        return pesqQtdeQuarto;
    }

    public void setPesqQtdeQuarto(int pesqQtdeQuarto) {
        this.pesqQtdeQuarto = pesqQtdeQuarto;
    }

    public int getPesqQtdeVagaGaragem() {
        return pesqQtdeVagaGaragem;
    }

    public void setPesqQtdeVagaGaragem(int pesqQtdeVagaGaragem) {
        this.pesqQtdeVagaGaragem = pesqQtdeVagaGaragem;
    }

    public int getPesqAreaDe() {
        return pesqAreaDe;
    }

    public void setPesqAreaDe(int pesqAreaDe) {
        this.pesqAreaDe = pesqAreaDe;
    }

    public int getPesqAreaAte() {
        return pesqAreaAte;
    }

    public void setPesqAreaAte(int pesqAreaAte) {
        this.pesqAreaAte = pesqAreaAte;
    }

    public String getPesqFinalidade() {
        return pesqFinalidade;
    }

    public void setPesqFinalidade(String pesqFinalidade) {
        this.pesqFinalidade = pesqFinalidade;
    }

    public int getPesqQtdeSuite() {
        return pesqQtdeSuite;
    }

    public void setPesqQtdeSuite(int pesqQtdeSuite) {
        this.pesqQtdeSuite = pesqQtdeSuite;
    }

    public boolean isExibeBuscaLocalizacaoAvancada() {
        return exibeBuscaLocalizacaoAvancada;
    }

    public void setExibeBuscaLocalizacaoAvancada(boolean exibeBuscaLocalizacaoAvancada) {
        this.exibeBuscaLocalizacaoAvancada = exibeBuscaLocalizacaoAvancada;
    }

    public String getPesqTipo() {
        return pesqTipo;
    }

    public void setPesqTipo(String pesqTipo) {
        this.pesqTipo = pesqTipo;
    }

    public boolean isExibeMedidaDeDistanciaKm() {
        return exibeMedidaDeDistanciaKm;
    }

    public void setExibeMedidaDeDistanciaKm(boolean exibeMedidaDeDistanciaKm) {
        this.exibeMedidaDeDistanciaKm = exibeMedidaDeDistanciaKm;
    }

    public boolean isExibeMapMinhaLocalizacao() {
        return exibeMapMinhaLocalizacao;
    }

    public void setExibeMapMinhaLocalizacao(boolean exibeMapMinhaLocalizacao) {
        this.exibeMapMinhaLocalizacao = exibeMapMinhaLocalizacao;
    }

    public String getComentarioTxt() {
        return comentarioTxt;
    }

    public void setComentarioTxt(String comentarioTxt) {
        this.comentarioTxt = comentarioTxt;
    }

    public Comentario getComentario() {
        return comentario;
    }

    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
    }

    public List<Imagem> listImagens() {
        ImagemManager imagemManager = new ImagemManager();
        List<Imagem> imagens = imagemDao.listarImagensPorImovel(imovel);
        imagemManager.criarImagens(imagens, null);
        return imagens;
    }

//    Mensagens
    public void mensagemInformativa(String titulo, String msg) {
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(titulo, msg);
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        fc.addMessage(null, fm);
    }

    public void mensagemErro(String titulo, String msg) {
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(titulo, msg);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        fc.addMessage(null, fm);
    }

    public void mensagemAlerta(String titulo, String msg) {
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(titulo, msg);
        fm.setSeverity(FacesMessage.SEVERITY_WARN);
        fc.addMessage(null, fm);
    }
}
