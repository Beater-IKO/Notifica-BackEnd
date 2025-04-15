package br.com.bd_notifica.utils;

import org.mindrot.jbcrypt.BCrypt;

public class Criptografia {

    public static String gerarHash(String senhaPlana) {
        return BCrypt.hashpw(senhaPlana, BCrypt.gensalt(10));
    }

    public static boolean verificarSenha(String senhaPlana, String hashSalvo) {
        return BCrypt.checkpw(senhaPlana, hashSalvo);
    }
}
