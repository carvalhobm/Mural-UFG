package br.ufg.inf.muralufg.activity;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

import br.ufg.inf.muralufg.R;
import br.ufg.inf.muralufg.fragment.InboxFragment;
import br.ufg.inf.muralufg.model.News;
import br.ufg.inf.muralufg.utils.db.DBOpenHelper;
import br.ufg.inf.muralufg.utils.json.NewsJSON;

public class NewsViewActivity extends AbstractBaseActivity {

    private static final String TAG = "NewsViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int id = getIntent().getExtras().getInt("id");
        DBOpenHelper db = new DBOpenHelper(getBaseContext());
        News news = db.getNews(id);

        if (news.getIsReaded() == 0 && !InboxFragment.isOnline(this)) {
            finish();
            Intent intent = new Intent(this, InboxActivity.class);
            startActivity(intent);
            return;
        }

        try {
            populate(db, news);
        } catch (ExecutionException e) {
            Log.e(NewsViewActivity.TAG, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(NewsViewActivity.TAG, e.getMessage());
        }
    }

    @Override
    protected void setActivityContentView() {
        setContentView(R.layout.activity_news_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_view, menu);
        return true;
    }

    private void populate(DBOpenHelper db, News newsIn) throws ExecutionException, InterruptedException {
        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(newsIn.getId());
        News news = newsIn;

        if (newsIn.getIsReaded() == 0) {
            ProgressDialog progressDialog = ProgressDialog.show(this, newsIn.getTitle(), getString(R.string.pdLoading), true, true);
            NewsJSON json = new NewsJSON(newsIn);
            news = json.getNews(newsIn);
            db.editNews(news);
            db.readedNews(news);
            progressDialog.dismiss();
        }

        ImageView newsIMG = (ImageView) findViewById(R.id.NewsIMG);

        if (news.getPhoto().length() > 0) {
            Picasso.with(getApplicationContext()).load(news.getPhoto()).into(newsIMG);
        } else {
            newsIMG.setVisibility(View.GONE);
        }

        TextView newsTitle = (TextView) findViewById(R.id.NewsTitle);
        newsTitle.setText(news.getTitle());

        TextView newsAuthorDateTime = (TextView) findViewById(R.id.NewsAuthorDateTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        newsAuthorDateTime.setText(news.getAuthor() + " - " + simpleDateFormat.format(Long.parseLong(news.getDatetime())));

        TextView newsNews = (TextView) findViewById(R.id.NewsNews);
        newsNews.setText(news.getNewsText());
    }
}