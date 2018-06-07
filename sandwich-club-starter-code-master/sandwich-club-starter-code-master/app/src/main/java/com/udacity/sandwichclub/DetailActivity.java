package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final ImageView ingredientsIv = findViewById(R.id.image_iv);
        TextView origin_tv=findViewById(R.id.origin_tv);
        TextView alsoKnown_tv=findViewById(R.id.also_known_tv);
        TextView ingredients_tv=findViewById(R.id.ingredients_tv);
        TextView description_tv=findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json,this);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(DetailActivity.this,"Image failed to load",Toast.LENGTH_SHORT).show();
                        ingredientsIv.setVisibility(View.INVISIBLE);
                    }
                });
            origin_tv.setText(sandwich.getPlaceOfOrigin());
        List<String> alsoKnownAs=sandwich.getAlsoKnownAs();
        if(alsoKnownAs.size()==0){
            alsoKnown_tv.setText("No other names");
        }
        else{
            String x=alsoKnownAs.get(0);
            for(int i=1;i<alsoKnownAs.size();i++){
                x=x+"\n"+alsoKnownAs.get(i);
            }
            alsoKnown_tv.setText(x);
        }
        if(sandwich.getDescription()==""){
            description_tv.setText("No description to display");
        }else {
            description_tv.setText(sandwich.getDescription());
        }
        List<String> ingredients=sandwich.getIngredients();
        if(ingredients.size()==0){
            ingredients_tv.setText("No ingredients to show");
        }
        else{
            String x=ingredients.get(0);
            for(int i=1;i<ingredients.size();i++){
                x=x+"\n"+ingredients.get(i);
            }
            ingredients_tv.setText(x);
        }
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }
}
