package com.example.alexh.msge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoggedInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        // inicjalizacja elementów
        final TextView tvHelloMessage = (TextView) findViewById(R.id.tvHelloMessage);
        final TextView tvUserId = (TextView) findViewById(R.id.tvUserId);
        final TextView tvImie = (TextView) findViewById(R.id.tvImie);
        final TextView tvNazwisko = (TextView) findViewById(R.id.tvNazwisko);
        final TextView tvPesel = (TextView) findViewById(R.id.tvPesel);

        final Button btCreateVoting = (Button) findViewById(R.id.btCreateVoting);
        final Button btOngoingVotings = (Button) findViewById(R.id.btOngoingVotings);
        final Button btUpcomingVotings = (Button) findViewById(R.id.btUpcomingPolls);
        final Button btEndedVotings = (Button) findViewById(R.id.btEndedVotings);
        final Button btBack = (Button) findViewById(R.id.btBackLogged);

        // inicjalizacja danych odebranych po zalogowaniu
        final Intent intent = getIntent();
        String imie = intent.getStringExtra("imie");
        String nazwisko = intent.getStringExtra("nazwisko");
        int pesel = intent.getIntExtra("pesel", -1);
        int userId = intent.getIntExtra("user_id", -1);
        String login = intent.getStringExtra("login");
        boolean admin = intent.getBooleanExtra("admin", false);

        // przywitanie użytkownika, przypisanie elementom danych odebranych po zalogowaniu,
        // jeśli użytkownik pełni funkcję administratora zmiana  atrybutu widoczności przycisku
        // na widzoczny
        tvHelloMessage.setText(tvHelloMessage.getText().toString()+login+"!");
        tvUserId.setText(tvUserId.getText().toString()+userId);
        if (admin) {
            tvUserId.append("(Admin)");
            btCreateVoting.setVisibility(View.VISIBLE);
        }
        tvImie.setText(tvImie.getText().toString()+imie);
        tvNazwisko.setText(tvNazwisko.getText().toString()+nazwisko);
        tvPesel.setText(tvPesel.getText().toString()+pesel);

        // po naciśnięciu przycisku otwiera CreateVotingActivity
        btCreateVoting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCreateVoing = new Intent(LoggedInActivity.this, CreateVotingActivity.class);
                LoggedInActivity.this.startActivity(intentCreateVoing);
            }

        });
        // po naciśnięciu przycisku otwiera OngoingVotingsActivity
        btOngoingVotings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPolls = new Intent(LoggedInActivity.this, OngoingVotingsActivity.class);
                String login = intent.getStringExtra("login");
                intentPolls.putExtra("login", login);
                LoggedInActivity.this.startActivity(intentPolls);
            }
        });
        // po naciśnięciu przycisku otwiera EndedVotingsActivity
        btEndedVotings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEndedPolls = new Intent(LoggedInActivity.this, EndedVotingsActivity.class);
                LoggedInActivity.this.startActivity(intentEndedPolls);
            }
        });
        // po naciśnięciu przycisku otwiera UpcomingVotingsActivity
        btUpcomingVotings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUpcomingPolls = new Intent(LoggedInActivity.this, UpcomingVotingsActivity.class);
                LoggedInActivity.this.startActivity(intentUpcomingPolls);
            }
        });
        //// po naciśnięciu przycisku zamyka LoggedInActivity
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
