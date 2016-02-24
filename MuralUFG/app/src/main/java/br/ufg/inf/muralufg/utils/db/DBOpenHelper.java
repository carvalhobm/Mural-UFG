package br.ufg.inf.muralufg.utils.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.muralufg.model.News;
import br.ufg.inf.muralufg.model.AcademicUnits;

public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(Context context) {
        super(context, DBInfo.getDBName(), null, DBInfo.getDBVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DBInfo.createDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DBInfo.deleteDB(db);
        onCreate(db);
    }

    public void ResetDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        DBInfo.resetDB(db);
        db.close();
    }

    public void ResetNews() {
        SQLiteDatabase db = this.getWritableDatabase();
        DBInfo.resetNews(db);
        db.close();
    }

    public void ResetAcademicUnits() {
        SQLiteDatabase db = this.getWritableDatabase();
        DBInfo.resetAcademicUnits(db);
        db.close();
    }

    public int addNews(News news) {
        ContentValues values = new ContentValues();
        values.put(DBInfo.getColumnTitle(), news.getTitle());
        values.put(DBInfo.getColumnNews(), news.getNews());
        values.put(DBInfo.getColumnPhoto(), news.getPhoto());
        values.put(DBInfo.getColumnAuthor(), news.getAuthor());
        values.put(DBInfo.getColumnAuthorbelongs(), news.getAuthorBelongs());
        values.put(DBInfo.getColumnDatetime(), news.getDatetime());
        values.put(DBInfo.getColumnIsreaded(), 0);
        values.put(DBInfo.getColumnRelevance(), news.getRelevance());
        values.put(DBInfo.getColumnURL(), news.getUrl());

        SQLiteDatabase db = this.getWritableDatabase();
        int id = (int) db.insert(DBInfo.getTableNameNews(), null, values);
        db.close();
        return id;
    }

    public void addAcademicUnits(AcademicUnits academicUnits) {
        ContentValues values = new ContentValues();
        values.put(DBInfo.getColumnUnitID(), academicUnits.getUnitID());
        values.put(DBInfo.getColumnUnit(), academicUnits.getUnit());
        values.put(DBInfo.getColumnIsChecked(), academicUnits.getIsChecked());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(DBInfo.getTableNameAcademicUnits(), null, values);
        db.close();
    }

    public void deleteNewsRow(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        DBInfo.deleteNewsRow(db, id);
        db.close();
    }

    public List<News> getNews() {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT *, CASE WHEN (" + DBInfo.getColumnIsreaded() + " = 0 AND " + DBInfo.getColumnRelevance() + " = 1) THEN 3 WHEN (" + DBInfo.getColumnIsreaded() + " = 0) THEN 2 ELSE 1 END AS newsorder FROM " + DBInfo.getTableNameNews() + " ORDER BY newsorder DESC, " + DBInfo.getColumnDatetime() + " DESC";

        Cursor cursor = db.rawQuery(query, null);
        ArrayList<News> newslst = new ArrayList<>();

        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                News news = getNews(cursor.getInt(0));

                newslst.add(news);
            }
            cursor.close();
        } else {
            newslst.clear();
        }
        db.close();
        return newslst;
    }

    public List<AcademicUnits> getAcademicUnits() {
        SQLiteDatabase db = this.getWritableDatabase();


        String query = "SELECT * FROM " + DBInfo.getTableNameAcademicUnits();

        Cursor cursor = db.rawQuery(query, null);
        ArrayList<AcademicUnits> academicunitslst = new ArrayList<>();

        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                AcademicUnits academicunits = new AcademicUnits();
                academicunits.setId(cursor.getInt(0));
                academicunits.setUnitID(cursor.getInt(1));
                academicunits.setUnit(cursor.getString(2));
                academicunits.setIsChecked(cursor.getInt(3));

                academicunitslst.add(academicunits);
            }
            cursor.close();
        } else {
            academicunitslst.clear();
        }
        db.close();
        return academicunitslst;
    }

    public News getNews(int ID) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + DBInfo.getTableNameNews() + " WHERE " + DBInfo.getColumnID() + " = " + ID;
        Cursor cursor = db.rawQuery(query, null);

        News news = new News();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                news.setId(cursor.getInt(0));
                news.setTitle(cursor.getString(1));
                news.setNews(cursor.getString(2));
                news.setPhoto(cursor.getString(3));
                news.setAuthor(cursor.getString(4));
                news.setAuthorBelongs(cursor.getInt(5));
                news.setDatetime(cursor.getString(6));
                news.setIsReaded(cursor.getInt(7));
                news.setRelevance(cursor.getInt(8));
                news.setUrl(cursor.getString(9));
            }
            cursor.close();
        } else {
            news = null;
        }
        db.close();
        return news;
    }

    public void editNews(News news) {
        ContentValues values = new ContentValues();
        values.put(DBInfo.getColumnTitle(), news.getTitle());
        values.put(DBInfo.getColumnNews(), news.getNews());
        values.put(DBInfo.getColumnPhoto(), news.getPhoto());
        values.put(DBInfo.getColumnAuthor(), news.getAuthor());
        values.put(DBInfo.getColumnAuthorbelongs(), news.getAuthorBelongs());
        values.put(DBInfo.getColumnDatetime(), news.getDatetime());
        values.put(DBInfo.getColumnIsreaded(), news.getIsReaded());
        values.put(DBInfo.getColumnRelevance(), news.getRelevance());
        values.put(DBInfo.getColumnURL(), news.getUrl());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(DBInfo.getTableNameNews(), values, DBInfo.getColumnID() + " = " + news.getId(), null);
        db.close();
    }

    public void editAcademicUnits(AcademicUnits academicUnits) {
        ContentValues values = new ContentValues();
        values.put(DBInfo.getColumnUnitID(), academicUnits.getUnitID());
        values.put(DBInfo.getColumnUnit(), academicUnits.getUnit());
        values.put(DBInfo.getColumnIsChecked(), academicUnits.getIsChecked());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(DBInfo.getTableNameAcademicUnits(), values, DBInfo.getColumnID() + " = " + academicUnits.getId(), null);
        db.close();
    }

    public void readedNews(News news) {
        ContentValues values = new ContentValues();
        values.put(DBInfo.getColumnTitle(), news.getTitle());
        values.put(DBInfo.getColumnNews(), news.getNews());
        values.put(DBInfo.getColumnPhoto(), news.getPhoto());
        values.put(DBInfo.getColumnAuthor(), news.getAuthor());
        values.put(DBInfo.getColumnAuthorbelongs(), news.getAuthorBelongs());
        values.put(DBInfo.getColumnDatetime(), news.getDatetime());
        values.put(DBInfo.getColumnIsreaded(), 1);
        values.put(DBInfo.getColumnRelevance(), news.getRelevance());
        values.put(DBInfo.getColumnURL(), "");

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(DBInfo.getTableNameNews(), values, DBInfo.getColumnID() + " = " + news.getId(), null);
        db.close();
    }

    public Boolean canDisplayNews(News news) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + DBInfo.getTableNameAcademicUnits() + " WHERE " + DBInfo.getColumnIsChecked() + " = 1 AND " + DBInfo.getColumnUnitID() + " = " + news.getAuthorBelongs();

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
}
