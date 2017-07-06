package com.example.alexh.msge;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;

public class UpcomingVotingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_votings);

        final ListView lvLista = (ListView) findViewById(R.id.lvListaUpcomingVotings);
        final Button btWroc = (Button) findViewById(R.id.btBackUpcomingVotings);
        final TextView tvNoVotings = (TextView) findViewById(R.id.tvNoUpcomingVotings);

        RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "http://testglosowanie.000webhostapp.com/GetUpcomingVotings.php";
        String url = "http://glosowanie.000webhostapp.com/GetUpcomingVotings.php";


        // Request a string response from the provided URL.
        StringRequest upcomingVotingsRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject getVotingsResponse = new JSONObject(response);
                            Log.i("getVotingsResponse", getVotingsResponse.toString());
                            boolean success = getVotingsResponse.getBoolean("success");

                            if (success){
                                int votings_count = getVotingsResponse.optInt("votings_count"); // liczba głosowań
                                final String[] votingsList = new String[votings_count]; // tablica głosowań
                                final String[] descriptionsList = new String[votings_count]; //tablica opisów głosowań
                                String iterator;
                                // wypełnienie tablicy głosowań
                                for (int i=0; i<votings_count; i++){
                                    iterator = Integer.toString(i+1);
                                    votingsList[i] = getVotingsResponse.getString(iterator);
                                }
                                // wypełnienie tablicy opisów głosowań
                                for(int i=0; i<votings_count; i++){
                                    iterator = Integer.toString(i+1+votings_count);
                                    descriptionsList[i] = getVotingsResponse.getString(iterator);
                                }
                                // tworzenie adaptera listy głosowań i ustawianie go jako adapter obiektu lvLista
                                ListAdapter listAdapter = new ArrayAdapter<String>(UpcomingVotingsActivity.this,
                                        android.R.layout.simple_list_item_1 , votingsList);
                                lvLista.setAdapter(listAdapter);
                                // nowa metoda OnClickListener() obiektu listy wyświetlanej przez aktywność
                                lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        String clickedItemString = lvLista.getItemAtPosition(position).toString();
                                        int index = Arrays.asList(votingsList).indexOf(clickedItemString);
                                        String msg;

                                        if (descriptionsList[index].equals("")) {
                                            msg = "To głosowanie nie ma opisu";
                                        }else{
                                            msg = descriptionsList[index];
                                        }

                                        AlertDialog.Builder builder = new AlertDialog.Builder(UpcomingVotingsActivity.this);
                                        builder.setMessage(msg)
                                                .setNegativeButton("Dalej", null)
                                                .create()
                                                .show();
                                    }
                                });


                            } else {
                                boolean emptyfield = getVotingsResponse.getBoolean("emptyfield");
                                if (emptyfield) {
                                    tvNoVotings.setVisibility(View.VISIBLE);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpcomingVotingsActivity.this);
                builder.setMessage("Błąd podczas wczytywania listy głosowań")
                        .setNegativeButton("Spróbuj ponownie", null)
                        .create()
                        .show();
            }
        });

        queue.add(upcomingVotingsRequest);

        btWroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
