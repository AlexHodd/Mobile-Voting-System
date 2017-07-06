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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class VoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        // inicjalizacja elementów
        final TextView tvOpis = (TextView) findViewById(R.id.tvOpis);
        final ListView lvOpcje = (ListView) findViewById(R.id.lvOpcje);
        final Button btWroc = (Button) findViewById(R.id.btBackVote);
        final Intent intent = getIntent();

        final String selectedVoting = intent.getStringExtra("selectedVoting");

        final RequestQueue queue = Volley.newRequestQueue(this);


        Response.Listener<String> getVotingInfoResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject getVotingInfoResponse = new JSONObject(response);
                    Log.i("getVotingInfoResponse", getVotingInfoResponse.toString());
                    boolean success = getVotingInfoResponse.getBoolean("success");

                    if (success){
                        String opis = getVotingInfoResponse.getString("description");
                        tvOpis.setText(opis);
                        int optionsCount = getVotingInfoResponse.optInt("options_count"); // liczba opcji głosowania
                        String[] optionsList = new String[optionsCount]; // inicjalizacja listy opcji głosowania
                        String iterator;
                        // wypełnienie listy wariantów głosowania
                        for (int i=0; i<optionsCount; i++){
                            iterator = Integer.toString(i+1);
                            optionsList[i] = getVotingInfoResponse.getString(iterator);
                        }
                        // jeśli głosowanie sprecyzowane jest do pokazywania wyników w trakcie głosowania
                        // dodaje do każdegej opcji liczbę głosów na nią oddanych
                        if(getVotingInfoResponse.optInt("liveResults")==1){
                            String glos;
                            for(int i=0; i<optionsCount; i++){
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

                        }

                        // tworzenie adaptera elementu listy opcji głosowania i wypełnienie elementu lity
                        ListAdapter listAdapter = new ArrayAdapter<String>(VoteActivity.this, android.R.layout.simple_list_item_1 , optionsList);
                        lvOpcje.setAdapter(listAdapter);

                        lvOpcje.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String login = intent.getStringExtra("login");
                                String itemAtPosition = String.valueOf(lvOpcje.getItemAtPosition(i));
                                String selectedOption;

                                if (itemAtPosition.contains(" :  ")){
                                    int index = itemAtPosition.indexOf(" :  ");
                                    selectedOption = itemAtPosition.substring(0, index);
                                }else
                                    selectedOption = itemAtPosition;

                                final String finalSelectedOption = selectedOption;


                                Response.Listener<String> voteResponseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject voteResponse = new JSONObject(response);
                                            boolean success = voteResponse.getBoolean("success");
                                            Log.i("voteResponse: ", voteResponse.toString());

                                            if (success){
                                                Toast.makeText(getApplicationContext(), "Oddano głos na: "+finalSelectedOption, Toast.LENGTH_SHORT).show();
                                                finish();
                                            }else if(voteResponse.getBoolean("voted")){
                                                AlertDialog.Builder builder = new AlertDialog.Builder(VoteActivity.this);
                                                builder.setMessage("Twój głos został już oddany na: "+ voteResponse.getString("voted for") + "!")
                                                        .setNegativeButton("Dalej", null)
                                                        .create()
                                                        .show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };

                                VoteRequest voteRequest = new VoteRequest(selectedVoting, login, finalSelectedOption, voteResponseListener);
                                queue.add(voteRequest);
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

        btWroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}