package com.sheepy.redbot;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Troels "Sheepyhead" Jessen on 4/28/2017.
 */
public class FeedReader {

    /*
     * A map of {[post_title, username], [comment_link, thread_link]}
     */
    private ArrayList<RedditThread> newestPosts;
    private ArrayList<PostListener> listeners;
    private String url = "https://www.reddit.com/r/Pathfinder_RPG/new/.rss";
    public static int SLEEP_TIME = 10;
    private boolean startRun = true;

    public FeedReader(int refreshTime){
        System.out.println("STARTING FEEDREADER");
        listeners = new ArrayList<PostListener>();
        newestPosts = new ArrayList<RedditThread>();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("RUNNING FEEDREADER");
                while (!Thread.interrupted()) {
                    grabNewestPosts();
                    try {
                        Thread.sleep(refreshTime* 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    public void grabNewestPosts()
    {
        System.out.println("POST GRABBING STARTED");
        try (CloseableHttpClient client = HttpClients.createMinimal()) {
            HttpUriRequest request = new HttpGet(url);
            request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
            try (CloseableHttpResponse response = client.execute(request);
                 InputStream stream = response.getEntity().getContent()) {
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(stream));
                System.out.println(feed.getTitle());
                List<SyndEntryImpl> entries = feed.getEntries();
                ArrayList<RedditThread> newPosts = new ArrayList<>();
                for (SyndEntryImpl e : entries)
                {
                    /*
                    ArrayList<String> list1 = new ArrayList<String>(2);
                    list1.add(e.getTitle());
                    list1.add(e.getAuthor());
                    ArrayList<String> list2 = new ArrayList<String>(2);
                    //System.out.println(e);
                    for (Object link : e.getLinks())
                    {
                        SyndLink slink = (SyndLink) link;
                        list2.add(slink.getHref());
                    }
                    newPosts.add(list1);
                    newPosts.add(list2);
                    */
                    newPosts.add(new BasicRedditThread(e));
                }
                if (newPosts.equals(newestPosts))
                {
                } else {
                    if (startRun)
                    {
                        startRun = false;
                    } else {
                        ArrayList<RedditThread> difference = mapDifference(newestPosts, newPosts);
                        System.out.println("Difference: " + difference);
                        if (!difference.isEmpty()) {
                            System.out.println("CALLING newPosts");
                            newPosts(difference);
                        }
                    }

                    newestPosts = newPosts;
                    System.out.println(newestPosts);
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // convert InputStream to String
    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

    public void addPostListener(PostListener listener)
    {
        System.out.println("Adding listener: " + listener);
        listeners.add(listener);
        System.out.println("Listeners: " + listeners);
    }

    public void removePostListener(PostListener listener)
    {
        listeners.remove(listener);
    }

    private void newPosts(ArrayList<RedditThread> posts)
    {
        System.out.println("NEW POST");
        System.out.println(posts);
        System.out.println("ALERTING LISTENERS: " + listeners);
        for (PostListener pl : listeners) {
            pl.newPosts(posts);
        }
    }

    public static ArrayList<RedditThread> mapDifference(ArrayList<RedditThread> oldPosts, ArrayList<RedditThread> newPosts) {
        ArrayList<RedditThread> difference = new ArrayList<>();
        if (!oldPosts.equals(newPosts)) {
            difference.addAll(newPosts);
            for (RedditThread thread : oldPosts) {
                if (difference.contains(thread))
                    difference.remove(thread);
            }
        }
        return difference;
    }
}
