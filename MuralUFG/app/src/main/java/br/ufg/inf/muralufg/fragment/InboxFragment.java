package br.ufg.inf.muralufg.fragment;


import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;
import java.util.Locale;

import br.ufg.inf.muralufg.R;
import br.ufg.inf.muralufg.utils.db.DBOpenHelper;
import br.ufg.inf.muralufg.model.News;
import br.ufg.inf.muralufg.adapter.NewsRecyclerViewAdapter;
import br.ufg.inf.muralufg.model.AcademicUnits;
import br.ufg.inf.muralufg.activity.NewsFilterActivity;


public class InboxFragment extends Fragment {

    public static View coordinatorLayoutView;
    public static Snackbar snackbar;
    public static View snackBarView;
    private static NewsRecyclerViewAdapter newsRecyclerViewAdapter;
    private View rootview;
    private Context context;
    private DBOpenHelper db;
    private List<News> news;
    private RecyclerView rvNews;
    private Boolean canDelete;

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        rootview = inflater.inflate(R.layout.fragment_news, container, false);
        context = rootview.getContext();
        coordinatorLayoutView = rootview.findViewById(R.id.snackbarPosition);

        if (!isOnline(context))
            Snackbar.make(coordinatorLayoutView, getString(R.string.NoNetwork), Snackbar.LENGTH_LONG).show();

        populateFilters();
        updateList();

        rvNews.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(getActivity().getApplicationContext())
                .build());

        final SwipeRefreshLayout srNews = (SwipeRefreshLayout) rootview.findViewById(R.id.SRNews);
        srNews.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isOnline(context)) {
                    if (snackbar != null) {
                        snackbar.dismiss();
                    }
                    srNews.setRefreshing(true);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            updateList();
                            srNews.setRefreshing(false);
                        }
                    }, 2000);
                } else {
                    srNews.setRefreshing(false);
                    Snackbar.make(coordinatorLayoutView, getString(R.string.NoNetwork), Snackbar.LENGTH_LONG).show();
                }
            }
        });

        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                canDelete = true;

                newsRecyclerViewAdapter.remove(position);

                final View.OnClickListener clickListener = new View.OnClickListener() {
                    public void onClick(View v) {
                        if (canDelete) {
                            canDelete = false;
                            news.add(position, db.getNews(((NewsRecyclerViewAdapter.NewsViewHolder) viewHolder).id));
                            newsRecyclerViewAdapter.notifyItemInserted(position);
                        }
                    }
                };

                snackbar = Snackbar.make(coordinatorLayoutView, getString(R.string.snackbarNewsRemoved), Snackbar.LENGTH_LONG)
                        .setAction(R.string.snackbarUndo, clickListener);

                snackBarView = snackbar.getView();
                snackBarView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                    @Override
                    public void onViewAttachedToWindow(View v) {
                        snackBarView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onViewDetachedFromWindow(View v) {
                        snackBarView.setVisibility(View.GONE);
                        if (canDelete) {
                            db.deleteNewsRow(((NewsRecyclerViewAdapter.NewsViewHolder) viewHolder).id);
                            newsRecyclerViewAdapter.notifyDataSetChanged();
                        }
                    }
                });
                snackbar.show();
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                manager.cancel(((NewsRecyclerViewAdapter.NewsViewHolder) viewHolder).id);
            }
        });
        swipeToDismissTouchHelper.attachToRecyclerView(rvNews);

        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
        if (!isOnline(context))
            Snackbar.make(coordinatorLayoutView, getString(R.string.NoNetwork), Snackbar.LENGTH_LONG).show();
    }

    private void updateList() {
        rvNews = (RecyclerView) rootview.findViewById(R.id.RVNews);
        rvNews.setLayoutManager(new LinearLayoutManager(context));

        db = new DBOpenHelper(context);
        news = db.getNews();

        for (int i = news.size() - 1; i >= 0; i--) {
            if (news.get(i).getRelevance() == 1)
                continue;
            if (!db.canDisplayNews(news.get(i)))
                news.remove(i);
        }

        newsRecyclerViewAdapter = new NewsRecyclerViewAdapter(context, news);
        rvNews.setAdapter(newsRecyclerViewAdapter);

        ImageView imgBG = (ImageView) rootview.findViewById(R.id.imgBG);
        if (newsRecyclerViewAdapter.getItemCount() == 0) {
            if ("pt_Br".equals(Locale.getDefault().toString()) || "pt_PT".equals(Locale.getDefault().toString()))
                imgBG.setImageResource(R.drawable.ufg_no_news_ptbr);
            else
                imgBG.setImageResource(R.drawable.ufg_no_news_en);
        }
        else
            imgBG.setImageDrawable(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_inbox_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actionFilter) {
            context.startActivity(new Intent(context, NewsFilterActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void populateFilters() {
        db = new DBOpenHelper(context);

        String[] units = getResources().getStringArray(R.array.academicunit);
        if (db.getAcademicUnits().size() != units.length) {
            db.ResetAcademicUnits();
            for (int i = 0; i < units.length; i++) {
                AcademicUnits aux = new AcademicUnits(i, units[i], 1);
                db.addAcademicUnits(aux);
            }
        }
    }
}
