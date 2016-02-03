package br.ufg.inf.muralufg.NewsView;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

import br.ufg.inf.muralufg.DB.DBOpenHelper;
import br.ufg.inf.muralufg.Inbox_Activity;
import br.ufg.inf.muralufg.Inbox_Fragment;
import br.ufg.inf.muralufg.News.News;
import br.ufg.inf.muralufg.News.NewsJSON;
import br.ufg.inf.muralufg.R;

public class NewsView extends ActionBarActivity {

    private static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int ID = getIntent().getExtras().getInt("id");
        DBOpenHelper db = new DBOpenHelper(getBaseContext());
        News news = db.getNews(ID);

        if (news.get_isreaded() == 0 && !Inbox_Fragment.isOnline(this)) {
            finish();
            Intent intent = new Intent(this, Inbox_Activity.class);
            startActivity(intent);
            return;
        }

        try {
            Populate(db, news);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_view, menu);
        return true;
    }

    private void Populate(DBOpenHelper db, News news) throws ExecutionException, InterruptedException {
        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(news.get_id());

        if (news.get_isreaded() == 0) {
            progressDialog = ProgressDialog.show(this, news.get_title(), getString(R.string.pdLoading), true, true);
            NewsJSON json = new NewsJSON(getApplicationContext(), news);
            news = json.getNews(news);
            db.editNews(news);
            db.readedNews(news);
            progressDialog.dismiss();
        }

        ImageView NewsIMG = (ImageView) findViewById(R.id.NewsIMG);

        if (news.get_photo().length() > 0) {
            Picasso.with(getApplicationContext()).load(news.get_photo()).into(NewsIMG);
        } else {
            NewsIMG.setVisibility(View.GONE);
        }

        TextView NewsTitle = (TextView) findViewById(R.id.NewsTitle);
        NewsTitle.setText(news.get_title());

        TextView NewsAuthorDateTime = (TextView) findViewById(R.id.NewsAuthorDateTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        NewsAuthorDateTime.setText(news.get_author() + " - " + simpleDateFormat.format(Long.parseLong(news.get_datetime())));

        TextView NewsNews = (TextView) findViewById(R.id.NewsNews);
        NewsNews.setText(news.get_news());
    }
}