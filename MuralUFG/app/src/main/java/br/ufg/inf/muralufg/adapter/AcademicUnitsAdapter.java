package br.ufg.inf.muralufg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;

import br.ufg.inf.muralufg.R;
import br.ufg.inf.muralufg.model.AcademicUnits;
import br.ufg.inf.muralufg.utils.db.DBOpenHelper;

public class AcademicUnitsAdapter extends ArrayAdapter<AcademicUnits> {

    private Context context;
    private List<AcademicUnits> academicUnitslst;

    public AcademicUnitsAdapter(Context context, List<AcademicUnits> academicUnitslst) {
        super(context, R.layout.filter_row, academicUnitslst);
        this.context = context;
        this.academicUnitslst = academicUnitslst;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View newView = inflater.inflate(R.layout.filter_row, parent, false);
        CheckBox cb = (CheckBox) newView.findViewById(R.id.FilterCB);

        cb.setText(academicUnitslst.get(position).getUnit());
        if (academicUnitslst.get(position).getIsChecked() == 1)
            cb.setChecked(true);
        else
            cb.setChecked(false);


        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DBOpenHelper db = new DBOpenHelper(context);
                AcademicUnits academicUnits = academicUnitslst.get(position);
                if (academicUnits.getIsChecked() == 0)
                    academicUnits.setIsChecked(1);
                else
                    academicUnits.setIsChecked(0);
                db.editAcademicUnits(academicUnits);
            }
        });

        return newView;
    }
}
