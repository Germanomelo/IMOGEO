/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifpb.tccii.imogeo.utils;

import br.com.ifpb.tccii.imogeo.entidades.Imagem;
import br.com.ifpb.tccii.imogeo.entidades.Usuario;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author germano
 */
public class ImagemManager {

    public String nomeImagem(Usuario user, UploadedFile arq) {
        String nome = null;
        String externsaoImagem = "";

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String data = sdf.format(calendar.getTime());

        Random gerador = new Random();
        int num = gerador.nextInt(9000)+1000;
        
        if (arq.getContentType().equals("image/png")) {
            externsaoImagem = ".png";
        } else if (arq.getContentType().equals("image/jpeg")) {
            externsaoImagem = ".jpg";
        }

        nome = user.getId() + data + num + externsaoImagem;
        System.out.println(nome);
        return nome;
    }

    public void criarImagens(List<Imagem> imagens, Imagem img) {

        try {
            ServletContext sContext = (ServletContext) FacesContext
                    .getCurrentInstance().getExternalContext().getContext();

            File folder = new File(sContext.getRealPath("/temp"));
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String arquivo;
            if (imagens == null) {
                arquivo = sContext.getRealPath("/temp") + File.separator + img.getNome();
                File file = new File(arquivo);
                if (!file.exists()) {
//                    System.out.println("Arquivo não existe");
                    criaArquivo(img.getFoto(), arquivo);
                } else {
//                    System.out.println("Arquivo já existe");
                }
            } else {
                for (Imagem i : imagens) {
                    arquivo = sContext.getRealPath("/temp") + File.separator + i.getNome();
                    File file = new File(arquivo);
                    if (!file.exists()) {
//                        System.out.println("Arquivo não existe");
                        criaArquivo(i.getFoto(), arquivo);
//                        System.out.println("Arquivo criado");
                    } else {
//                        System.out.println("Arquivo já existe");
                    }
                }
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
