package com.udacity.sandwichclub.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json,Context context)
    {
        Sandwich sandwich=null;
        try {
            JSONObject jsonObject=new JSONObject(json);
            JSONObject nameJsonObject=jsonObject.getJSONObject("name");
            String mainName=nameJsonObject.getString("mainName");
            JSONArray alsoKnowJsonArray=nameJsonObject.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAsList=new ArrayList<String>();
            for(int i=0;i<alsoKnowJsonArray.length();i++){
              alsoKnownAsList.add(alsoKnowJsonArray.getString(i));
            }
            String placeOfOrigin=jsonObject.getString("placeOfOrigin");
            String description=jsonObject.getString("description");
            String image=jsonObject.getString("image");
            JSONArray ingredientsJsonArray=jsonObject.getJSONArray("ingredients");
            List<String> ingredientsArray=new ArrayList<String>();
            for(int i=0;i<ingredientsJsonArray.length();i++){
                ingredientsArray.add(ingredientsJsonArray.getString(i));
            }
            sandwich=new Sandwich(mainName,alsoKnownAsList,placeOfOrigin,description,image,ingredientsArray);

        } catch (JSONException e) {
            Log.i("catch:","In catch");
            e.printStackTrace();
        }
        return sandwich;
    }
}
