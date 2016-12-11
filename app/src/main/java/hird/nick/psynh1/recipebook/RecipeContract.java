package hird.nick.psynh1.recipebook;

import android.net.Uri;

public class RecipeContract {
    //our authority name
    public static final String AUTHORITY = "hird.nick.psynh1.recipebook.RecipeProvider";
    //recipe uri
    public static final Uri RECIPES_URI = Uri.parse("content://"+AUTHORITY+"/recipes");
    //table name
    public static final String RECIPE_TABLE = "recipes";
    //values to reference
    public static final String RECIPE_ID = "_id";
    public static final String RECIPE_TITLE = "title";
    public static final String RECIPE_CONTENT = "content";

    //for selecting a single or multiple recipes
    public static final String CONTENT_TYPE_SINGLE = "vnd.android.cursor.item/RecipeProvider.data.text";
    public static final String CONTENT_TYPE_MULTIPLE = "vnd.android.cursor.dir/RecipeProvider.data.text";


}
