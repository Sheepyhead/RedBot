package com.sheepy.redbot;

import sx.blah.discord.api.IDiscordClient;


/**
 * Created by Troels "Sheepyhead" Jessen on 4/28/2017.
 */
public class Driver {
    public static String BOT_TOKEN = "MzA3NTg4MDM0NzkzMDQ2MDE4.C-XOtg.SmtKgHjBPxzKdzNLB3TF-nJu7e8";
    public static void main(String[] args) {

        FeedReader feedReader = new FeedReader(10);
        RedBot bot = new RedBot();
        IDiscordClient client = RedBot.createClient(BOT_TOKEN, true);
        bot.enable(client);
    }
}
