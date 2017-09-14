package com.discord.soundboard.api;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by guillaume on 14/09/17.
 */
public class SongManager {

    private static final String MUSIC_PATH = "songs";

    public static ArrayList<String> getSongsAsStrings(){
        File file = new File(MUSIC_PATH);
        ArrayList<String> songs = new ArrayList<String>();
        for(File song:file.listFiles()){
            songs.add(song.getName());
        }
        return songs;
    }

    public static File[] getSongs(){
        File file = new File(MUSIC_PATH);
        return file.listFiles();
    }

    public static File getFileFromString(String name){
        File file = new File(MUSIC_PATH);
        for(File song: file.listFiles()){
            if(song.getName().equals(name)){
                return song;
            }
        }
        return null;
    }
}
