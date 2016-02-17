package br.ufg.inf.muralufg.activity;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

import br.ufg.inf.muralufg.utils.db.DBOpenHelper;
import br.ufg.inf.muralufg.fragment.InboxFragment;
import br.ufg.inf.muralufg.model.News;
import br.ufg.inf.muralufg.utils.json.NewsJSON;
import br.ufg.inf.muralufg.R;
import android.util.Log;

public class NewsViewActivity extends AbstractBaseActivity {

    private static final String TAG = "NewsViewActivity";

    private static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int ID = getIntent().getExtras().getInt("id");
        DBOpenHelper db = new DBOpenHelper(getBaseContext());
        News news = db.getNews(ID);

        if (news.getIsReaded() == 0 && !InboxFragment.isOnline(this)) {
            finish();
            Intent intent = new Intent(this, InboxActivity.class);
            startActivity(intent);
            return;
        }

        try {
            Populate(db, news);
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

    private void Populate(DBOpenHelper db, News news) throws ExecutionException, InterruptedException {
        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(news.getId());

        if (news.getIsReaded() == 0) {
            progressDialog = ProgressDialog.show(this, news.getTitle(), getString(R.string.pdLoading), true, true);
            NewsJSON json = new NewsJSON(getApplicationContext(), news);
            news = json.getNews(news);
            db.editNews(news);
            db.readedNews(news);
            progressDialog.dismiss();
        }

        ImageView NewsIMG = (ImageView) findViewById(R.id.NewsIMG);

        if (news.getPhoto().length() > 0) {
            Picasso.with(getApplicationContext()).load(news.getPhoto()).into(NewsIMG);
        } else {
            NewsIMG.setVisibility(View.GONE);
        }

        TextView NewsTitle = (TextView) findViewById(R.id.NewsTitle);
        NewsTitle.setText(news.getTitle());

        TextView NewsAuthorDateTime = (TextView) findViewById(R.id.NewsAuthorDateTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        NewsAuthorDateTime.setText(news.getAuthor() + " - " + simpleDateFormat.format(Long.parseLong(news.getDatetime())));

        TextView NewsNews = (TextView) findViewById(R.id.NewsNews);
        NewsNews.setText(news.getNews());
    }
}