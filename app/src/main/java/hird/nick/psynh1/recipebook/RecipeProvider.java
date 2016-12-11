package hird.nick.psynh1.recipebook;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import static hird.nick.psynh1.recipebook.RecipeContract.CONTENT_TYPE_MULTIPLE;
import static hird.nick.psynh1.recipebook.RecipeContract.CONTENT_TYPE_SINGLE;
import static hird.nick.psynh1.recipebook.RecipeContract.RECIPE_ID;
import static hird.nick.psynh1.recipebook.RecipeContract.RECIPE_TABLE;


public class RecipeProvider extends ContentProvider {

    private static final int RECIPES = 1;
    private static final int RECIPE = 2;
    private RecipeDBHelper recipeDB = null;

    private UriMatcher uriMatcher = getUriMatcher();

    static UriMatcher getUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(RecipeContract.AUTHORITY, "recipe", RECIPES);
        uriMatcher.addURI(RecipeContract.AUTHORITY, "recipe/#", RECIPE);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        recipeDB = new RecipeDBHelper(context,1);
        Log.d("Recipe Provider", "onCreate");
        return true;
    }

    @Override
    public String getType(Uri uri) {
        String contentType;
        if(uri.getLastPathSegment()==null){
            contentType = RecipeContract.CONTENT_TYPE_MULTIPLE;
        }
        else{
            contentType = RecipeContract.CONTENT_TYPE_SINGLE;
        }
        return contentType;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        return recipeDB.getReadableDatabase().query(RECIPE_TABLE, projection, selection, selectionArgs, null, null, sortOrder);

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = recipeDB.getWritableDatabase();
        long id = db.insert(RECIPE_TABLE, null, values);
        Log.d("provider insert", id+" : "+ uri.toString());
        Uri responseURI = ContentUris.withAppendedId(uri, id);

        getContext().getContentResolver().notifyChange(responseURI, null);

        return responseURI;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = recipeDB.getWritableDatabase();
        Log.d("Provider delete", uri.toString());
        return db.delete(RECIPE_TABLE, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = recipeDB.getWritableDatabase();
        Log.d("Provider update", uri.toString());
        return db.update(RECIPE_TABLE, values, selection, selectionArgs);
    }
}
