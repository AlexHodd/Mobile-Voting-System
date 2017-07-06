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

public class EndedVotingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ended_votings);

        final ListView lvLista = (ListView) findViewById(R.id.lvListaEndedVotings);
        final Button btWroc = (Button) findViewById(R.id.btBackEndedVotings);
        final Button btWroc2 = (Button) findViewById(R.id.btBackEndedVotings2);
        final TextView tvInfo = (TextView) findViewById(R.id.tvInfo);

        final RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "http://testglosowanie.000webhostapp.com/GetEndedPolls.php";
        String url = "http://glosowanie.000webhostapp.com/GetEndedPolls.php";


        // Request a string response from the provided URL.
        StringRequest endedVotingsRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject getVotingsResponse = new JSONObject(response);
                            Log.i("getVotingsResponse", getVotingsResponse.toString());
                            boolean success = getVotingsResponse.getBoolean("success");

                            if (success){
                                int liczbaGlosowan = getVotingsResponse.optInt("votings_count"); // liczba głosowań
                                String[] votingsList = new String[liczbaGlosowan]; // lista wariantów głosowania
                                String iterator;
                                // wypełnienie listy wariantów głosowania
                                for (int i=0; i<liczbaGlosowan; i++){
                                    iterator = Integer.toString(i+1);
                                    votingsList[i] = getVotingsResponse.getString(iterator);
                                }

                                // tworzenie adaptera elementu listy głosowania i jego wypełnienie
                                final ListAdapter votingsListAdapter = new ArrayAdapter<String>(EndedVotingsActivity.this, android.R.layout.simple_list_item_1 , votingsList);
                                lvLista.setAdapter(votingsListAdapter);

                                lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        String selectedVoting = String.valueOf(lvLista.getItemAtPosition(position));

                                        Response.Listener<String> getVotingInfoResponseListener = new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    JSONObject getVotingInfoResponse = new JSONObject(response);
                                                    Log.i("getVotingInfoResponse", getVotingInfoResponse.toString());
                                                    boolean success = getVotingInfoResponse.getBoolean("success");

                                                    if (success) {
                                                        int optionsCount = getVotingInfoResponse.optInt("options_count"); // liczba opcji głosowania
                                                        String opis = getVotingInfoResponse.getString("description");
                                                        tvInfo.setText(opis);
                                                        String[] optionsList = new String[optionsCount]; // lista opcji głosowania
                                                        String iterator;
                                                        // wypełnienie listy wariantów głosowania
                                                        for (int i = 0; i < optionsCount; i++) {
                                                            iterator = Integer.toString(i + 1);
                                                            optionsList[i] = getVotingInfoResponse.getString(iterator);
                                                        }

                                                        String glos;
                                                        for (int i = 0; i < optionsCount; i++) {
                                                            iterator = Integer.toString(i+1+optionsCount);
                                                            switch (Integer.valueOf(getVotingInfoResponse.getString(iterator))){
                                                                case 1:
                                                                    glos = " głos";
                                                                    break;
                                                                case 2:
                                                                    glos = " głosy";
                                                                    break;
                                                                case 3:
                                                                    glos = " głosy";
                                                                    break;
                                                                case 4:
                                                                    glos = " głosy";
                                                                    break;
                                                                default:
                                                                    glos = " głosów";

                                                            }
                                                            optionsList[i] = optionsList[i] + " :  " + getVotingInfoResponse.getString(iterator) + glos;
                                                        }

                                                        final ListAdapter optionsListAdapter = new ArrayAdapter<String>(EndedVotingsActivity.this, android.R.layout.simple_list_item_1 , optionsList);
                                                        lvLista.setAdapter(optionsListAdapter);

                                                        btWroc.setVisibility(View.GONE);
                                                        btWroc2.setVisibility(View.VISIBLE);

                                                        btWroc2.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                btWroc2.setVisibility(View.GONE);
                                                                btWroc.setVisibility(View.VISIBLE);
                                                                tvInfo.setText("");
                                                                lvLista.setAdapter(votingsListAdapter);
                                                            }
                                                        });

                                                    }

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                        };


                                        GetVotingInfoRequest getVotingInfoRequest = new GetVotingInfoRequest(selectedVoting, getVotingInfoResponseListener);
                                        queue.add(getVotingInfoRequest);
                                    }
                                });



                            } else {
                                boolean emptyfield = getVotingsResponse.getBoolean("emptyfield");
                                if (emptyfield) {
                                    tvInfo.setText("Brak zakończonych głosowań");
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EndedVotingsActivity.this);
                builder.setMessage("Błąd podczas wczytywania listy głosowań")
                        .setNegativeButton("Spróbuj ponownie", null)
                        .create()
                        .show();
            }
        });

        queue.add(endedVotingsRequest);

        btWroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
