import sx.blah.discord.api.IDiscordClient;

/**
 * Created by kairy on 4/28/2017.
 */
public class Driver {
    public static String BOT_TOKEN = "MzA3NTg4MDM0NzkzMDQ2MDE4.C-XOtg.SmtKgHjBPxzKdzNLB3TF-nJu7e8";
    public static void main(String[] args) {

        FeedReader feedReader = new FeedReader(10);
        Bot bot = new Bot(feedReader);
        IDiscordClient client = Bot.createClient(BOT_TOKEN, true);
        bot.enable(client);
    }
}
