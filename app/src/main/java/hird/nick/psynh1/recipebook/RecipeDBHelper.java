package hird.nick.psynh1.recipebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static hird.nick.psynh1.recipebook.RecipeContract.RECIPE_TABLE;
import static hird.nick.psynh1.recipebook.RecipeContract.RECIPE_ID;
import static hird.nick.psynh1.recipebook.RecipeContract.RECIPE_TITLE;
import static hird.nick.psynh1.recipebook.RecipeContract.RECIPE_CONTENT;

public class RecipeDBHelper extends SQLiteOpenHelper {


    //The database's name
    private static final String DATABASE_NAME = "RecipeDatabase";
    //SQL Commands
    //the string containing the sql for creating the table
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + RECIPE_TABLE + "("+
            RECIPE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT ," +
            RECIPE_TITLE + " VARCHAR(128) NOT NULL, "+
            RECIPE_CONTENT + " VARCHAR(128) NOT NULL);";
    //the string to drop the whole sql table
    private static final String SQL_DROP = "DROP TABLE IF EXISTS " + RECIPE_TABLE;

    //The constructor for the sql helper
    public RecipeDBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
        Log.d("DB Helper", "Constructed");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
//        db.execSQL("INSERT INTO RECIPES ("+RECIPE_TITLE+", "+RECIPE_CONTENT+") VALUES ('Test 1', 'Test one filler text')");
//        db.execSQL("INSERT INTO RECIPES ("+RECIPE_TITLE+", "+RECIPE_CONTENT+") VALUES ('Test 2', 'Test two filler text')");

        Log.d("DB Helper", "onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP);
        onCreate(db);
        Log.d("DB Helper", "onUpgrade");
    }


}
