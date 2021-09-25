package com.example.a4mation.activities;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;


public class DbHandler extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String DB_NAME = "NoteVibes";
    private static final String TABLE_NAME = "todo";
    public static final String TABLE1_NAME = "lock_table";
    public static final String TABLE2_NAME = "security_table";
    public static final String TABLE_NAME3 = "Sticky_Note_table";
    private static final String TABLE_NAME4 = "adding_items";

    // Column Names
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String STARTED = "started";
    private static final String FINISHED = "finished";

    // Column add items
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ITEM_NAME = "item";
    private static final String COLUMN_QUANTITY = "quantity";

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

    //Sticky_notes table columns
    private static final String C_ID = "ID";
    private static final String C_TITLE = "TITLE";
    private static final String C_BODY = "BODY";
    private static final String C_IMAGE = "IMAGE";
    private static final String C_COLOR = "COLORP";
    private static final String C_TIMESTAMP = "TIMESTAMP";
    private Context context;

    public DbHandler(@Nullable Context context) {

        super(context, DB_NAME, null, VERSION);
        this.context = context;
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
        String query =
                "CREATE TABLE " + TABLE_NAME3 +
                        " (" + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + C_TITLE + " TEXT, " + C_BODY + " TEXT, " + C_TIMESTAMP + " TEXT, " + C_IMAGE + " TEXT, " + C_COLOR + " TEXT);";
        db.execSQL(query);


        String query2 =
                "CREATE TABLE " + TABLE_NAME4 +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_ITEM_NAME + " TEXT, " +
                        COLUMN_QUANTITY + " INTEGER);";

        db.execSQL(query2);

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
       // for sticky note table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        onCreate(db);

        // add item
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME4);
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








    //sticky notes functions


    public String getDateTime(){
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date;
        date = new Date();
        return dateFormat.format(date);
    }

    public void insertData(String Title, String Body, String Image, String Color) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_TITLE, Title);
        values.put(C_BODY, Body);
        values.put(C_IMAGE, Image);
        values.put(C_TIMESTAMP, getDateTime());
        values.put(C_COLOR, Color);
        long result = db.insert(TABLE_NAME3, null, values);

        if (result == -1)
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show();
    }



    Cursor readstk(){

        String query = "SELECT * FROM " +TABLE_NAME3;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }



    void updatestk(String row_id, String Title, String Body, String Color, String Image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(C_TITLE, Title);
        val.put(C_BODY, Body);
        val.put(C_COLOR, Color);
        val.put(C_IMAGE, Image);
        val.put(C_TIMESTAMP, getDateTime());
        long res = db.update(TABLE_NAME3, val, "ID=?", new String[]{row_id});
        if (res == -1)
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();
    }

    void deleteonestk(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long re = db.delete(TABLE_NAME3, "ID=?", new String[]{row_id});
        if(re == -1)
            Toast.makeText(context, "can't delete", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
    }
    void deleteAllStk(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME3);
        Toast.makeText(context,"Deleted", Toast.LENGTH_SHORT).show();

    }





    void addItems(String item_name, int quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put (COLUMN_ITEM_NAME, item_name);
        cv.put (COLUMN_QUANTITY, quantity);
        long result = db.insert(TABLE_NAME4, null,cv);
        if(result == -1){
            Toast.makeText(context,"Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME4;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    // Update items
    void updateData(String row_id, String item, String quantity){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ITEM_NAME, item);
        cv.put(COLUMN_QUANTITY, quantity);

        long result = db.update(TABLE_NAME4, cv, "id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
        }
    }

    // Delete

    void deleteOneRow (String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME4, "id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context,"Failed to Delete", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Successfully Deleted",Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME4);
    }

}


