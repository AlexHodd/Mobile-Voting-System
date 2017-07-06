package com.example.alexh.msge;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexh on 24.04.2017.
 */

public class LoginRequest extends StringRequest {

    //private static final String LOGIN_REQUEST_URL = "http://testglosowanie.000webhostapp.com/Login.php";
    private static final String LOGIN_REQUEST_URL = "http://glosowanie.000webhostapp.com/Login.php";
    private Map<String, String> params;

    public LoginRequest(String login, String haslo, Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("login", login);
        params.put("haslo", haslo);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}