package br.ufg.inf.muralufg.news;

public class News {
    private int _id;
    private String _title;
    private String _news;
    private String _photo;
    private int _authorbelongs;
    private String _author;
    private String _datetime;
    private int _isreaded;
    private int _relevance;
    private String _url;

    public News() {
    }

    public News(int id, String title, String news, String photo, String author, int authorbelongs, String datetime, int relevance, String url) {
        this._id = id;
        this._title = title;
        this._news = news;
        this._photo = photo;
        this._author = author;
        this._authorbelongs = authorbelongs;
        this._datetime = datetime;
        this._relevance = relevance;
        this._url = url;
    }

    public News(String title, String news, String photo, String author, int authorbelongs, String datetime, int relevance, String url) {
        this._title = title;
        this._news = news;
        this._photo = photo;
        this._author = author;
        this._authorbelongs = authorbelongs;
        this._datetime = datetime;
        this._relevance = relevance;
        this._url = url;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_news() {
        return _news;
    }

    public void set_news(String _news) {
        this._news = _news;
    }

    public String get_photo() {
        return _photo;
    }

    public void set_photo(String _photo) {
        this._photo = _photo;
    }

    public String get_author() {
        return _author;
    }

    public void set_author(String _author) {
        this._author = _author;
    }

    public int get_authorbelongs() {
        return _authorbelongs;
    }

    public void set_authorbelongs(int _authorbelongs) {
        this._authorbelongs = _authorbelongs;
    }

    public String get_datetime() {
        return _datetime;
    }

    public void set_datetime(String _datetime) {
        this._datetime = _datetime;
    }

    public int get_isreaded() {
        return _isreaded;
    }

    public void set_isreaded(int _isreaded) {
        this._isreaded = _isreaded;
    }

    public int get_relevance() {
        return _relevance;
    }

    public void set_relevance(int _relevance) {
        this._relevance = _relevance;
    }

    public String get_url() {
        return _url;
    }

    public void set_url(String _url) {
        this._url = _url;
    }
}
