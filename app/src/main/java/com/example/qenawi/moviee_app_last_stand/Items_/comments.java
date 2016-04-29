package com.example.qenawi.moviee_app_last_stand.Items_;

import java.io.Serializable;

/**
 * Created by QEnawi on 4/6/2016.
 */
public class comments implements Serializable {
    String author,content,url;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
