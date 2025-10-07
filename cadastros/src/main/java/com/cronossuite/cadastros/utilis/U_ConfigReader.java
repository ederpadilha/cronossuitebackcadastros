package com.cronossuite.cadastros.utilis;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cronossuite.cadastros.repository.model.cfg.Cgf;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@Component
public class U_ConfigReader {

    private JsonObject jsonObject;

    @Value("${rlcl.config.file.path:config/default-config.json}")
    private String fileCfg;

    public U_ConfigReader() {
        // Construtor vazio para Spring - a inicialização será feita via @PostConstruct
    }
    
    @jakarta.annotation.PostConstruct
    public void init() {
        loadConfig();
    }
    
    private void loadConfig() {
        try {
            FileReader reader = new FileReader(fileCfg);
            JsonElement jsonElement = JsonParser.parseReader(reader);
            jsonObject = jsonElement.getAsJsonObject();
            reader.close();
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo de configuração não encontrado: " + fileCfg);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de configuração: " + fileCfg);
            e.printStackTrace();
        }
    }

     public Cgf getValue() {
        Cgf c = new Cgf();
        Gson gson = new Gson();
        c = gson.fromJson(jsonObject, Cgf.class);
        return c;
    }
    
}