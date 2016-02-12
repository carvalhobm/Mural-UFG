package br.ufg.inf.muralufg.newsfilter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;

import br.ufg.inf.muralufg.db.DBOpenHelper;
import br.ufg.inf.muralufg.R;

class AcademicUnitsAdapter extends ArrayAdapter<AcademicUnits> {

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
        convertView = inflater.inflate(R.layout.filter_row, parent, false);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.FilterCB);

        cb.setText(academicUnitslst.get(position).get_unit());
        if (academicUnitslst.get(position).get_ischecked() == 1)
            cb.setChecked(true);
        else
            cb.setChecked(false);

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DBOpenHelper db = new DBOpenHelper(context);
                AcademicUnits academicUnits = academicUnitslst.get(position);
                if (academicUnits.get_ischecked() == 0)
                    academicUnits.set_ischecked(1);
                else
                    academicUnits.set_ischecked(0);
                db.editAcademicUnits(academicUnits);
            }
        });

        return convertView;
    }
}
