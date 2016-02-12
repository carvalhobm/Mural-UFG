package br.ufg.inf.muralufg.news.filter;

public class AcademicUnits {
    private int id;
    private int unitID;
    private String unit;
    private int isChecked;

    public AcademicUnits() {
    }

    public AcademicUnits(int unitID, String unit, int isChecked) {
        this.unitID = unitID;
        this.unit = unit;
        this.isChecked = isChecked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUnitID() {
        return unitID;
    }

    public void setUnitID(int unitID) {
        this.unitID = unitID;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }
}
