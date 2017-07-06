package com.example.alexh.msge;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // inicjalizacja elementów
        final EditText etImie = (EditText) findViewById(R.id.etImie);
        final EditText etNazwisko = (EditText) findViewById(R.id.etNazwisko);
        final EditText etPESEL = (EditText) findViewById(R.id.etPESEL);
        final EditText etLogin = (EditText) findViewById(R.id.etLogin);
        final EditText etHaslo = (EditText) findViewById(R.id.etHaslo);
        final Button btZarejestruj = (Button) findViewById(R.id.bZarejetruj);
        final Button btWroc = (Button) findViewById(R.id.btWroc);

        btWroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // konfiguracja przycisku "Zarejestruj"
        btZarejestruj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((etImie.getText().length() > 0) &&
                        (etNazwisko.getText().length() > 0) &&
                        (etPESEL.getText().length() == 9) &&
                        (etLogin.getText().length() > 0) &&
                        (etHaslo.getText().length() > 0)){

                    final String imie = etImie.getText().toString();
                    final String nazwisko = etNazwisko.getText().toString();
                    final String login = etLogin.getText().toString();
                    final String haslo = etHaslo.getText().toString();
                    final String pesel = etPESEL.getText().toString();
                    final int peselint = Integer.parseInt(pesel);

                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);

                    // on Response
                    Response.Listener<String> responceListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                Log.i("jsonresponse", jsonResponse.toString());
                                boolean success = jsonResponse.getBoolean("success");
                                String error = "";
                                error = jsonResponse.optString("error");
                                //boolean usernameexists = jsonResponse.getBoolean("usernameexists");
                                //boolean emptyfields = jsonResponse.getBoolean("emptyfields");

                                if (success){
                                    Toast.makeText(getApplicationContext(), "Rejestracja zakończona powodzeniem", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else if(jsonResponse.getBoolean("emptyfields")){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("Pola nię mogą być puste")
                                            .setNegativeButton("Spróbuj ponownie", null)
                                            .create()
                                            .show();
                                }
                                else if(error.length()>0){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage(error)
                                            .setNegativeButton("Spróbuj ponownie", null)
                                            .create()
                                            .show();
                                }
                                else if(jsonResponse.getBoolean("usernameexists")){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("Już istnieje taki użytkownik, spróbuj innej nazwy")
                                            .setNegativeButton("Spróbuj ponownie", null)
                                            .create()
                                            .show();
                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("Niepowodzenie rejestracji")
                                            .setNegativeButton("Spróbuj ponownie", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Ups, coś nie działa")
                                        .setNegativeButton("Spróbuj ponownie", null)
                                        .create()
                                        .show();
                            }
                        }
                    };

                    RegisterRequest registerRequest = new RegisterRequest(imie, nazwisko, peselint, login, haslo, responceListener);
                    queue.add(registerRequest);
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Wypełnij poprawnie wszystkie pola")
                            .setNegativeButton("Spróbuj ponownie", null)
                            .create()
                            .show();
                }

            }
        });
    }
}
