package com.example.alexh.msge;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateVotingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_voting);

        // inicjalizacja elementów
        final EditText etNazwaGlosowania = (EditText) findViewById(R.id.etNazwaGlos);
        final EditText etOpcje = (EditText) findViewById(R.id.etOpcje);
        final EditText etStartingDate = (EditText) findViewById(R.id.etStartingDate);
        final EditText etEndingDate = (EditText) findViewById(R.id.etEndingDate);
        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        final EditText etOpis = (EditText) findViewById(R.id.etOpis);
        final EditText etOption1 = (EditText) findViewById(R.id.etOption1);
        final EditText etOption2 = (EditText) findViewById(R.id.etOption2);
        final EditText etOption3 = (EditText) findViewById(R.id.etOption3);
        final EditText etOption4 = (EditText) findViewById(R.id.etOption4);
        final EditText etOption5 = (EditText) findViewById(R.id.etOption5);
        final EditText etOption6 = (EditText) findViewById(R.id.etOption6);
        final EditText etOption7 = (EditText) findViewById(R.id.etOption7);
        final EditText etOption8 = (EditText) findViewById(R.id.etOption8);
        final EditText etOption9 = (EditText) findViewById(R.id.etOption9);
        final EditText etOption10 = (EditText) findViewById(R.id.etOption10);

        final Button btNextCreate = (Button) findViewById(R.id.btNextCreate);
        final Button btNextCreate2 = (Button) findViewById(R.id.btNextCreate2);
        final Button btBackCreate = (Button) findViewById(R.id.btBackCreate);
        final Button btBackCreate2 = (Button) findViewById(R.id.btBackCreate2);

        // konfiguracja przycisku "Dalej 1"
        btNextCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etNazwaGlosowania.getText().length()>0
                        && etOpcje.getText().length()>0
                        && etStartingDate.getText().length()>0
                        && etEndingDate.getText().length()>0){
                    final String nazwaGlosowania = etNazwaGlosowania.getText().toString();
                    final String liczbaOpcjiString = etOpcje.getText().toString();
                    final int liczbaOpcji = Integer.parseInt(liczbaOpcjiString);   //Parsing String to Integer
                    final String startDate = etStartingDate.getText().toString();
                    final String endDate = etEndingDate.getText().toString();
                    final String opis = etOpis.getText().toString();

                    etNazwaGlosowania.setVisibility(View.INVISIBLE);
                    etOpcje.setVisibility(View.INVISIBLE);
                    etStartingDate.setVisibility(View.INVISIBLE);
                    etEndingDate.setVisibility(View.INVISIBLE);
                    etOpis.setVisibility(View.INVISIBLE);
                    btNextCreate.setVisibility(View.INVISIBLE);
                    btBackCreate.setVisibility(View.INVISIBLE);
                    checkBox.setVisibility(View.INVISIBLE);

                    if (liczbaOpcji>=10){
                        etOption1.setVisibility(View.VISIBLE);
                        etOption2.setVisibility(View.VISIBLE);
                        etOption3.setVisibility(View.VISIBLE);
                        etOption4.setVisibility(View.VISIBLE);
                        etOption5.setVisibility(View.VISIBLE);
                        etOption6.setVisibility(View.VISIBLE);
                        etOption7.setVisibility(View.VISIBLE);
                        etOption8.setVisibility(View.VISIBLE);
                        etOption9.setVisibility(View.VISIBLE);
                        etOption10.setVisibility(View.VISIBLE);
                    }
                    else{
                        switch (liczbaOpcji){
                            case 1:
                                etOption1.setVisibility(View.VISIBLE);
                                break;
                            case 2:
                                etOption1.setVisibility(View.VISIBLE);
                                etOption2.setVisibility(View.VISIBLE);
                                break;
                            case 3:
                                etOption1.setVisibility(View.VISIBLE);
                                etOption2.setVisibility(View.VISIBLE);
                                etOption3.setVisibility(View.VISIBLE);
                                break;
                            case 4:
                                etOption1.setVisibility(View.VISIBLE);
                                etOption2.setVisibility(View.VISIBLE);
                                etOption3.setVisibility(View.VISIBLE);
                                etOption4.setVisibility(View.VISIBLE);
                                break;
                            case 5:
                                etOption1.setVisibility(View.VISIBLE);
                                etOption2.setVisibility(View.VISIBLE);
                                etOption3.setVisibility(View.VISIBLE);
                                etOption4.setVisibility(View.VISIBLE);
                                etOption5.setVisibility(View.VISIBLE);
                                break;
                            case 6:
                                etOption1.setVisibility(View.VISIBLE);
                                etOption2.setVisibility(View.VISIBLE);
                                etOption3.setVisibility(View.VISIBLE);
                                etOption4.setVisibility(View.VISIBLE);
                                etOption5.setVisibility(View.VISIBLE);
                                etOption6.setVisibility(View.VISIBLE);
                                break;
                            case 7:
                                etOption1.setVisibility(View.VISIBLE);
                                etOption2.setVisibility(View.VISIBLE);
                                etOption3.setVisibility(View.VISIBLE);
                                etOption4.setVisibility(View.VISIBLE);
                                etOption5.setVisibility(View.VISIBLE);
                                etOption6.setVisibility(View.VISIBLE);
                                etOption7.setVisibility(View.VISIBLE);
                                break;
                            case 8:
                                etOption1.setVisibility(View.VISIBLE);
                                etOption2.setVisibility(View.VISIBLE);
                                etOption3.setVisibility(View.VISIBLE);
                                etOption4.setVisibility(View.VISIBLE);
                                etOption5.setVisibility(View.VISIBLE);
                                etOption6.setVisibility(View.VISIBLE);
                                etOption7.setVisibility(View.VISIBLE);
                                etOption8.setVisibility(View.VISIBLE);
                                break;
                            case 9:
                                etOption1.setVisibility(View.VISIBLE);
                                etOption2.setVisibility(View.VISIBLE);
                                etOption3.setVisibility(View.VISIBLE);
                                etOption4.setVisibility(View.VISIBLE);
                                etOption5.setVisibility(View.VISIBLE);
                                etOption6.setVisibility(View.VISIBLE);
                                etOption7.setVisibility(View.VISIBLE);
                                etOption8.setVisibility(View.VISIBLE);
                                etOption9.setVisibility(View.VISIBLE);
                                break;
                            case 10:
                                etOption1.setVisibility(View.VISIBLE);
                                etOption2.setVisibility(View.VISIBLE);
                                etOption3.setVisibility(View.VISIBLE);
                                etOption4.setVisibility(View.VISIBLE);
                                etOption5.setVisibility(View.VISIBLE);
                                etOption6.setVisibility(View.VISIBLE);
                                etOption7.setVisibility(View.VISIBLE);
                                etOption8.setVisibility(View.VISIBLE);
                                etOption9.setVisibility(View.VISIBLE);
                                etOption10.setVisibility(View.VISIBLE);
                                break;
                        }
                    }

                    btBackCreate2.setVisibility(View.VISIBLE);
                    btNextCreate2.setVisibility(View.VISIBLE);

                    // Przycisk "Dalej 2"
                    btNextCreate2.setOnClickListener(new View.OnClickListener() {
                        int counter = liczbaOpcji;
                        //String globalOptions = "";
                        String options = "";


                        @Override
                        public void onClick(View v) {

                            //Jeśli do dodania jest ponad 10 nowych opcji
                            if (counter>10){

                                if (options.isEmpty())
                                    options = etOption1.getText().toString();
                                else
                                    options = options + ", " + etOption1.getText().toString();
                                options = options + ", " + etOption2.getText().toString();
                                options = options + ", " + etOption3.getText().toString();
                                options = options + ", " + etOption4.getText().toString();
                                options = options + ", " + etOption5.getText().toString();
                                options = options + ", " + etOption6.getText().toString();
                                options = options + ", " + etOption7.getText().toString();
                                options = options + ", " + etOption8.getText().toString();
                                options = options + ", " + etOption9.getText().toString();
                                options = options + ", " + etOption10.getText().toString();

                                etOption1.setText("");
                                etOption2.setText("");
                                etOption3.setText("");
                                etOption4.setText("");
                                etOption5.setText("");
                                etOption6.setText("");
                                etOption7.setText("");
                                etOption8.setText("");
                                etOption9.setText("");
                                etOption10.setText("");

                                counter -= 10;
                                if (counter<=10){
                                    etOption1.setVisibility(View.INVISIBLE);
                                    etOption2.setVisibility(View.INVISIBLE);
                                    etOption3.setVisibility(View.INVISIBLE);
                                    etOption4.setVisibility(View.INVISIBLE);
                                    etOption5.setVisibility(View.INVISIBLE);
                                    etOption6.setVisibility(View.INVISIBLE);
                                    etOption7.setVisibility(View.INVISIBLE);
                                    etOption8.setVisibility(View.INVISIBLE);
                                    etOption9.setVisibility(View.INVISIBLE);
                                    etOption10.setVisibility(View.INVISIBLE);
                                    switch (counter){
                                        case 1:
                                            etOption1.setVisibility(View.VISIBLE);
                                            break;
                                        case 2:
                                            etOption1.setVisibility(View.VISIBLE);
                                            etOption2.setVisibility(View.VISIBLE);
                                            break;
                                        case 3:
                                            etOption1.setVisibility(View.VISIBLE);
                                            etOption2.setVisibility(View.VISIBLE);
                                            etOption3.setVisibility(View.VISIBLE);
                                            break;
                                        case 4:
                                            etOption1.setVisibility(View.VISIBLE);
                                            etOption2.setVisibility(View.VISIBLE);
                                            etOption3.setVisibility(View.VISIBLE);
                                            etOption4.setVisibility(View.VISIBLE);
                                            break;
                                        case 5:
                                            etOption1.setVisibility(View.VISIBLE);
                                            etOption2.setVisibility(View.VISIBLE);
                                            etOption3.setVisibility(View.VISIBLE);
                                            etOption4.setVisibility(View.VISIBLE);
                                            etOption5.setVisibility(View.VISIBLE);
                                            break;
                                        case 6:
                                            etOption1.setVisibility(View.VISIBLE);
                                            etOption2.setVisibility(View.VISIBLE);
                                            etOption3.setVisibility(View.VISIBLE);
                                            etOption4.setVisibility(View.VISIBLE);
                                            etOption5.setVisibility(View.VISIBLE);
                                            etOption6.setVisibility(View.VISIBLE);
                                            break;
                                        case 7:
                                            etOption1.setVisibility(View.VISIBLE);
                                            etOption2.setVisibility(View.VISIBLE);
                                            etOption3.setVisibility(View.VISIBLE);
                                            etOption4.setVisibility(View.VISIBLE);
                                            etOption5.setVisibility(View.VISIBLE);
                                            etOption6.setVisibility(View.VISIBLE);
                                            etOption7.setVisibility(View.VISIBLE);
                                            break;
                                        case 8:
                                            etOption1.setVisibility(View.VISIBLE);
                                            etOption2.setVisibility(View.VISIBLE);
                                            etOption3.setVisibility(View.VISIBLE);
                                            etOption4.setVisibility(View.VISIBLE);
                                            etOption5.setVisibility(View.VISIBLE);
                                            etOption6.setVisibility(View.VISIBLE);
                                            etOption7.setVisibility(View.VISIBLE);
                                            etOption8.setVisibility(View.VISIBLE);
                                            break;
                                        case 9:
                                            etOption1.setVisibility(View.VISIBLE);
                                            etOption2.setVisibility(View.VISIBLE);
                                            etOption3.setVisibility(View.VISIBLE);
                                            etOption4.setVisibility(View.VISIBLE);
                                            etOption5.setVisibility(View.VISIBLE);
                                            etOption6.setVisibility(View.VISIBLE);
                                            etOption7.setVisibility(View.VISIBLE);
                                            etOption8.setVisibility(View.VISIBLE);
                                            etOption9.setVisibility(View.VISIBLE);
                                            break;
                                        case 10:
                                            etOption1.setVisibility(View.VISIBLE);
                                            etOption2.setVisibility(View.VISIBLE);
                                            etOption3.setVisibility(View.VISIBLE);
                                            etOption4.setVisibility(View.VISIBLE);
                                            etOption5.setVisibility(View.VISIBLE);
                                            etOption6.setVisibility(View.VISIBLE);
                                            etOption7.setVisibility(View.VISIBLE);
                                            etOption8.setVisibility(View.VISIBLE);
                                            etOption9.setVisibility(View.VISIBLE);
                                            etOption10.setVisibility(View.VISIBLE);
                                            break;
                                    }
                                }


                            }
                            //Jeśli do dodania jest 10 lub mniej opcji
                            else if (counter<=10){

                                switch (counter){
                                    case 1:
                                        if (options.isEmpty())
                                            options = etOption1.getText().toString();
                                        else
                                            options = options + ", " + etOption1.getText().toString();
                                        break;
                                    case 2:
                                        if (options.isEmpty())
                                            options = etOption1.getText().toString();
                                        else
                                            options = options + ", " + etOption1.getText().toString();
                                        options = options + ", " + etOption2.getText().toString();
                                        break;
                                    case 3:
                                        if (options.isEmpty())
                                            options = etOption1.getText().toString();
                                        else
                                            options = options + ", " + etOption1.getText().toString();
                                        options = options + ", " + etOption2.getText().toString();
                                        options = options + ", " + etOption3.getText().toString();
                                        break;
                                    case 4:
                                        if (options.length()==0)
                                            options = etOption1.getText().toString();
                                        else
                                            options = options + ", " + etOption1.getText().toString();
                                        options = options + ", " + etOption2.getText().toString();
                                        options = options + ", " + etOption3.getText().toString();
                                        options = options + ", " + etOption4.getText().toString();
                                        break;
                                    case 5:
                                        if (options.isEmpty())
                                            options = etOption1.getText().toString();
                                        else
                                            options = options + ", " + etOption1.getText().toString();
                                        options = options + ", " + etOption2.getText().toString();
                                        options = options + ", " + etOption3.getText().toString();
                                        options = options + ", " + etOption4.getText().toString();
                                        options = options + ", " + etOption5.getText().toString();
                                        break;
                                    case 6:
                                        if (options.isEmpty())
                                            options = etOption1.getText().toString();
                                        else
                                            options = options + ", " + etOption1.getText().toString();
                                        options = options + ", " + etOption2.getText().toString();
                                        options = options + ", " + etOption3.getText().toString();
                                        options = options + ", " + etOption4.getText().toString();
                                        options = options + ", " + etOption5.getText().toString();
                                        options = options + ", " + etOption6.getText().toString();
                                        break;
                                    case 7:
                                        if (options.isEmpty())
                                            options = etOption1.getText().toString();
                                        else
                                            options = options + ", " + etOption1.getText().toString();
                                        options = options + ", " + etOption2.getText().toString();
                                        options = options + ", " + etOption3.getText().toString();
                                        options = options + ", " + etOption4.getText().toString();
                                        options = options + ", " + etOption5.getText().toString();
                                        options = options + ", " + etOption6.getText().toString();
                                        options = options + ", " + etOption7.getText().toString();
                                        break;
                                    case 8:
                                        if (options.isEmpty())
                                            options = etOption1.getText().toString();
                                        else
                                            options = options + ", " + etOption1.getText().toString();
                                        options = options + ", " + etOption2.getText().toString();
                                        options = options + ", " + etOption3.getText().toString();
                                        options = options + ", " + etOption4.getText().toString();
                                        options = options + ", " + etOption5.getText().toString();
                                        options = options + ", " + etOption6.getText().toString();
                                        options = options + ", " + etOption7.getText().toString();
                                        options = options + ", " + etOption8.getText().toString();
                                        break;
                                    case 9:
                                        if (options.isEmpty())
                                            options = etOption1.getText().toString();
                                        else
                                            options = options + ", " + etOption1.getText().toString();
                                        options = options + ", " + etOption2.getText().toString();
                                        options = options + ", " + etOption3.getText().toString();
                                        options = options + ", " + etOption4.getText().toString();
                                        options = options + ", " + etOption5.getText().toString();
                                        options = options + ", " + etOption6.getText().toString();
                                        options = options + ", " + etOption7.getText().toString();
                                        options = options + ", " + etOption8.getText().toString();
                                        options = options + ", " + etOption9.getText().toString();
                                        break;
                                    case 10:
                                        if (options.isEmpty())
                                            options = etOption1.getText().toString();
                                        else
                                            options = options + ", " + etOption1.getText().toString();
                                        options = options + ", " + etOption2.getText().toString();
                                        options = options + ", " + etOption3.getText().toString();
                                        options = options + ", " + etOption4.getText().toString();
                                        options = options + ", " + etOption5.getText().toString();
                                        options = options + ", " + etOption6.getText().toString();
                                        options = options + ", " + etOption7.getText().toString();
                                        options = options + ", " + etOption8.getText().toString();
                                        options = options + ", " + etOption9.getText().toString();
                                        options = options + ", " + etOption10.getText().toString();
                                        break;
                                }
                                Log.i("Options: ", options);

                                //
                                Response.Listener<String> createVotingResponseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject createVotingResponse = new JSONObject(response);
                                            boolean success = createVotingResponse.getBoolean("success");
                                            Log.i("Response: ", createVotingResponse.toString());
                                            if (success){
                                                Toast.makeText(getApplicationContext(), "Tworzenie głosowania zakończone", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };

                                CreateVotingRequest createVotingRequest = new CreateVotingRequest(nazwaGlosowania, liczbaOpcji, startDate, endDate,
                                        checkBox.isChecked(), opis, options, createVotingResponseListener);
                                RequestQueue queue = Volley.newRequestQueue(CreateVotingActivity.this);
                                queue.add(createVotingRequest);

                            }
                        }
                    });
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateVotingActivity.this);
                    builder.setMessage("Wypełnij poprawnie wszystkie pola")
                            .setNegativeButton("Spróbuj ponownie", null)
                            .create()
                            .show();
                }

            }
        });

        // Przycisk "Wróć 1"
        btBackCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

