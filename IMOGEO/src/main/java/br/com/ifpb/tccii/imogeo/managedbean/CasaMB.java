package br.com.ifpb.tccii.imogeo.managedbean;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.ifpb.tccii.imogeo.entidades.Anuncio;
import br.com.ifpb.tccii.imogeo.entidades.Endereco;
import br.com.ifpb.tccii.imogeo.entidades.Imagem;
import br.com.ifpb.tccii.imogeo.entidades.Usuario;
import br.com.ifpb.tccii.imogeo.entidades.especializacao.Casa;
import br.com.ifpb.tccii.imogeo.sessionbeans.CasaDao;
import br.com.ifpb.tccii.imogeo.sessionbeans.ImagemDao;
import br.com.ifpb.tccii.imogeo.utils.ImagemManager;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Germano
 */
@ManagedBean(name = "casaMB")
@ViewScoped
public class CasaMB implements Serializable {

    private String loc;
    private boolean cadastrarCasa = true;
    private boolean cadastrarEnderecoCasa = false;
    private boolean cadastrarImagensCasa = false;
    private List<Imagem> imagens = new ArrayList<>();
    private Imagem imagem = new Imagem();
    private Casa casa = new Casa();
    private Endereco endereco = new Endereco();
    @EJB
    private CasaDao casaDao;
    @EJB
    private ImagemDao imagemDao;

    public void telaCadastrarCasa() {
        this.cadastrarCasa = true;
        this.cadastrarEnderecoCasa = false;
        this.cadastrarImagensCasa = false;
    }

    public void telaCadastrarEnderecoCasa() {
        this.cadastrarCasa = false;
        this.cadastrarEnderecoCasa = true;
        this.cadastrarImagensCasa = false;
    }

    public void telaCadastrarImagensCasa() {
        this.cadastrarCasa = false;
        this.cadastrarEnderecoCasa = false;
        this.cadastrarImagensCasa = true;
    }

    public void telaNull() {
        this.cadastrarCasa = false;
        this.cadastrarEnderecoCasa = false;
        this.cadastrarImagensCasa = false;
    }

    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    public boolean isCadastrarCasa() {
        return cadastrarCasa;
    }

    public boolean isCadastrarImagensCasa() {
        return cadastrarImagensCasa;
    }

    public void setCadastrarImagensCasa(boolean cadastrarImagensCasa) {
        this.cadastrarImagensCasa = cadastrarImagensCasa;
    }

    public boolean isCadastrarEnderecoCasa() {
        return cadastrarEnderecoCasa;
    }

    public void setCadastrarEnderecoCasa(boolean cadastrarEnderecoCasa) {
        this.cadastrarEnderecoCasa = cadastrarEnderecoCasa;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    public List<Imagem> getImagens() {
        return imagens;
    }

    public void setImagens(List<Imagem> imagens) {
        this.imagens = imagens;
    }

    public void addCasa() throws ParseException {
        try {
            Geometry g1 = new WKTReader().read(this.loc.toString());
            this.endereco.setLocalizacao((Point) g1);
            this.endereco.setImovel(this.casa);
            this.casa.setUsuario(this.getUsuarioSession());
            this.casa.setAnuncio(new Anuncio());
            this.casa.setEndereco(this.endereco);
            this.casaDao.inserirCasa(this.casa);

            telaCadastrarImagensCasa();

        } catch (Exception e) {
            mensagemErro("Erro!", "Erro ao cadastrar casa");
        }
    }

    public String addImagens() {
        try {
            if (imagens.size() > 0) {
                casa.setImagens(imagens);
                casaDao.atualizarCasa(casa);
            }
            mensagemInformativa("Sucesso", "Casa cadastrada com sucesso.");
            return "meus-imoveis.jsf";
        } catch (Exception e) {
            mensagemErro("Erro!", "Erro ao cadastrar casa");
        }
        return null;
    }

    public void fileUpload(FileUploadEvent event) {
        UploadedFile arq = event.getFile();
        byte[] bimagem = event.getFile().getContents();

        ImagemManager imagemManager = new ImagemManager();

        imagem.setNome(imagemManager.nomeImagem(this.getUsuarioSession(), arq));
        imagem.setFoto(bimagem);
        imagem.setImovel(casa);
        System.out.println(imagem.toString());

        imagens.add(imagem);
        imagem = new Imagem();

        mensagemInformativa("Sucesso!", "Imagem enviada com sucesso!");
    }

    public Usuario getUsuarioSession() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        Usuario user = (Usuario) session.getAttribute("usuario");
        return user;
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
