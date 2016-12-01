package hird.nick.psynh1.recipebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class RecipeDBHelper extends SQLiteOpenHelper {


    //The database's name
    private static final String DATABASE_NAME = "RecipeDatabase.db";
    //The database table name
    private static final String TABLE_NAME = "recipestore";
    //The table value names
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_RECIPE_TITLE = "recipetitle";
    private static final String COLUMN_RECIPE_CONTENT = "recipecontent";
    //SQL Commands
    //the string containing the sql for creating the table
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + "("+ COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_RECIPE_TITLE + " TEXT NOT NULL, "+ COLUMN_RECIPE_CONTENT + " TEXT NOT NULL);";
    //
    private static final String SQL_DROP = "DROP TABLE IS EXISTS " + TABLE_NAME;




    //The constructor for the sql helper
    public RecipeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP);
        onCreate(db);
    }
    /**
     * Called when you want to create a new recipe entry.
     * @param values the string of values to be stored in the database entry.
     */
    public long addNewRecipe(ContentValues values){
        long id = 0;
        try{
            id = getWritableDatabase().insert(TABLE_NAME,"",values);
        }catch (SQLException e){
            Log.e("RecipeDBHelper", e.toString());
        }
        return id;
    }
    /**
     * Called when you want to delete an existing recipe entry.
     * @param id the string of the id of the database entry you want to remove.
     */
    public int deleteRecipe(String id){
        if (id == null){
            return getWritableDatabase().delete(TABLE_NAME, null, null);
        }
        else {
            return getWritableDatabase().delete(TABLE_NAME, "_id=?", new String[]{id});
        }
    }
    /**
     * Called when you want to update an  recipe entry.
     * @param id the id value of the database entry you want to update.
     * @param values the string of values to be update in the database entry.
     */
    public int updateRecipe(String id, ContentValues values){
        if (id == null){
            return getWritableDatabase().update(TABLE_NAME, values, null, null);
        }
        else {
            return getWritableDatabase().update(TABLE_NAME, values, "_id=?", new String[]{id});
        }
    }

}
