package mm.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mateusz on 2016-10-29.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="DataBase";
    private static final int DB_VERSION=1;
    private static final String TableShoppings="ShoppingList";
    private static final String TableProducts="ProductList";//do zaimlementowania tabeli z historycznymi rekordami


    DBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override  //gdy BD po raz pierwszy jest tworzona
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TableShoppings+
                "(_ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "NAME TEXT UNIQUE, "+
                "DONE INTEGER);");
    }
    //"DONE NUMERIC);");

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }


    public static boolean InsertRowShoppingList(SQLiteDatabase db, String name){
        ContentValues values=new ContentValues();
        values.put("NAME",name);
        values.put("DONE",false);
        long success=db.insert(TableShoppings,null,values);
        return success!=-1;
    }
    public static boolean UpdateRowNameValue(SQLiteDatabase db,String OldName,String NewName){
        ContentValues values=new ContentValues();
        values.put("Name",NewName);
        int success = db.update(TableShoppings,values,"NAME = ?",new String[]{OldName});
        return success!=0;
    }
    public static boolean UpdateRowDoneValue(SQLiteDatabase db,String Name,boolean done){
        ContentValues values=new ContentValues();
        values.put("DONE",done);
        int success = db.update(TableShoppings,values,"NAME = ?",new String[]{Name});
        return success!=0;
    }
    public static boolean DeleteRowShoppingList(SQLiteDatabase db,String name){
        int success=db.delete(TableShoppings,"NAME = ?",new String[]{name});
        return success>0;
    }
    public static boolean DeleteWholeList(SQLiteDatabase db){
        int success=db.delete(TableShoppings,null,null);
        return success>0;
    }
    public Cursor GetAllShoppings(SQLiteDatabase db){
        Cursor cursor=db.query(TableShoppings,new String[]{"_ID","NAME","DONE"},null,null,null
        ,null,null);
        return cursor;
    }
    public static Cursor getDoneValue(SQLiteDatabase db, String name){
        Cursor cursor=db.query(TableShoppings,new String[]{"DONE"},"NAME = ?",new String[]{name},null,null,null);
        //int i=cursor.getInt(cursor.getColumnIndex("DONE"));
        return cursor;
    }
}
