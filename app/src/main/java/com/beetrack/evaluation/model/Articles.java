package com.beetrack.evaluation.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by mbot on 2/21/18.
 */

public class Articles implements Serializable {

    private String status;
    private int totalResults;
    private List<Article> articles;

    public Articles() {

    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
