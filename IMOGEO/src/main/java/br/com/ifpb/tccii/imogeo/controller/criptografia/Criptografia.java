/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.ifpb.tccii.imogeo.controller.criptografia;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Mano
 */
public class Criptografia {
    
      public String encriptar(String senha){
         String senhaCriptografada = hashEncriptacao(senha);

        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        
        sb.append(senhaCriptografada.substring(0, 30));
        sb.reverse();
        
        sb2.append(senhaCriptografada.substring(30, 60));
        sb2.reverse();
        
        sb3.append(senhaCriptografada.substring(60, 88));
        sb3.reverse();
        
        sb.append(sb2.toString());
        sb.append(sb3.toString());
        sb.append(senhaCriptografada.substring(88, 90));

        return sb.toString();
    }

    private String hashEncriptacao(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.update(senha.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(digest.digest());
        } catch (NoSuchAlgorithmException ns) {
            ns.printStackTrace();
            
            return senha;
        }
    }
}
