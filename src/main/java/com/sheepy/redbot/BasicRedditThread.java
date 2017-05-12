package com.sheepy.redbot;

import com.sun.syndication.feed.synd.SyndEntry;

/**
 * Created by Troels "Sheepyhead" Jessen on 17/05/12.
 */
public class BasicRedditThread implements RedditThread {
    private String title;
    private String author;
    private String url;

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BasicRedditThread(SyndEntry entry)
    {
        title = entry.getTitle();
        author = entry.getAuthor();
        url = entry.getLink();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicRedditThread that = (BasicRedditThread) o;

        return url.equals(that.url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public String toString() {
        return "**" + title + "**, by *"
                + author + "*\n"
                + "**Thread:** " + url;
    }
}
