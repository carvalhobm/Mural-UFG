package br.ufg.inf.muralufg.activity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import java.util.List;

import br.ufg.inf.muralufg.model.AcademicUnits;
import br.ufg.inf.muralufg.adapter.AcademicUnitsAdapter;
import br.ufg.inf.muralufg.utils.db.DBOpenHelper;
import br.ufg.inf.muralufg.R;

public class NewsFilterActivity extends AbstractBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        populate();
    }

    @Override
    protected void setActivityContentView() {
        setContentView(R.layout.activity_news_filter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_filter, menu);
        return true;
    }

    private void populate() {
        DBOpenHelper db = new DBOpenHelper(getBaseContext());

        List<AcademicUnits> academicUnits = db.getAcademicUnits();

        ListView filterlst = (ListView) findViewById(R.id.FilterList);
        AcademicUnitsAdapter academicUnitsAdapter = new AcademicUnitsAdapter(getApplicationContext(), academicUnits);
        filterlst.setAdapter(academicUnitsAdapter);
    }
}
