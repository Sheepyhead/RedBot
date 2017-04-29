import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.modules.IModule;
import sx.blah.discord.util.DiscordException;

import java.util.ArrayList;

/**
 * Created by kairy on 4/28/2017.
 */
public class Bot implements IModule, IListener<ReadyEvent>, PostListener {

    private static final String CHANNEL_NAME = "pf_subreddit_talk";
    private String moduleName = "RedBot";
    private String moduleVersion = "1.0";
    private String moduleMinimumVersion = "2.3.0";
    private String author = "Sheepyhead";
    private IChannel channel;

    public static String BOT_VERSION = "1.0.0";
    public static IDiscordClient client;
    private FeedReader feedReader;

    public Bot(FeedReader feedReader) {
        this.feedReader = feedReader;
        feedReader.addPostListener(this);
    }

    @Override
    public boolean enable(IDiscordClient dclient) {
        client = dclient;
        EventDispatcher dispatcher = client.getDispatcher();
        dispatcher.registerListener(this);
        return true;
    }

    @Override
    public void disable() {

    }

    @Override
    public String getName() {
        return moduleName;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getVersion() {
        return moduleVersion;
    }

    @Override
    public String getMinimumDiscord4JVersion() {
        return moduleMinimumVersion;
    }

    public static IDiscordClient createClient(String token, boolean login) { // Returns a new instance of the Discord client
        ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
        clientBuilder.withToken(token); // Adds the login info to the builder
        try {
            if (login) {
                return clientBuilder.login(); // Creates the client instance and logs the client in
            } else {
                return clientBuilder.build(); // Creates the client instance but it doesn't log the client in yet, you would have to call client.login() yourself
            }
        } catch (DiscordException e) { // This is thrown if there was a problem building the client
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void handle(ReadyEvent readyEvent) {
        client.changeUsername("RedBot");
        client.changePlayingText("v. " + BOT_VERSION);
        for (IChannel nchannel : client.getChannels()) {
            if (!nchannel.getName().equals(CHANNEL_NAME)) continue;
            channel = nchannel;
            channel.sendMessage("I AWAKE!!!");
            break;
        }
    }

    @Override
    public void newPosts(ArrayList<ArrayList<String>> posts) {
        if (posts.isEmpty()) return;
        System.out.println("Call to newposts registered: " + posts);
        String message = "**" + posts.get(0).get(0) + "**, by *"
                + posts.get(0).get(1) + "*\n"
                + "**Thread:** " + posts.get(1).get(0);
        channel.sendMessage(message);
    }
}
