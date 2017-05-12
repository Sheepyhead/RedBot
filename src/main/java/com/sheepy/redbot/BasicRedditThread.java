package com.sheepy.redbot;

import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.StringReader;
import java.util.List;

/**
 * Created by Troels "Sheepyhead" Jessen on 17/05/12.
 */
public class BasicRedditThread implements RedditThread {
    private String title;
    private String author;
    private String url;
    private String postUrl;

    @Override
    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

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
        postUrl = extractPostLink(entry);
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
                + "**Thread:** " + url
                + ((postUrl.isEmpty()) ? "" : "\n**Link:** " + postUrl);
    }

    private String extractPostLink(SyndEntry entry)
    {
        List<SyndContentImpl> contents = entry.getContents();
        String postLink = "";
        for (SyndContentImpl content : contents)
        {
            String value = content.getValue();
            if (value.startsWith("<table>")) {
                postLink = value.split("<td>")[2].split("<a href=\"")[2].split("\"")[0];
            }
        }
        return postLink;
    }
}
