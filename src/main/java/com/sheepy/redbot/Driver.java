package com.sheepy.redbot;

import sx.blah.discord.api.IDiscordClient;

import java.io.*;


/**
 * Created by Troels "Sheepyhead" Jessen on 4/28/2017.
 */
public class Driver {
    public static String BOT_TOKEN = "MzA3NTg4MDM0NzkzMDQ2MDE4.DI2qgQ.5DI89McUh0v9fqIo3RNXEmnVdwo";
    public static String DEFAULT_OUTPUT_FILE = "output.txt";

    public static void main(String[] args) {
        try {
            File outputFile = new File(DEFAULT_OUTPUT_FILE);

            outputFile.delete();
            outputFile.createNewFile();
            System.setOut(new PrintStream(new FileOutputStream(DEFAULT_OUTPUT_FILE)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FeedReader feedReader = new FeedReader(10);
        RedBot bot = new RedBot();
        IDiscordClient client = RedBot.createClient(BOT_TOKEN, true);
        bot.enable(client);
    }
}
