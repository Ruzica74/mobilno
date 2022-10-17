package com.example.mymobapp.model;

import java.io.Serializable;
import java.util.List;

public class News implements Serializable {

    private String status;
    private String totalResult;
    private List<Article> articles;

    public News(String status, String totalResult, List<Article> articles) {
        this.status = status;
        this.totalResult = totalResult;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(String totalResult) {
        this.totalResult = totalResult;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
