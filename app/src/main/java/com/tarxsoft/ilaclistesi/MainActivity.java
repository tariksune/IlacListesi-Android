package com.tarxsoft.ilaclistesi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AdView adView;
    private PublisherInterstitialAd publisherInterstitialAd;

    private static final String TAG = "TAG";
    private static String JSON_URL = "https://805a3cb0dfe6.ngrok.io/api/drugs";

    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    List<Drugs> drugs;
    Adapter adapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.drugList);
        drugs = new ArrayList<>();
        prepareAds();
        listOfDrugs();
    }

    private void listOfDrugs() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject drugObject = response.getJSONObject(i);
                        Drugs drug = new Drugs();
                        drug.setDrugName(drugObject.getString("drugName").toString());
                        drug.setDrugDesc(drugObject.getString("drugActiveSubstance").toString());
                        drug.setDrugBarcode(drugObject.getString("drugBarcode").toString());
                        drug.setDrugIcon(drugObject.getString("drugImage").toString());
                        drugs.add(drug);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                progressBar.setVisibility(View.INVISIBLE);
                gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(gridLayoutManager);
                //recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new Adapter(getApplicationContext(),drugs);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.d(TAG,"Hata: "+ error.getMessage());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem xMenuItem = menu.findItem(R.id.search);
        SearchView xSearchView = (SearchView) xMenuItem.getActionView();
        xSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        MenuItem about = menu.findItem(R.id.about);
        about.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getApplicationContext(),AboutActivity.class));
                Ads();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void prepareAds(){
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        recyclerView.setPadding(0, 0, 0, adView.getHeight());
        recyclerView.setClipToPadding(false);
        publisherInterstitialAd = new PublisherInterstitialAd(this);
        publisherInterstitialAd.setAdUnitId("/6499/example/interstitial");
        publisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());

    }

    public void Ads() {

        if (publisherInterstitialAd.isLoaded()){
            publisherInterstitialAd.show();
        }else{
            finish();
        }

    }

}