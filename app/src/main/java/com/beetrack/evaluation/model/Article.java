package com.beetrack.evaluation.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mbot on 2/21/18.
 */

public class Article extends RealmObject implements Serializable {

    @PrimaryKey
    private int id;
    private Source source;
    private String author;
    private String title; //Used like identifier
    private String description;
    private String url;
    private String urlToImage;
    private Date publishedAt;
    private boolean isFavorite;
    private boolean isDeleted;

    public Article() {

    }

    public void setData(Article article) {
        setAuthor(article.getAuthor());
        setTitle(article.getTitle());
        setDescription(article.getDescription());
        setUrl(article.getUrl());
        setUrlToImage(article.getUrlToImage());
        setPublishedAt(article.getPublishedAt());
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
