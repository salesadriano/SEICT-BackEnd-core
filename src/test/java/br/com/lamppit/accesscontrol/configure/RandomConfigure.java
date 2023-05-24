package br.com.lamppit.accesscontrol.configure;

import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

public class RandomConfigure {

    public String randomNames() {

        ThreadLocalRandom gerador = ThreadLocalRandom.current();
        int tamanhoNome = gerador.nextInt(3, 10);
        char primeiraLetraNome = (char) gerador.nextInt(65, 90);
        StringBuilder nome = new StringBuilder().append(primeiraLetraNome);


        for (int i = 1; i < tamanhoNome; i++) {
            char letra = (char) gerador.nextInt(97, 122);
            nome.append(letra);
        }

        return nome.toString();

    }

    public String randomPassword(int len) {

        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < len; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }

    public Long randomLong() {
        return ThreadLocalRandom.current().nextLong(1, 100);
    }
}
