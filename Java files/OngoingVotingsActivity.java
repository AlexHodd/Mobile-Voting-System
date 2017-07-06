package com.example.alexh.msge;

import android.content.Intent;
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

public class OngoingVotingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoing_votings);

        // inicjalizacja elementów
        final ListView lvLista = (ListView) findViewById(R.id.lvListaOngoingVotings);
        final Button btWroc = (Button) findViewById(R.id.btBackOngoingVotings);
        final TextView tvNoVotings = (TextView) findViewById(R.id.tvNoOngoingVotings);

        final Intent intent = getIntent();


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://glosowanie.000webhostapp.com/GetOngoingVotings.php";
        // Request a string response from the provided URL.
        StringRequest ongoingVotingsRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject getVotingsResponse = new JSONObject(response);
                            Log.i("getVotingsResponse", getVotingsResponse.toString());
                            boolean success = getVotingsResponse.getBoolean("success");

                            if (success){
                                int voting_count = getVotingsResponse.optInt("votings_count"); // liczba wariantów głosowania
                                String[] votingsList = new String[voting_count]; // lista wariantów głosowania
                                String iterator;
                                // wypełnienie listy wariantów głosowania
                                for (int i=0; i<voting_count; i++){
                                    iterator = Integer.toString(i+1);
                                    votingsList[i] = getVotingsResponse.getString(iterator);
                                }

                                // tworzenie adaptera elementu listy głosowania i jego wypełnienie
                                ListAdapter listAdapter = new ArrayAdapter<String>(OngoingVotingsActivity.this,
                                        android.R.layout.simple_list_item_1 , votingsList);
                                lvLista.setAdapter(listAdapter);

                                // po wybraniu elementu z listy otwiera VoteActivity
                                lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        String login = intent.getStringExtra("login");
                                        String selectedVoting = String.valueOf(lvLista.getItemAtPosition(i));
                                        Intent intentVote = new Intent(OngoingVotingsActivity.this, VoteActivity.class);
                                        intentVote.putExtra("selectedVoting", selectedVoting);
                                        intentVote.putExtra("login", login);
                                        OngoingVotingsActivity.this.startActivity(intentVote);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(OngoingVotingsActivity.this);
                builder.setMessage("Błąd podczas wczytywania listy głosowań")
                        .setNegativeButton("Spróbuj ponownie", null)
                        .create()
                        .show();
            }
        });

        queue.add(ongoingVotingsRequest);

        btWroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}