package com.discord.soundboard.service;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

public class DiscordClient {



  public static IDiscordClient createClient(String token, boolean login) {
    ClientBuilder clientBuilder = new ClientBuilder();
    clientBuilder.withToken(token);
    try {
      if (login) {
        return clientBuilder.login();
      } else {
        return clientBuilder.build();
      }
    } catch (DiscordException e) {
      e.printStackTrace();
      return null;
    }
  }
}
