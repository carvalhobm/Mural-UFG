package br.ufg.inf.muralufg.newsfilter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ListView;

import java.util.List;

import br.ufg.inf.muralufg.db.DBOpenHelper;
import br.ufg.inf.muralufg.R;

public class NewsFilter extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_filter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Populate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_filter, menu);
        return true;
    }

    private void Populate() {
        DBOpenHelper db = new DBOpenHelper(getBaseContext());

        List<AcademicUnits> academicUnitses = db.getAcademicUnits();

        ListView filterlst = (ListView) findViewById(R.id.FilterList);
        AcademicUnitsAdapter academicUnitsAdapter = new AcademicUnitsAdapter(getApplicationContext(), academicUnitses);
        filterlst.setAdapter(academicUnitsAdapter);
    }
}
