package br.ufg.inf.muralufg.NewsFilter;

public class AcademicUnits {
    private int _id;
    private int _unitid;
    private String _unit;
    private int _ischecked;

    public AcademicUnits() {
    }

    public AcademicUnits(int id, int unitid, String unit, int ischecked) {
        this._id = id;
        this._unitid = unitid;
        this._unit = unit;
        this._ischecked = ischecked;
    }

    public AcademicUnits(int unitid, String unit, int ischecked) {
        this._unitid = unitid;
        this._unit = unit;
        this._ischecked = ischecked;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_unitid() {
        return _unitid;
    }

    public void set_unitid(int _unitid) {
        this._unitid = _unitid;
    }

    public String get_unit() {
        return _unit;
    }

    public void set_unit(String _unit) {
        this._unit = _unit;
    }

    public int get_ischecked() {
        return _ischecked;
    }

    public void set_ischecked(int _ischecked) {
        this._ischecked = _ischecked;
    }
}
