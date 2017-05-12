package com.sheepy.redbot;

import java.util.ArrayList;

/**
 * Created by Troels "Sheepyhead" Jessen on 4/29/2017.
 */
public interface PostListener {
    public void newPosts(ArrayList<RedditThread> posts);
}
