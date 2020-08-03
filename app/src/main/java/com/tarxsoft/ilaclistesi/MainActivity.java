package com.tarxsoft.ilaclistesi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String JSON_URL = "https://www.tariksune.com/ilac-liste.json";
    RecyclerView recyclerView;
    List<Drugs> drugs;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.drugList);
        drugs = new ArrayList<>();
        listOfDrugs();
    }

    private void listOfDrugs() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject drugObject = response.getJSONObject(i);
                        Drugs drug = new Drugs();
                        drug.setDrugName(drugObject.getString("C").toString());
                        drug.setDrugDesc(drugObject.getString("D").toString());
                        drug.setDrugBarcode(drugObject.getString("B").toString());
                        drug.setDrugIcon(drugObject.getString("G").toString());
                        drugs.add(drug);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new Adapter(getApplicationContext(),drugs);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag","onError: "+ error.getMessage());
            }
        });

        requestQueue.add(jsonArrayRequest);

    }
}