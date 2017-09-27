package com.discord.soundboard.bot;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Configuration {
    private static Configuration conf;
    private String token;
    private String soundsPath;

    public static Configuration getConfiguration(){
        if(conf == null){
            conf = new Configuration();
            Gson gson = new Gson();
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader("src/main/resources/conf/configuration.json"));
                conf = gson.fromJson(br, Configuration.class);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return conf;
    }

    public String getToken(){
        return this.token;
    }

    public String getSoundsPath(){
        return this.soundsPath;
    }
}
