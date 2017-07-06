package com.example.alexh.msge;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etLogin = (EditText) findViewById(R.id.etLogin);
        final EditText etHaslo = (EditText) findViewById(R.id.etHaslo);
        final Button btZaloguj = (Button) findViewById(R.id.bZaloguj);
        final TextView tvZarejestuj = (TextView) findViewById(R.id.tvZarejestruj);
        final RequestQueue queue = Volley.newRequestQueue(this);

        tvZarejestuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentZarejetruj = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intentZarejetruj);
            }
        });

        btZaloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login = "";
                String haslo = "";
                boolean pustePola = false;

                if (etLogin.getText().toString().length()>0
                        && etHaslo.getText().toString().length()>0){
                    login = etLogin.getText().toString();
                    haslo = etHaslo.getText().toString();
                }else {
                    pustePola = true;
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("Pola nie mogą być puste")
                            .setNegativeButton("Spróbuj ponownie", null)
                            .create()
                            .show();
                }

                final String finalLogin = login;
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // (...)
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            Log.i("jsonresponse", jsonResponse.toString());
                            boolean success = jsonResponse.getBoolean("success");
                            boolean incorruser = jsonResponse.getBoolean("incorruser");
                            boolean incorrpass = jsonResponse.getBoolean("incorrpass");

                            if (success){
                                int user_id = jsonResponse.optInt("user_id");
                                String imie = jsonResponse.optString("name");
                                String nazwisko = jsonResponse.optString("nazwisko");
                                int pesel = jsonResponse.optInt("pesel");
                                boolean admin = jsonResponse.getBoolean("admin");

                                Intent intent = new Intent(LoginActivity.this, LoggedInActivity.class);
                                intent.putExtra("user_id", user_id);
                                intent.putExtra("imie", imie);
                                intent.putExtra("nazwisko", nazwisko);
                                intent.putExtra("pesel", pesel);
                                intent.putExtra("login", finalLogin);
                                intent.putExtra("admin", admin);
                                LoginActivity.this.startActivity(intent);
                            }
                            else {
                                if (incorruser) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("Nie ma takiego użytkownika")
                                            .setNegativeButton("Spróbuj ponownie", null)
                                            .create()
                                            .show();
                                }
                                else if (incorrpass){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("Podano nieprawidłowe hasło")
                                            .setNegativeButton("Spróbuj ponownie", null)
                                            .create()
                                            .show();
                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("Logowanie zakończone niepowodzeniem")
                                            .setNegativeButton("Spróbuj ponownie", null)
                                            .create()
                                            .show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                if (!pustePola) {
                    LoginRequest loginRequest = new LoginRequest(login, haslo, responseListener);
                    queue.add(loginRequest);
                }
            }
        });

    }
}
