package br.ufg.inf.muralufg.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.muralufg.News.News;
import br.ufg.inf.muralufg.NewsFilter.AcademicUnits;

public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(Context context) {
        super(context, DBInfo.getDB_NAME(), null, DBInfo.getDB_VERSION());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DBInfo.CreateDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DBInfo.DeleteDB(db);
        onCreate(db);
    }

    public void ResetDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        DBInfo.ResetDB(db);
        db.close();
    }

    public void ResetNews() {
        SQLiteDatabase db = this.getWritableDatabase();
        DBInfo.ResetNews(db);
        db.close();
    }

    public void ResetAcademicUnits() {
        SQLiteDatabase db = this.getWritableDatabase();
        DBInfo.ResetAcademicUnits(db);
        db.close();
    }

    public int addNews(News news) {
        ContentValues values = new ContentValues();
        values.put(DBInfo.getCOLUMN_Title(), news.get_title());
        values.put(DBInfo.getCOLUMN_News(), news.get_news());
        values.put(DBInfo.getCOLUMN_Photo(), news.get_photo());
        values.put(DBInfo.getCOLUMN_Author(), news.get_author());
        values.put(DBInfo.getCOLUMN_AuthorBelongs(), news.get_authorbelongs());
        values.put(DBInfo.getCOLUMN_DateTime(), news.get_datetime());
        values.put(DBInfo.getCOLUMN_IsReaded(), 0);
        values.put(DBInfo.getCOLUMN_Relevance(), news.get_relevance());
        values.put(DBInfo.getCOLUMN_URL(), news.get_url());

        SQLiteDatabase db = this.getWritableDatabase();
        int id = (int) db.insert(DBInfo.getTABLE_NAME_NEWS(), null, values);
        db.close();
        return id;
    }

    public void addAcademicUnits(AcademicUnits academicUnits) {
        ContentValues values = new ContentValues();
        values.put(DBInfo.getCOLUMN_UnitID(), academicUnits.get_unitid());
        values.put(DBInfo.getCOLUMN_Unit(), academicUnits.get_unit());
        values.put(DBInfo.getCOLUMN_IsChecked(), academicUnits.get_ischecked());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(DBInfo.getTABLE_NAME_ACADEMITUNITS(), null, values);
        db.close();
    }

    public void deleteNewsRow(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        DBInfo.deleteNewsRow(db, id);
        db.close();
    }

    public List<News> getNews() {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT *, CASE WHEN (" + DBInfo.getCOLUMN_IsReaded() + " = 0 AND " + DBInfo.getCOLUMN_Relevance() + " = 1) THEN 3 WHEN (" + DBInfo.getCOLUMN_IsReaded() + " = 0) THEN 2 ELSE 1 END AS newsorder FROM " + DBInfo.getTABLE_NAME_NEWS() + " ORDER BY newsorder DESC, " + DBInfo.getCOLUMN_DateTime() + " DESC";

        Cursor cursor = db.rawQuery(query, null);
        ArrayList<News> newslst = new ArrayList<>();

        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                News news = new News();
                news.set_id(cursor.getInt(0));
                news.set_title(cursor.getString(1));
                news.set_news(cursor.getString(2));
                news.set_photo(cursor.getString(3));
                news.set_author(cursor.getString(4));
                news.set_authorbelongs(cursor.getInt(5));
                news.set_datetime(cursor.getString(6));
                news.set_isreaded(cursor.getInt(7));
                news.set_relevance(cursor.getInt(8));
                news.set_url(cursor.getString(9));

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


        String query = "SELECT * FROM " + DBInfo.getTABLE_NAME_ACADEMITUNITS();

        Cursor cursor = db.rawQuery(query, null);
        ArrayList<AcademicUnits> academicunitslst = new ArrayList<>();

        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                AcademicUnits academicunits = new AcademicUnits();
                academicunits.set_id(cursor.getInt(0));
                academicunits.set_unitid(cursor.getInt(1));
                academicunits.set_unit(cursor.getString(2));
                academicunits.set_ischecked(cursor.getInt(3));

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

        String query = "SELECT * FROM " + DBInfo.getTABLE_NAME_NEWS() + " WHERE " + DBInfo.getCOLUMN_ID() + " = " + ID;
        Cursor cursor = db.rawQuery(query, null);

        News news = new News();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                news.set_id(cursor.getInt(0));
                news.set_title(cursor.getString(1));
                news.set_news(cursor.getString(2));
                news.set_photo(cursor.getString(3));
                news.set_author(cursor.getString(4));
                news.set_authorbelongs(cursor.getInt(5));
                news.set_datetime(cursor.getString(6));
                news.set_isreaded(cursor.getInt(7));
                news.set_relevance(cursor.getInt(8));
                news.set_url(cursor.getString(9));
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
        values.put(DBInfo.getCOLUMN_Title(), news.get_title());
        values.put(DBInfo.getCOLUMN_News(), news.get_news());
        values.put(DBInfo.getCOLUMN_Photo(), news.get_photo());
        values.put(DBInfo.getCOLUMN_Author(), news.get_author());
        values.put(DBInfo.getCOLUMN_AuthorBelongs(), news.get_authorbelongs());
        values.put(DBInfo.getCOLUMN_DateTime(), news.get_datetime());
        values.put(DBInfo.getCOLUMN_IsReaded(), news.get_isreaded());
        values.put(DBInfo.getCOLUMN_Relevance(), news.get_relevance());
        values.put(DBInfo.getCOLUMN_URL(), news.get_url());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(DBInfo.getTABLE_NAME_NEWS(), values, DBInfo.getCOLUMN_ID() + " = " + news.get_id(), null);
        db.close();
    }

    public void editAcademicUnits(AcademicUnits academicUnits) {
        ContentValues values = new ContentValues();
        values.put(DBInfo.getCOLUMN_UnitID(), academicUnits.get_unitid());
        values.put(DBInfo.getCOLUMN_Unit(), academicUnits.get_unit());
        values.put(DBInfo.getCOLUMN_IsChecked(), academicUnits.get_ischecked());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(DBInfo.getTABLE_NAME_ACADEMITUNITS(), values, DBInfo.getCOLUMN_ID() + " = " + academicUnits.get_id(), null);
        db.close();
    }

    public void readedNews(News news) {
        ContentValues values = new ContentValues();
        values.put(DBInfo.getCOLUMN_Title(), news.get_title());
        values.put(DBInfo.getCOLUMN_News(), news.get_news());
        values.put(DBInfo.getCOLUMN_Photo(), news.get_photo());
        values.put(DBInfo.getCOLUMN_Author(), news.get_author());
        values.put(DBInfo.getCOLUMN_AuthorBelongs(), news.get_authorbelongs());
        values.put(DBInfo.getCOLUMN_DateTime(), news.get_datetime());
        values.put(DBInfo.getCOLUMN_IsReaded(), 1);
        values.put(DBInfo.getCOLUMN_Relevance(), news.get_relevance());
        values.put(DBInfo.getCOLUMN_URL(), "");

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(DBInfo.getTABLE_NAME_NEWS(), values, DBInfo.getCOLUMN_ID() + " = " + news.get_id(), null);
        db.close();
    }

    public Boolean canDisplayNews(News news) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + DBInfo.getTABLE_NAME_ACADEMITUNITS() + " WHERE " + DBInfo.getCOLUMN_IsChecked() + " = 1 AND " + DBInfo.getCOLUMN_UnitID() + " = " + news.get_authorbelongs();

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
}
