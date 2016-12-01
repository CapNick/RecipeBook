package hird.nick.psynh1.recipebook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

public class RecipeProvider extends ContentProvider {

    private static final String PROVIDER_NAME = "hird.nick.psynh1.recipebook.RecipeProvider.recipes";
    private static final int RECIPES = 1;
    private static final int RECIPE_ID = 2;
    private RecipeDBHelper recipeDB = null;

    private UriMatcher uriMatcher = getUriMatcher();
    private static UriMatcher getUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "recipes", RECIPES);
        uriMatcher.addURI(PROVIDER_NAME, "recipes/#", RECIPE_ID);
        return uriMatcher;
    }



    @Override
    public boolean onCreate() {
        Context context = getContext();
        recipeDB = new RecipeDBHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case RECIPES:
                return "";
            case RECIPE_ID:
                return "";
            default:
                return "";
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
