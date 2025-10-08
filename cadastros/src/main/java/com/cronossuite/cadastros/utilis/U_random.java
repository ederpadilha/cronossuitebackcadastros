package com.cronossuite.cadastros.utilis;


import java.security.SecureRandom;

public class U_random {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int TOKEN_LENGTH = 30;
    private static final SecureRandom random = new SecureRandom();

    public String generateToken() {
        StringBuilder token = new StringBuilder(TOKEN_LENGTH);
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            token.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return token.toString();
    }

    /**
     * Gera uma palavra aleatória com o número de caracteres especificado
     * 
     * @param length número de caracteres da palavra
     * @return string aleatória com o tamanho especificado
     */
    public String generateRandomWord(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("O tamanho deve ser maior que zero");
        }

        StringBuilder word = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            word.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return word.toString();
    }

    /**
     * Gera uma palavra aleatória apenas com letras (sem números)
     * 
     * @param length número de caracteres da palavra
     * @return string aleatória apenas com letras
     */
    public String generateRandomLettersOnly(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("O tamanho deve ser maior que zero");
        }

        String lettersOnly = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder word = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            word.append(lettersOnly.charAt(random.nextInt(lettersOnly.length())));
        }
        return word.toString();
    }

    /**
     * Gera uma palavra aleatória apenas com números
     * 
     * @param length número de dígitos
     * @return string aleatória apenas com números
     */
    public String generateRandomNumbers(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("O tamanho deve ser maior que zero");
        }

        String numbersOnly = "0123456789";
        StringBuilder word = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            word.append(numbersOnly.charAt(random.nextInt(numbersOnly.length())));
        }
        return word.toString();
    }

}