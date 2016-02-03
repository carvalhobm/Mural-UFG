package br.ufg.inf.muralufg.DB;

import android.database.sqlite.SQLiteDatabase;

class DBInfo {
    //DB Info
    private static final String DB_NAME = "MURALUFG.DB";
    private static final int DB_VERSION = 1;

    //Tables
    private static final String TABLE_NAME_NEWS = "news";
    private static final String TABLE_NAME_ACADEMITUNITS = "academicunits";

    //Columns
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_Title = "title";
    private static final String COLUMN_News = "news";
    private static final String COLUMN_Photo = "photo";
    private static final String COLUMN_Author = "author";
    private static final String COLUMN_AuthorBelongs = "authorbelongs";
    private static final String COLUMN_DateTime = "datetime";
    private static final String COLUMN_IsReaded = "isreaded";
    private static final String COLUMN_Relevance = "relevance";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_UnitID = "unitid";
    private static final String COLUMN_Unit = "unit";
    private static final String COLUMN_IsChecked = "ischecked";

    //Creates
    private static final String CREATE_TABLE_NEWS = "CREATE TABLE "
            + TABLE_NAME_NEWS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_Title + " TEXT NOT NULL, "
            + COLUMN_News + " TEXT NOT NULL, "
            + COLUMN_Photo + " TEXT, "
            + COLUMN_Author + " TEXT NOT NULL, "
            + COLUMN_AuthorBelongs + " INTEGER NOT NULL, "
            + COLUMN_DateTime + " TEXT NOT NULL, "
            + COLUMN_IsReaded + " INTEGER NOT NULL, "
            + COLUMN_Relevance + " INTEGER NOT NULL, "
            + COLUMN_URL + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_ACADEMITUNITS = "CREATE TABLE "
            + TABLE_NAME_ACADEMITUNITS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_UnitID + " INTEGER NOT NULL, "
            + COLUMN_Unit + " TEXT NOT NULL, "
            + COLUMN_IsChecked + " INTEGER NOT NULL);";

    //Deletes
    private static final String DELETE_TABLE_NEWS = "DROP TABLE IF EXISTS " + TABLE_NAME_NEWS;
    private static final String DELETE_TABLE_ACADEMITUNITS = "DROP TABLE IF EXISTS " + TABLE_NAME_ACADEMITUNITS;

    //Get's
    public static String getDB_NAME() {
        return DB_NAME;
    }

    public static int getDB_VERSION() {
        return DB_VERSION;
    }

    public static String getTABLE_NAME_NEWS() {
        return TABLE_NAME_NEWS;
    }

    public static String getTABLE_NAME_ACADEMITUNITS() {
        return TABLE_NAME_ACADEMITUNITS;
    }

    public static String getCOLUMN_ID() {
        return COLUMN_ID;
    }

    public static String getCOLUMN_Title() {
        return COLUMN_Title;
    }

    public static String getCOLUMN_News() {
        return COLUMN_News;
    }

    public static String getCOLUMN_Photo() {
        return COLUMN_Photo;
    }

    public static String getCOLUMN_Author() {
        return COLUMN_Author;
    }

    public static String getCOLUMN_AuthorBelongs() {
        return COLUMN_AuthorBelongs;
    }

    public static String getCOLUMN_DateTime() {
        return COLUMN_DateTime;
    }

    public static String getCOLUMN_IsReaded() {
        return COLUMN_IsReaded;
    }

    public static String getCOLUMN_Relevance() {
        return COLUMN_Relevance;
    }

    public static String getCOLUMN_URL() {
        return COLUMN_URL;
    }

    public static String getCOLUMN_UnitID() {
        return COLUMN_UnitID;
    }

    public static String getCOLUMN_Unit() {
        return COLUMN_Unit;
    }

    public static String getCOLUMN_IsChecked() {
        return COLUMN_IsChecked;
    }

    public static void CreateDB(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NEWS);
        db.execSQL(CREATE_TABLE_ACADEMITUNITS);
    }

    public static void DeleteDB(SQLiteDatabase db) {
        db.execSQL(DELETE_TABLE_NEWS);
        db.execSQL(DELETE_TABLE_ACADEMITUNITS);
    }

    public static void ResetDB(SQLiteDatabase db) {
        DeleteDB(db);
        CreateDB(db);
    }

    public static void ResetNews(SQLiteDatabase db) {
        db.execSQL(DELETE_TABLE_NEWS);
        db.execSQL(CREATE_TABLE_NEWS);
    }

    public static void ResetAcademicUnits(SQLiteDatabase db) {
        db.execSQL(DELETE_TABLE_ACADEMITUNITS);
        db.execSQL(CREATE_TABLE_ACADEMITUNITS);
    }

    public static void deleteNewsRow(SQLiteDatabase db, long id) {
        db.delete(TABLE_NAME_NEWS, COLUMN_ID + " = " + id, null);
    }

    public static void deleteAcademicUnitsRow(SQLiteDatabase db, long id) {
        db.delete(TABLE_NAME_ACADEMITUNITS, COLUMN_ID + " = " + id, null);
    }
}
