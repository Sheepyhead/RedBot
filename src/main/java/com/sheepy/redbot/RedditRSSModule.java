package com.sheepy.redbot;

import sx.blah.discord.handle.impl.obj.Channel;
import sx.blah.discord.handle.obj.IChannel;

import java.util.ArrayList;

/**
 * Created by Troels "Sheepyhead" Jessen on 17/05/13.
 */
public class RedditRSSModule implements DiscordBotModule, PostListener {

    private FeedReader feedReader;
    private IChannel channel;

    public RedditRSSModule(IChannel channel, String redditUrl, int interval)
    {
        this.channel = channel;
        if (!redditUrl.endsWith("/")) redditUrl += "/";
        redditUrl += ".rss";
        feedReader = new FeedReader(interval);
        feedReader.addPostListener(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void newPosts(ArrayList<RedditThread> posts) {
        if (posts.isEmpty()) return;
        for (RedditThread thread : posts) {
            channel.sendMessage(thread.toString());
            System.out.println(thread.toString());
        }
    }
}
