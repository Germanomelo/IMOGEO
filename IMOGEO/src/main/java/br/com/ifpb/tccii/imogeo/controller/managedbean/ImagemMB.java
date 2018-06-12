/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifpb.tccii.imogeo.controller.managedbean;

import br.com.ifpb.tccii.imogeo.model.Imagem;
import br.com.ifpb.tccii.imogeo.controller.dao.ImagemDAO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Germano
 */
@ViewScoped
@ManagedBean
public class ImagemMB implements Serializable {

    private List<StreamedContent> fotos;
    
    @EJB
    ImagemDAO imagemDao;
    
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

    public List<Imagem> getImagens() {
        listaImagens();
        return imagens;
    }

    public void setImagens(List<Imagem> imagens) {
        this.imagens = imagens;
    }
    
    public void fileUpload(FileUploadEvent event) {
        UploadedFile arq = event.getFile();
        System.out.println(arq.getContentType());
        String externsaoImagem="";
        
        if(arq.getContentType().equals("image/png")){
            externsaoImagem = ".png";
        }else if(arq.getContentType().equals("image/jpeg")){
            externsaoImagem = ".jpeg";
        }
        
        byte[] bimagem = event.getFile().getContents();
        
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	String data = sdf.format(calendar.getTime());
        
        imagem.setNome(data+externsaoImagem);
        imagem.setFoto(bimagem);
        System.out.println(imagem.toString());
        
        imagens.add(imagem);
        imagem = new Imagem();
        
//        listaImagens();
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
