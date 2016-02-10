package br.ufg.inf.muralufg;

import junit.framework.TestCase;

import java.util.concurrent.ExecutionException;

import br.ufg.inf.muralufg.News.News;
import br.ufg.inf.muralufg.News.NewsJSON;

public class NewsTest extends TestCase{

    public void testNewsGetPhoto() throws ExecutionException, InterruptedException {
        News news = new News("Title", "News", "" ,"Author", 0, "1435264970000", 0, "https://dl.dropboxusercontent.com/s/plt99l1zd5ux7eu/01.json");
        NewsJSON json = new NewsJSON(null, news);
        news = json.getNews(news);
        Boolean resulBoolean = news.get_photo().isEmpty();
        assertFalse(resulBoolean);
    }
}
