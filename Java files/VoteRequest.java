package com.example.alexh.msge;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexh on 25.04.2017.
 */

public class VoteRequest extends StringRequest {

    //private static final String VOTE_REQUEST_URL = "http://testglosowanie.000webhostapp.com/Vote.php";
    private static final String VOTE_REQUEST_URL = "http://glosowanie.000webhostapp.com/Vote.php";

    private Map<String, String> params;

    public VoteRequest(String selectedVoting, String login, String selectedOption, Response.Listener<String> listener){
        super(Method.POST, VOTE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("selectedVoting", selectedVoting);
        params.put("login", login);
        params.put("selectedOption", selectedOption);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}