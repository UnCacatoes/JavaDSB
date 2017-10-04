package com.discord.soundboard.api;

import com.discord.soundboard.service.DiscordBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private DiscordBot bot;
    
    @RequestMapping("/")
    public String index() {
        return "Welcome to the API root !";
    }

    @RequestMapping("/play/{song}")
    public String play(@PathVariable(value="song") String song ) {
        return bot.playSong(song);
    }

    @RequestMapping("/songs")
    public ArrayList<String> songs(){
        return bot.getSongsAsStrings();
    }

    @PostMapping("/songs")
    public void uploadSong(){
    }

}
