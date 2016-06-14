/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifpb.tccii.imogeo.managedbean;

import br.com.ifpb.tccii.imogeo.entidades.Imagem;
import br.com.ifpb.tccii.imogeo.sessionbeans.ImagemDao;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Germano
 */
@ViewScoped
@ManagedBean
public class ImagemMB implements Serializable {

    @EJB
    ImagemDao imagemDao;
    
    private Imagem imagem = new Imagem();

    private List<Imagem> imagens;

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    public void inserirImagem() {

        try {
            this.imagemDao.inserirImagem(imagem);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            imagem = new Imagem();

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Foto adicionada", "Foto adicionada"));
        }

    }

    public void processFileUpload(FileUploadEvent uploadEvent) {
        try {
            imagem.setFoto(uploadEvent.getFile().getContents());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void listaImagens() {

        try {
            ServletContext sContext = (ServletContext) FacesContext
                    .getCurrentInstance().getExternalContext().getContext();

            imagens = imagemDao.listarImagens();

            File folder = new File(sContext.getRealPath("/temp"));
            if (!folder.exists()) {
                folder.mkdirs();
            }

            for (Imagem i : imagens) {
                String nomeArquivo = i.getId() + ".jpg";
                String arquivo = sContext.getRealPath("/temp") + File.separator
                        + nomeArquivo;

                criaArquivo(i.getFoto(), arquivo);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void criaArquivo(byte[] bytes, String arquivo) throws IOException {
        FileOutputStream fos;

        try {
            fos = new FileOutputStream(arquivo);
            fos.write(bytes);

            fos.flush();
            fos.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
