package com.aviator.elearning.el.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    static final String DBNAME = "el.db";

    public DbHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(courseSql);
        db.execSQL(topicsSql);
        db.execSQL(subtopicsql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS  " + topicsTb);
        db.execSQL("DROP TABLE IF EXISTS  " + courseTb);
        onCreate(db);
    }

    public boolean InsertCourse(String uniqid, String coursename) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("uniqid", uniqid);
        contentValues.put("course", coursename);
        return (sqLiteDatabase.insert(courseTb, null, contentValues)) != -1;
    }

    public boolean InsertTopic(String courseuniqid, String topic) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("courseuniqid", courseuniqid);
        contentValues.put("topic", topic);
        return (sqLiteDatabase.insert(topicsTb, null, contentValues)) != -1;
    }

    public boolean InsertSubTopic(String topicid, String subtopic) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("topicid", topicid);
        contentValues.put("subtopic", subtopic);
        return (sqLiteDatabase.insert(tsubopicsTb, null, contentValues)) != -1;
    }

    private static final String topicsTb = "topics_tb";
    private static final String tsubopicsTb = "subtopics_tb";
    private static final String courseTb = "course_tb";

    private static final String courseSql = "CREATE TABLE  " + courseTb + "(id INTEGER PRIMARY KEY AUTOINCREMENT,uniqid TEXT UNIQUE,course TEXT)";

    private static final String topicsSql = "CREATE TABLE " + topicsTb + "(id INTEGER PRIMARY KEY AUTOINCREMENT, courseuniqid TEXT,topic TEXT)";

    private static final String subtopicsql = "CREATE TABLE " + tsubopicsTb + "(id INTEGER PRIMARY KEY AUTOINCREMENT,topicid TEXT,subtopic TEXT, content TEXT)";


}
