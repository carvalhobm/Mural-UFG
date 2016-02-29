package br.ufg.inf.muralufg.model;

import android.os.Build;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Created by Bruno on 28/02/2016.
 */
@Config(sdk = Build.VERSION_CODES.KITKAT)
public class NewsTest {

    News news = new News();

    @Before
    public void setUp() throws Exception {
        news = new News(1, "title", "news", "photo", "author", 1, "datetime", 1, "url");
    }

    @Test
    public void testGetId() throws Exception {
        Assert.assertEquals(1, news.getId());
    }

    @Test
    public void testSetId() throws Exception {
        news.setId(2);
        Assert.assertEquals(2, news.getId());
    }

    @Test
    public void testGetTitle() throws Exception {
        Assert.assertEquals("title", news.getTitle());
    }

    @Test
    public void testSetTitle() throws Exception {
        news.setTitle("title-test");
        Assert.assertEquals("title-test", news.getTitle());
    }

    @Test
    public void testGetNews() throws Exception {
        Assert.assertEquals("news", news.getNews());
    }

    @Test
    public void testSetNews() throws Exception {
        news.setNews("news-test");
        Assert.assertEquals("news-test", news.getNews());
    }

    @Test
    public void testGetPhoto() throws Exception {
        Assert.assertEquals("photo", news.getPhoto());
    }

    @Test
    public void testSetPhoto() throws Exception {
        news.setPhoto("photo-test");
        Assert.assertEquals("photo-test", news.getPhoto());
    }

    @Test
    public void testGetAuthor() throws Exception {
        Assert.assertEquals("author", news.getAuthor());
    }

    @Test
    public void testSetAuthor() throws Exception {
        news.setAuthor("author-test");
        Assert.assertEquals("author-test", news.getAuthor());
    }

    @Test
    public void testGetAuthorBelongs() throws Exception {
        Assert.assertEquals(1, news.getAuthorBelongs());
    }

    @Test
    public void testSetAuthorBelongs() throws Exception {
        news.setAuthorBelongs(2);
        Assert.assertEquals(2, news.getAuthorBelongs());
    }

    @Test
    public void testGetDatetime() throws Exception {
        Assert.assertEquals("datetime", news.getDatetime());
    }

    @Test
    public void testSetDatetime() throws Exception {
        news.setDatetime("datetime-test");
        Assert.assertEquals("datetime-test", news.getDatetime());
    }

    @Test
    public void testGetIsReaded() throws Exception {
        news.setIsReaded(1);
        Assert.assertEquals(1, news.getIsReaded());
    }

    @Test
    public void testSetIsReaded() throws Exception {
        news.setIsReaded(2);
        Assert.assertEquals(2, news.getIsReaded());
    }

    @Test
    public void testGetRelevance() throws Exception {
        Assert.assertEquals(1, news.getRelevance());
    }

    @Test
    public void testSetRelevance() throws Exception {
        news.setRelevance(3);
        Assert.assertEquals(3, news.getRelevance());
    }

    @Test
    public void testGetUrl() throws Exception {
        Assert.assertEquals("url", news.getUrl());
    }

    @Test
    public void testSetUrl() throws Exception {
        news.setUrl("url-test");
        Assert.assertEquals("url-test", news.getUrl());
    }

    @Test
    public void testSecondConstructor() throws Exception {
        News news2 = new News("title-2", "news-2", "photo-2", "author-2", 10, "datetime-2", 10, "url-2");
        Assert.assertEquals(0, news2.getId());
    }

}