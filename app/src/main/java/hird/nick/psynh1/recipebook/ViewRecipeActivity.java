package hird.nick.psynh1.recipebook;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ViewRecipeActivity extends Activity {

    private String recipeID = "";
    private String recipeTitle = "";
    private String recipeContent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);
        getIntent().getExtras().getStringArray("recipe");
        TextView title = (TextView) findViewById(R.id.recipeTitle);
        TextView content = (TextView) findViewById(R.id.recipeContent);


        recipeID = getIntent().getExtras().getString(RecipeContract.RECIPE_ID);
        recipeTitle = getIntent().getExtras().getString(RecipeContract.RECIPE_TITLE);
        recipeContent = getIntent().getExtras().getString(RecipeContract.RECIPE_CONTENT);

        title.setText(recipeTitle);
        content.setText(recipeContent);

    }

    public void editRecipe(View view){
        Intent intent = new Intent();
        intent.putExtra("recipe", getIntent().getExtras().getStringArray("recipe"));
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void backRecipe(View view){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
    public void deleteRecipe(View view){
        Intent intent = new Intent();
        intent.putExtra(RecipeContract.RECIPE_ID, recipeID);
        intent.putExtra(RecipeContract.RECIPE_TITLE, recipeTitle);
        intent.putExtra(RecipeContract.RECIPE_CONTENT, recipeContent);
        setResult(MainActivity.DELETE_RECIPE_RESULT_CODE, intent);
        finish();
    }
}
