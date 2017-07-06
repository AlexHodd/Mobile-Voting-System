package com.example.alexh.msge;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexh on 24.04.2017.
 */

public class CreateVotingRequest extends StringRequest {
    //private static final String CREATE_VOTING_URL = "http://testglosowanie.000webhostapp.com/CreateVotingObjectedv4.php";
    private static final String CREATE_VOTING_URL = "http://glosowanie.000webhostapp.com/CreateVoting.php";
    private Map<String, String> params;

    public CreateVotingRequest (String nazwaGlosowania, int liczbaOpcji, String startDate, String endDate, boolean liveResults, String opis,  String options, Response.Listener<String> listener){
        super(Method.POST, CREATE_VOTING_URL, listener, null);
        params = new HashMap<>();
        params.put("voting_name", nazwaGlosowania);
        params.put("options_count", Integer.toString(liczbaOpcji));
        params.put("description", opis);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("liveResults", Boolean.toString(liveResults));
        params.put("options", options);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}