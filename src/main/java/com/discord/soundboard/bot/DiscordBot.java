package com.discord.soundboard.bot;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageEvent;
import sx.blah.discord.handle.impl.obj.Message;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.audio.AudioPlayer;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DiscordBot implements IListener<Event> {

    private IDiscordClient client;
    private IGuild activeGuild;
    private IChannel activeChannel;
    private IVoiceChannel activeVoiceChannel;

    public DiscordBot(){
        String token = "MzI2MTE2NzQ2MzcxMTM3NTM4.DJAfGg.6G6jg69dhVWBYP7_j6zkepos9aE";
        client = DiscordClient.createClient(token, true);
        client.getDispatcher().registerListener(this);
    }

    @Override
    public void handle(Event event) {
        if(event instanceof ReadyEvent) {
            System.out.println("Client is ready !");
        }
        else if(event instanceof MessageEvent){
            Message message = (Message)((MessageEvent) event).getMessage();
            if (message.getMentions().contains(this.client.getOurUser())){
                this.commandHandler(message);
            }
        }
    }

    private void commandHandler(Message message) {
        List<String> arr = Arrays.asList(message.getContent().toLowerCase().split(" "));
        String command = arr.get(1);
        List<String> args = arr.subList(2, arr.size());

        if(command.equals("join")){
            this.activeGuild = message.getGuild();
            this.activeVoiceChannel = message.getAuthor().getVoiceStateForGuild(message.getGuild()).getChannel();
            this.activeVoiceChannel.join();
        }
        else if(command.equals("list")){

        }
        else if(command.equals("stop") || command.equals("quit") || command.equals("leave")){
            message.getAuthor().getVoiceStateForGuild(message.getGuild()).getChannel().leave();
        }
    }

    public void playSong(String song){

        IVoiceChannel botVoiceChannel = client.getVoiceChannels().get(0);

        if(botVoiceChannel == null) {
            this.sendMessage("Not in a voice channel, join one and then use joinvoice");
            return;
        }

        // Get the AudioPlayer object for the guild
        AudioPlayer audioP = AudioPlayer.getAudioPlayerForGuild(this.activeGuild);

        // Find a song given the search term
        File[] songDir = new File("music")
                .listFiles(file -> file.getName().contains(song));

        if(songDir == null || songDir.length == 0)
            return;

        // Stop the playing track
        audioP.clear();

        // Play the found song
        try {
            audioP.queue(songDir[0]);
        } catch (IOException | UnsupportedAudioFileException e) {
            sendMessage("There was an issue playing that song.");
            e.printStackTrace();
        }

        sendMessage("Now playing: " + songDir[0].getName());
    }

    private void sendMessage(String s) {
        this.activeChannel.sendMessage(s);
    }
}
