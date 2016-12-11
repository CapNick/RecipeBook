package hird.nick.psynh1.recipebook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static hird.nick.psynh1.recipebook.RecipeContract.RECIPE_CONTENT;
import static hird.nick.psynh1.recipebook.RecipeContract.RECIPE_ID;
import static hird.nick.psynh1.recipebook.RecipeContract.RECIPE_TITLE;

public class ModifyRecipeActivity extends AppCompatActivity {

    private TextView titleTextField;
    private TextView contentTextField;
    private String recipeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_recipe);
        titleTextField = (TextView) findViewById(R.id.editTitle);
        contentTextField = (TextView) findViewById(R.id.editContent);

        //setting up the buttons to work both ways for new and update
        Button saveButton = (Button) findViewById(R.id.updateButton);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        if(getIntent().hasExtra(RECIPE_ID)){
            saveButton.setText("Update");
            recipeID = getIntent().getExtras().getString(RECIPE_ID);
            titleTextField.setText(getIntent().getExtras().getString(RECIPE_TITLE));
            contentTextField.setText(getIntent().getExtras().getString(RECIPE_CONTENT));

        }else {
            saveButton.setText("Save");
            deleteButton.setVisibility(View.GONE);
        }


    }

    public void saveRecipe(View view){
        Intent intent = new Intent();
        if(getIntent().hasExtra(RECIPE_ID)){
            intent.putExtra(RECIPE_ID, recipeID);
        }
        intent.putExtra(RECIPE_TITLE, titleTextField.getText().toString());
        intent.putExtra(RECIPE_CONTENT, contentTextField.getText().toString());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void backRecipe(View view){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public void deleteRecipe(View view){
        Intent intent = new Intent();
        intent.putExtra(RECIPE_ID, recipeID);
        setResult(MainActivity.DELETE_RECIPE_RESULT_CODE, intent);
        finish();
    }
}
