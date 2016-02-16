package br.ufg.inf.muralufg.news;

public class News {
    private int id;
    private String title;
    private String news;
    private String photo;
    private int authorBelongs;
    private String author;
    private String datetime;
    private int isReaded;
    private int relevance;
    private String url;

    public News() {
    }

    public News(int id, String title, String news, String photo, String author, int authorBelongs, String datetime, int relevance, String url) {
        this.id = id;
        this.title = title;
        this.news = news;
        this.photo = photo;
        this.author = author;
        this.authorBelongs = authorBelongs;
        this.datetime = datetime;
        this.relevance = relevance;
        this.url = url;
    }

    public News(String title, String news, String photo, String author, int authorBelongs, String datetime, int relevance, String url) {
        this.title = title;
        this.news = news;
        this.photo = photo;
        this.author = author;
        this.authorBelongs = authorBelongs;
        this.datetime = datetime;
        this.relevance = relevance;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAuthorBelongs() {
        return authorBelongs;
    }

    public void setAuthorBelongs(int authorBelongs) {
        this.authorBelongs = authorBelongs;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getIsReaded() {
        return isReaded;
    }

    public void setIsReaded(int isReaded) {
        this.isReaded = isReaded;
    }

    public int getRelevance() {
        return relevance;
    }

    public void setRelevance(int relevance) {
        this.relevance = relevance;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
