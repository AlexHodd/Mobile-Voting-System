package com.example.alexh.msge;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexh on 25.04.2017.
 */

public class GetVotingInfoRequest extends StringRequest {

    //private static final String GET_VOTING_INFO_URL = "http://testglosowanie.000webhostapp.com/GetPollinfo.php";
    private static final String GET_VOTING_INFO_URL = "http://glosowanie.000webhostapp.com/GetVotingInfo.php";
    private Map<String, String> params;

    public GetVotingInfoRequest(String selectedVoting, Response.Listener<String> listener){
        super(Method.POST, GET_VOTING_INFO_URL, listener, null);
        params = new HashMap<>();
        params.put("voting_name", selectedVoting);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
