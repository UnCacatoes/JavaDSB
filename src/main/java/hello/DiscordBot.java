package hello;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageEvent;
import sx.blah.discord.handle.impl.obj.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiscordBot implements IListener<Event> {

    private IDiscordClient client;
    private MusicBot musicBot;

    public DiscordBot(){
        String token = "MzI2MTE2NzQ2MzcxMTM3NTM4.DJAfGg.6G6jg69dhVWBYP7_j6zkepos9aE";
        client = DiscordClient.createClient(token, true);
        client.getDispatcher().registerListener(this);
        this.musicBot = new MusicBot(client);
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
            message.getAuthor().getVoiceStateForGuild(message.getGuild()).getChannel().join();
        }
        else if(command.equals("list")){
            this.musicBot.printMusicList(message.getChannel());
        }
        else if(command.equals("stop") || command.equals("quit") || command.equals("leave")){
            message.getAuthor().getVoiceStateForGuild(message.getGuild()).getChannel().leave();
        }
    }
}
