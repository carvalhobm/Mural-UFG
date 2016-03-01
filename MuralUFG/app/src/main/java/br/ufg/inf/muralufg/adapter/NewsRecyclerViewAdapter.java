package br.ufg.inf.muralufg.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import br.ufg.inf.muralufg.model.News;
import br.ufg.inf.muralufg.utils.db.DBOpenHelper;
import br.ufg.inf.muralufg.fragment.InboxFragment;
import br.ufg.inf.muralufg.activity.NewsViewActivity;
import br.ufg.inf.muralufg.R;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {

    private static final String TAG = "NewsRecyclerViewAdapter";
    private static Context context;
    private final List<News> news;

    public NewsRecyclerViewAdapter(Context context, List<News> news) {
        NewsRecyclerViewAdapter.context = context;
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
            newsViewHolder.id = news.get(i).getId();
        } catch (Exception e) {
            Log.e(NewsRecyclerViewAdapter.TAG, e.getMessage());
            return;
        }

        if (news.get(i).getIsReaded() == 0) {
            newsViewHolder.title.setTextAppearance(context, R.style.boldText);
            newsViewHolder.news.setTextAppearance(context, R.style.boldText);
            newsViewHolder.authordatetime.setTextAppearance(context, R.style.boldText);
            newsViewHolder.isreaded.setEnabled(false);
            if (news.get(i).getRelevance() != 1)
                newsViewHolder.isUrgent.setVisibility(View.GONE);
        } else {
            newsViewHolder.title.setTextAppearance(context, R.style.normalText);
            newsViewHolder.news.setTextAppearance(context, R.style.normalText);
            newsViewHolder.authordatetime.setTextAppearance(context, R.style.normalText);
            newsViewHolder.isreaded.setEnabled(true);
            if (news.get(i).getRelevance() != 1)
                newsViewHolder.isUrgent.setVisibility(View.GONE);
        }

        newsViewHolder.title.setText(news.get(i).getTitle());
        newsViewHolder.news.setText(news.get(i).getNewsText());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        newsViewHolder.authordatetime.setText(news.get(i).getAuthor() + " - " + simpleDateFormat.format(Long.parseLong(news.get(i).getDatetime())));
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
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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

                    if (InboxFragment.getSnackbar() != null && InboxFragment.getSnackBarView().getVisibility() == View.VISIBLE) {
                        InboxFragment.getSnackbar().dismiss();
                    } else if (db.getNews(id).getIsReaded() == 1 || InboxFragment.isOnline(context))
                        context.startActivity(new Intent(context, NewsViewActivity.class).putExtra("id", id));
                    else
                        Snackbar.make(InboxFragment.getCoordinatorLayoutView(), context.getString(R.string.NoNetwork), Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }
}