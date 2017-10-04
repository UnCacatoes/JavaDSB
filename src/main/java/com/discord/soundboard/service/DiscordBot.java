package com.discord.soundboard.service;

import org.springframework.stereotype.Service;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageEvent;
import sx.blah.discord.handle.impl.obj.Message;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.audio.AudioPlayer;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DiscordBot implements IListener<Event> {

    private IDiscordClient client;

    public DiscordBot(){
        String token = Configuration.getConfiguration().getToken();
        client = DiscordClient.createClient(token, true);
        client.getDispatcher().registerListener(this);
    }

    public String playSong(String song){
        if(this.getActiveVoiceChannel()==null) {
            this.sendMessage("Not in a voice channel, use '@SoundBoard join'");
            return "Not in a voice channel, use '@SoundBoard join'";
        }

        // Get the AudioPlayer object for the guild
        AudioPlayer audioP = AudioPlayer.getAudioPlayerForGuild(this.getActiveGuild());

        // Find a song given the search term
        File[] songDir = getSongsDir().listFiles(file -> file.getName().contains(song));

        if(songDir == null || songDir.length == 0)
            return "Could not find song";

        // Stop the playing track
        audioP.clear();

        // Play the found song
        try {
            audioP.queue(songDir[0]);
            return "Now playing: " + songDir[0].getName();
        } catch (IOException | UnsupportedAudioFileException e) {
            this.sendMessage("There was an issue playing that song");
            e.printStackTrace();
            return "There was an issue playing that song";
        }
    }

    public ArrayList<String> getSongsAsStrings(){
        File file = getSongsDir();
        ArrayList<String> songs = new ArrayList<String>();
        for(File song:file.listFiles()){
            songs.add(song.getName());
        }
        return songs;
    }

    public File[] getSongs(){
        File file = getSongsDir();
        return file.listFiles();
    }

    public File getFileFromString(String name) {
        File file = getSongsDir();
        for (File song : file.listFiles()) {
            if (song.getName().equals(name)) {
                return song;
            }
        }
        return null;
    }

    public IGuild getActiveGuild(){
        if(client.getGuilds().isEmpty()){
            return null;
        }
        return client.getGuilds().get(0);
    }

    public IVoiceChannel getActiveVoiceChannel(){
        if(client.getConnectedVoiceChannels().isEmpty()){
            return null;
        }
        return client.getConnectedVoiceChannels().get(0);
    }

    public IChannel getDefaultChannel(){
        if(this.getActiveGuild()==null){
            return null;
        }
        return this.getActiveGuild().getDefaultChannel();
    }

    private File getSongsDir(){
        File file = new File(Configuration.getConfiguration().getSoundsPath());
        if(!file.exists()){
            file.mkdir();
        }
        return file;
    }

    @Override
    public void handle(Event event) {
        if(event instanceof ReadyEvent) {
            System.out.println("Client is ready !");
        }
        else if(event instanceof MessageEvent){
            Message message = (Message)((MessageEvent) event).getMessage();
            if (message.getMentions()!=null && message.getMentions().contains(this.client.getOurUser())){
                this.commandHandler(message);
            }
        }
    }

    private void commandHandler(Message message) {
        List<String> arr = Arrays.asList(message.getContent().toLowerCase().split(" "));
        String command = arr.get(1);
        List<String> args = arr.subList(2, arr.size());

        if(command.equals("join")){
            if(message.getAuthor().getVoiceStateForGuild(message.getGuild()).getChannel() != null){
                message.getAuthor().getVoiceStateForGuild(message.getGuild()).getChannel().join();
            }
            else{
                sendMessage("User '" + message.getAuthor().getName() + "' is not in a voice channel");
            }
        }
        else if(command.equals("list")){

        }
        else if(command.equals("stop") || command.equals("quit") || command.equals("leave")){
            message.getAuthor().getVoiceStateForGuild(message.getGuild()).getChannel().leave();
        }
    }


    private void sendMessage(String s) {
        this.client.getGuilds().get(0).getDefaultChannel().sendMessage(s);
    }
}
