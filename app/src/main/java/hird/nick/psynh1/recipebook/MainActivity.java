package hird.nick.psynh1.recipebook;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import static hird.nick.psynh1.recipebook.RecipeContract.RECIPES_URI;
import static hird.nick.psynh1.recipebook.RecipeContract.RECIPE_CONTENT;
import static hird.nick.psynh1.recipebook.RecipeContract.RECIPE_ID;
import static hird.nick.psynh1.recipebook.RecipeContract.RECIPE_TITLE;

public class MainActivity extends AppCompatActivity {

    //the request codes for new, update and view
    static final int NEW_RECIPE_REQUEST_CODE = 1;
    static final int UPDATE_RECIPE_REQUEST_CODE = 2;
    static final int VIEW_RECIPE_REQUEST_CODE = 3;
    //the result code for delete, if the user wants to delete the recipe
    public static final int DELETE_RECIPE_RESULT_CODE = 404;

    private String selectedRecipeID = "-1";
    private String selectedRecipeTitle = "";
    private String selectedRecipeContent = "";
    private SimpleCursorAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Main Activity", "onCreate");

        queryContentProvider();
    }

    /**
     * Used to update the
     **/

    public void queryContentProvider() {

        String[] projection = new String[] {
                RECIPE_ID,
                RECIPE_TITLE,
                RECIPE_CONTENT
        };

        String colsToDisplay [] = new String[] {
                RECIPE_TITLE
        };

        int[] colResIds = new int[] {
                R.id.value1,
        };

        final Cursor cursor = getContentResolver().query(RECIPES_URI, projection, null, null, null);

        dataAdapter = new SimpleCursorAdapter(
                this,
                R.layout.recipe_item_layout,
                cursor,
                colsToDisplay,
                colResIds,
                0);

        ListView listView = (ListView) findViewById(R.id.recipeList);
        listView.setAdapter(dataAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> dataAdapter, View myView, int position, long id) {
                Log.d("onClick", "position: "+position+", id: "+id);
                //
                cursor.moveToPosition(position);
                selectedRecipeID = cursor.getString(0);
                selectedRecipeTitle = cursor.getString(1);
                selectedRecipeContent = cursor.getString(2);
                Log.d("Selected Recipe", selectedRecipeID+", "+selectedRecipeTitle+", "+selectedRecipeContent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == NEW_RECIPE_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                //add new to database
                Log.v("New Values:", data.getExtras().getString(RECIPE_TITLE)+", "+
                        data.getExtras().getString(RECIPE_CONTENT));
                ContentValues newValues = new ContentValues();
                newValues.put(RecipeContract.RECIPE_TITLE, data.getExtras().getString(RECIPE_TITLE));
                newValues.put(RecipeContract.RECIPE_CONTENT, data.getExtras().getString(RECIPE_CONTENT));
                Log.v("New to:", getContentResolver().insert(RECIPES_URI, newValues).toString());
                queryContentProvider();
            }
        }
        if(requestCode == UPDATE_RECIPE_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                //update database
                Log.v("Update","returned sucessful");
                Log.v("Update Values:", data.getExtras().getString(RECIPE_ID)+", "+
                        data.getExtras().getString(RECIPE_TITLE)+", "+
                        data.getExtras().getString(RECIPE_CONTENT));

                ContentValues newValues = new ContentValues();
                newValues.put(RecipeContract.RECIPE_TITLE, data.getExtras().getString(RECIPE_ID));
                newValues.put(RecipeContract.RECIPE_TITLE, data.getExtras().getString(RECIPE_TITLE));
                newValues.put(RecipeContract.RECIPE_CONTENT, data.getExtras().getString(RECIPE_CONTENT));

                getContentResolver().update(RECIPES_URI, newValues,RECIPE_ID+"="+selectedRecipeID, null);
                queryContentProvider();
            }
            else if (resultCode == DELETE_RECIPE_RESULT_CODE){
                deleteRecipeWithID();
                queryContentProvider();
            }
        }
        if(requestCode == VIEW_RECIPE_REQUEST_CODE){
            if (resultCode == DELETE_RECIPE_RESULT_CODE){
                deleteRecipeWithID();
                queryContentProvider();
            }
        }

    }

    public void newRecipe(View view){
        Intent intent = new Intent(MainActivity.this, ModifyRecipeActivity.class);
        startActivityForResult(intent,NEW_RECIPE_REQUEST_CODE);
    }
    public void editRecipe(View view){
        if(selectedRecipeID.equals("-1")){
            Log.d("editRecipe", "there is no recipe selected to edit");
        }
        else {
            Intent intent = new Intent(MainActivity.this, ModifyRecipeActivity.class);
            intent.putExtra(RECIPE_ID, selectedRecipeID);
            intent.putExtra(RECIPE_TITLE, selectedRecipeTitle);
            intent.putExtra(RECIPE_CONTENT, selectedRecipeContent);
            startActivityForResult(intent, UPDATE_RECIPE_REQUEST_CODE);
        }
    }
    public void viewRecipe(View view){
        if(selectedRecipeID.equals("-1")){
            Log.d("viewRecipe", "there is no recipe selected to view");
        }
        else {
            Intent intent = new Intent(MainActivity.this, ViewRecipeActivity.class);
            intent.putExtra(RECIPE_ID, selectedRecipeID);
            intent.putExtra(RECIPE_TITLE, selectedRecipeTitle);
            intent.putExtra(RECIPE_CONTENT, selectedRecipeContent);
            startActivityForResult(intent, VIEW_RECIPE_REQUEST_CODE);
        }
    }
    public void deleteRecipeButton(View view){
        if(selectedRecipeID.equals("-1")){
            Log.d("deleteRecipe", "there is no recipe selected to delete");
        }
        else{
            deleteRecipeWithID();
        }
    }

    private void deleteRecipeWithID(){
        getContentResolver().delete(RECIPES_URI, RECIPE_ID+"="+selectedRecipeID, null);
        resetPerams();
        queryContentProvider();
    }
    private void resetPerams(){
        selectedRecipeID = "-1";
        selectedRecipeTitle = "";
        selectedRecipeContent = "";
    }
}
