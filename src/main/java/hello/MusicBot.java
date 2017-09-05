package hello;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.util.audio.AudioPlayer;

import java.io.File;
import java.util.ArrayList;

public class MusicBot {

    private IDiscordClient client;
    private static final String MUSIC_PATH = "/home/guillaume/workspace/web/DiscordSoundBoard/server/songs";
    private File[] musicList;

    public MusicBot(IDiscordClient client) {
        this.client = client;
        this.musicList = new File(MUSIC_PATH).listFiles();
    }

    public void playMusic(String music, IGuild guild){
        //AudioPlayer.getAudioPlayerForGuild(guild).queue();
    }

    public void printMusicList(IChannel channel){
        for(File music: this.getMusics()){
            channel.sendMessage(music.getName());
        }
    }

    private File[] getMusics() {
        return this.musicList;
    }
}
