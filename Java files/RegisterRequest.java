package com.example.alexh.msge;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexh on 24.04.2017.
 */

public class RegisterRequest extends StringRequest {

    //private static final String REGISTER_REQUEST_URL = "http://testglosowanie.000webhostapp.com/Register.php";
    private static final String REGISTER_REQUEST_URL = "http://glosowanie.000webhostapp.com/Register.php";

    private Map<String, String> params;

    public RegisterRequest(String imie, String nazwisko, int pesel, String nazwauzytkownika, String haslo, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("imie", imie);
        params.put("nazwisko", nazwisko);
        params.put("pesel", pesel + "");
        params.put("nazwauzytkownika", nazwauzytkownika);
        params.put("haslo", haslo);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}