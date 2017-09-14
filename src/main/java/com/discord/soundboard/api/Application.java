package com.discord.soundboard.api;

import com.discord.soundboard.bot.DiscordBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        DiscordBot bot = new DiscordBot();
        ApiController.setDiscordBot(bot);
    }

}
