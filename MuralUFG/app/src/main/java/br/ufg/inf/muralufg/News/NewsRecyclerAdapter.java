package br.ufg.inf.muralufg.News;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import br.ufg.inf.muralufg.DB.DBOpenHelper;
import br.ufg.inf.muralufg.Inbox_Fragment;
import br.ufg.inf.muralufg.NewsView.NewsView;
import br.ufg.inf.muralufg.R;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder> {

    private static Context context;
    private final List<News> news;

    public NewsRecyclerAdapter(Context context, List<News> news) {
        this.context = context;
        this.news = news;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_row, viewGroup, false);
        v.setClickable(true);
        return new NewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder newsViewHolder, int i) {
        try {
            newsViewHolder.id = news.get(i).get_id();
        } catch (Exception e) {
            return;
        }

        if (news.get(i).get_isreaded() == 0) {
            newsViewHolder.title.setTextAppearance(context, R.style.boldText);
            newsViewHolder.news.setTextAppearance(context, R.style.boldText);
            newsViewHolder.authordatetime.setTextAppearance(context, R.style.boldText);
            newsViewHolder.isreaded.setEnabled(false);
            if (news.get(i).get_relevance() != 1)
                newsViewHolder.isUrgent.setVisibility(View.GONE);
        } else {
            newsViewHolder.title.setTextAppearance(context, R.style.normalText);
            newsViewHolder.news.setTextAppearance(context, R.style.normalText);
            newsViewHolder.authordatetime.setTextAppearance(context, R.style.normalText);
            newsViewHolder.isreaded.setEnabled(true);
            if (news.get(i).get_relevance() != 1)
                newsViewHolder.isUrgent.setVisibility(View.GONE);
        }

        newsViewHolder.title.setText(news.get(i).get_title());
        newsViewHolder.news.setText(news.get(i).get_news());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        newsViewHolder.authordatetime.setText(news.get(i).get_author() + " - " + simpleDateFormat.format(Long.parseLong(news.get(i).get_datetime())));
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public void remove(int position) {
        news.remove(position);
        notifyItemRemoved(position);
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        final CardView cv;
        final TextView title;
        final TextView news;
        final TextView authordatetime;
        final CheckBox isreaded;
        final ImageView isUrgent;
        public int id;

        NewsViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.RVNews);
            title = (TextView) itemView.findViewById(R.id.CardTitle);
            news = (TextView) itemView.findViewById(R.id.CardNews);
            authordatetime = (TextView) itemView.findViewById(R.id.CardAuthorDateTime);
            isreaded = (CheckBox) itemView.findViewById(R.id.CardIsReaded);
            isUrgent = (ImageView) itemView.findViewById(R.id.CardImgUrgent);

            final DBOpenHelper db = new DBOpenHelper(context);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Inbox_Fragment.snackbar != null && Inbox_Fragment.snackBarView.getVisibility() == View.VISIBLE) {
                        Inbox_Fragment.snackbar.dismiss();
                    } else if (db.getNews(id).get_isreaded() == 1 || Inbox_Fragment.isOnline(context))
                        context.startActivity(new Intent(context, NewsView.class).putExtra("id", id));
                    else
                        Snackbar.make(Inbox_Fragment.coordinatorLayoutView, context.getString(R.string.NoNetwork), Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }
}