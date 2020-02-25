package org.techtown.image_load;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 12;
    private static final String DATABASE_NAME = "imgload";
    private static final String TABLE = "animals";
    private static final String KEY_NAME = "name";
    private static final String KEY_URL = "url";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_DRINK =
                "CREATE TABLE IF NOT EXISTS " + TABLE + "(" +
                        KEY_NAME + " TEXT NOT NULL, " +
                        KEY_URL + " TEXT NOT NULL" +
                        ");";


        db.execSQL(CREATE_TABLE_DRINK);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE_DRINK =
                "DROP TABLE IF EXISTS " + TABLE;
        db.execSQL(DROP_TABLE_DRINK);

        onCreate(db);
    }




    public void add(String name, String url) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_URL, url);

        db.insert(TABLE, null, values);
        db.close();
    }



    public ArrayList<String> getListName() {
        ArrayList<String> items=new ArrayList<>();

        String SELECT = "SELECT distinct name FROM "+ TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(SELECT, null);
        if(c!=null){
            if(c.moveToFirst()){
                do {
                    String Name = c.getString(c.getColumnIndex("name"));
                    items.add(Name);
                }while(c.moveToNext());
            }
        }
        db.close();

        return items;
    }



    public ArrayList<String> getListUrl(String s) {
        ArrayList<String> items=new ArrayList<>();

        String SELECT = "SELECT url FROM "+ TABLE +" WHERE "+KEY_NAME+" like \""+s+"\" limit 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(SELECT, null);
        if(c!=null){
            if(c.moveToFirst()){
                do {
                    String Name = c.getString(c.getColumnIndex("url"));
                    items.add(Name);
                }while(c.moveToNext());
            }
        }
        db.close();

        return items;
    }



    public ArrayList<String> getAllUrl(String s) {
        ArrayList<String> items=new ArrayList<>();

        String SELECT = "SELECT url FROM "+TABLE+" WHERE "+KEY_NAME+" like \""+s+"\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(SELECT, null);
        if(c!=null){

            } if(c.moveToFirst()){
                do {
                    String Url = c.getString(c.getColumnIndex("url"));
                    items.add(Url);
                }while(c.moveToNext());
        }
        db.close();

        return items;
    }



    public boolean checkTable(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        String SELECT = "SELECT * FROM " + "animals WHERE name LIKE \""+s+"\"";
        String Name = null;

        Cursor c = db.rawQuery(SELECT, null);
        if(c!=null){

        } if(c.moveToFirst()){
            do {
                Name = c.getString(c.getColumnIndex("url"));
            }while(c.moveToNext());
        }
        db.close();

        if(Name==null)
            return false;

        return true;
    }



    public void delete(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        String DELETE = "DELETE FROM " + "animals WHERE name like \""+name+"\"";
        db.execSQL(DELETE);
        db.close();
    }




    //홈페이지에서 이미지 url을 가져오는 코드 다른 홈페이지로 적용할 시 안될거임...
    public void get_img(String[] str){
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        StringBuilder contents = new StringBuilder();


        for(String s:str) {
            if(checkTable(s))
                continue;

            try {
                String url_page = "https://www.gettyimages.com/photos/collaboration?license=rf&family=creative&phrase=" + s + "&sort=mostpopular#license";
                url = new URL(url_page);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                contents.setLength(0);
                while ((line = rd.readLine()) != null) {
                    contents.append(line);
                }
                rd.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Pattern pattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");
            Matcher matcher = pattern.matcher(contents);

            while (matcher.find()) {
                if (matcher.group(1).charAt(0) != 'h')
                    continue;
                add(s, matcher.group(1));
            }
        }
    }
}


