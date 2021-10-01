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
    private static final String TABLE_NAME5 = "list_table";

    // Column Names
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String STARTED = "started";
    private static final String FINISHED = "finished";

    // list_table table Columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ITEM_NAME = "item";
    private static final String COLUMN_QUANTITY = "quantity";


    // create_list table Columns
    private static final String COLUMN_lIST_ID = "id";
    private static final String COLUMN_LIST_TITLE_NAME = "title";
    private static final String COLUMN_lIST_SUB_NAME = "subtitle";
    private static final String COLUMN_LIST_DATE = "date";

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

        // Item Query
        String query2 =
                "CREATE TABLE " + TABLE_NAME4 +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_ITEM_NAME + " TEXT, " +
                        COLUMN_QUANTITY + " INTEGER);";

        db.execSQL(query2);

        // List Query
        String query3 =
                "CREATE TABLE " + TABLE_NAME5 +
                        " (" + COLUMN_lIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_LIST_TITLE_NAME + " TEXT, " +
                        COLUMN_lIST_SUB_NAME + " TEXT," + COLUMN_LIST_DATE + " TEXT);";

        db.execSQL(query3);

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


        // add items
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME4);
        onCreate(db);

        // Create list
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME5);
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


    //insert sticky note data into database including title,body,image,selected color and created date
    public void insertData(String Title, String Body, String Image, String Color) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_TITLE, Title);
        values.put(C_BODY, Body);
        values.put(C_IMAGE, Image);
        values.put(C_TIMESTAMP, getDateTime());
        values.put(C_COLOR, Color);
        long result = db.insert(TABLE_NAME3, null, values);
         //display unsuccessful message
        if (result == -1)
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show();
        //display successful message if no error
    }


        //read all data from database table
    Cursor readstk(){
        String query = "SELECT * FROM " +TABLE_NAME3;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


        //update sticky note table with changes
    void updatestk(String row_id, String Title, String Body, String Color, String Image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(C_TITLE, Title);
        val.put(C_BODY, Body);
        val.put(C_COLOR, Color);
        val.put(C_IMAGE, Image);
        val.put(C_TIMESTAMP, getDateTime());//further adding updated time into database
        //update values that user changed
        long res = db.update(TABLE_NAME3, val, "ID=?", new String[]{row_id});
        if (res == -1)
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();
        //display successful message if no error
    }


    //delete sticky notes from table using ID
    void deleteonestk(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long re = db.delete(TABLE_NAME3, "ID=?", new String[]{row_id});
        if(re == -1)
            Toast.makeText(context, "can't delete", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
    }

    //delete all sticky notes at once
    void deleteAllStk(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME3);
        Toast.makeText(context,"Deleted", Toast.LENGTH_SHORT).show();

    }




    // Adding list Items
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

    // Delete Items

    void deleteOneRow (String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME4, "id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context,"Failed to Delete", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Successfully Deleted",Toast.LENGTH_SHORT).show();
        }
    }
    // Delete All Data
    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME4);
    }

    // Adding main List
    public boolean createList(String title, String subtitle, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put (COLUMN_LIST_TITLE_NAME, title);
        cv.put (COLUMN_lIST_SUB_NAME, subtitle);
        cv.put (COLUMN_LIST_DATE, date);

        long result = db.insert(TABLE_NAME5, null,cv);
        if(result == -1){
           return false;
        }else{
            return true;
        }


    }

    Cursor readData(){
        String query3 = "SELECT * FROM " + TABLE_NAME5;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query3, null);
        }
        return cursor;
    }

    // Delete All lists (ListView)
    void deleteAllListData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME5);
    }

}


