package com.example.solar.newsapp;

public class NewsArticle {

    String title;
    String content;
    String section;
    String link;
    String author;
    String date;
    int img;

    public NewsArticle() {
    }


    public NewsArticle(String title, String section, String link ) {
        this.title = title;
        this.section = section;
        this.link = link;
    }


    public NewsArticle(String title, String section, String link , String date , String author) {
        this.title = title;
        this.section = section;
        this.link = link;
        this.date = date;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String d) {
        this.date = d;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

}
