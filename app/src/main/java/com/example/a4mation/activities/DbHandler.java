package com.example.a4mation.activities;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;


public class DbHandler extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String DB_NAME = "NoteVibes";
    private static final String TABLE_NAME = "todo";
    public static final String TABLE1_NAME = "lock_table";
    public static final String TABLE2_NAME = "security_table";

    // Column Names
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String STARTED = "started";
    private static final String FINISHED = "finished";

    //Lock_Table columns
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Title";
    public static final String COL_3 = "Password";
    public static final String COL_4 = "Description";
    public static final String COL_5 = "DateTime";
    public static final String COL_6 = "Color";

    //security_table columns
    public static final String COL2_1 = "ID";
    public static final String COL2_2 = "SQuestion";
    public static final String COL2_3 = "Answer";
    public static final String COL2_4 = "Lock";

    public DbHandler(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLE_CREATE_QUERY = "CREATE TABLE "+TABLE_NAME+" "+
                "("
                +ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +TITLE+ " TEXT,"
                +DESCRIPTION+ " TEXT,"
                +STARTED+ " TEXT,"
                +FINISHED+ " TEXT" +
                ");";

        db.execSQL(TABLE_CREATE_QUERY);
        db.execSQL("create table "+ TABLE1_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT, Password TEXT, Description TEXT, DateTime TEXT, Color TEXT)");
        db.execSQL("create table "+ TABLE2_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, SQuestion TEXT, Answer TEXT, Lock TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        // Drop older table if existed
        db.execSQL(DROP_TABLE_QUERY);
        // Create tables again
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS "+ TABLE1_NAME);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE2_NAME);
        onCreate(db);
    }

    public void addToDo(ToDo toDo){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE,toDo.getTitle());
        contentValues.put(DESCRIPTION,toDo.getDescription());
        contentValues.put(STARTED,toDo.getStarted());
        contentValues.put(FINISHED,toDo.getFinished());

        //Save to table
        sqLiteDatabase.insert(TABLE_NAME,null, contentValues);
        //close database
        sqLiteDatabase.close();
    }

    // Count todo table recodes
    public int countToDo(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }

    // Get all todos into a list
    public List<ToDo> getAllToDos(){

        List<ToDo> toDOS = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                //create new todo object
                ToDo toDo = new ToDo();
                //mmgby6hh
                toDo.setId(cursor.getInt(0));
                toDo.setTitle(cursor.getString(1));
                toDo.setDescription(cursor.getString(2));
                toDo.setStarted(cursor.getLong(3));
                toDo.setFinished(cursor.getLong(4));

                //toDos  = [Obj, onjs, ]
                toDOS.add(toDo);

            }while (cursor.moveToNext());
        }
        return toDOS;
    }



    //Insert Data
    public boolean insertLockData(String title, String password, String description, String date, String color){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, title);
        contentValues.put(COL_3, password);
        contentValues.put(COL_4, description);
        contentValues.put(COL_5, date);
        contentValues.put(COL_6, color);
        long result = sqLiteDatabase.insert(TABLE1_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    //Retrieve data from table 1
    public Cursor getLockData(){
        String query = "select * from "+ TABLE1_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = null;
        if(sqLiteDatabase != null){
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        return cursor;
    }

    //Update table 1
    public boolean updateLock(String id, String title, String password, String color, String description, String date){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, title);
        contentValues.put(COL_3, password);
        contentValues.put(COL_4, description);
        contentValues.put(COL_5, date);
        contentValues.put(COL_6, color);
        long result = sqLiteDatabase.update(TABLE1_NAME, contentValues, "ID = ?", new String[] { id });
        if(result == -1)
            return false;
        else
            return true;
    }


    //Insert security key
    public boolean insertSecurity(String SQuestion, String Answer, String Lock){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2_2, SQuestion);
        contentValues.put(COL2_3, Answer);
        contentValues.put(COL2_4, Lock);
        long result = sqLiteDatabase.insert(TABLE2_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    //Retrieve all security data
    public Cursor getSecurity(){
        String query = "select * from "+ TABLE2_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = null;
        if(sqLiteDatabase != null){
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        return cursor;
    }

    //Get Security question
    public String getSecurityQuestion(){
        String get_Status = "0";
        String query = "select SQuestion from "+ TABLE2_NAME + " where ID = " + 1 ;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            get_Status = c.getString(0);
        }
        sqLiteDatabase.close();
        return get_Status;
    }

    //Update key
    public boolean updateSecurityKey(String l){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2_4, l);

        int i = sqLiteDatabase.update(TABLE2_NAME, contentValues, COL2_1 + "=" + 1, null);
        return i > 0;
    }

    //Retrieve key
    public String getSecurityKey(){
        String get_Status = "0";
        String query = "select Lock from "+ TABLE2_NAME + " where ID = " + 1 ;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            get_Status = c.getString(0);
        }
        sqLiteDatabase.close();
        return get_Status;
    }

    //return answer
    public String getAnswer(){
        String get_Status = "0";
        String query = "select Answer from "+ TABLE2_NAME + " where ID = " + 1 ;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            get_Status = c.getString(0);
        }
        sqLiteDatabase.close();
        return get_Status;
    }

    //return password
    public String getPassword(String id){
        String get_Status = "0";
        String query = "select Password from "+ TABLE1_NAME + " where ID = " + id ;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            get_Status = c.getString(0);
        }
        sqLiteDatabase.close();
        return get_Status;
    }

    //return description
    public String getDescription(String id){
        String get_Status = "0";
        String query = "select Description from "+ TABLE1_NAME + " where ID = " + id ;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(query, null);
        if(c != null && c.moveToFirst()){
            get_Status = c.getString(0);
        }
        sqLiteDatabase.close();
        return get_Status;
    }

    //delete one row of table 1
    boolean deleteOneLock(String row_id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long result = sqLiteDatabase.delete(TABLE1_NAME, " ID = ?", new String[] { row_id });
        if(result == -1)
            return false;
        else
            return true;
    }
}
