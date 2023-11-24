package com.example.livecricketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView liveScoreTextView;
    private String apiKey = "bRJPHX22nHjnmePUJ5QKYHBB0lHxqafN0CRK0DHqKDIMD7Xw6UHQ2nMt0eHn"; // Replace with your SportsMonk API key
    private String apiUrl = "https://soccer.sportmonks.com/api/v2.0/livescores/now"; // Example API endpoint
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        liveScoreTextView = findViewById(R.id.score);

        // Make API request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl + "?api_token=" + apiKey, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String liveScore = response.getString("data");
                            liveScoreTextView.setText("Live Score: " + liveScore);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                                        @Override
                    public void onErrorResponse(VolleyError error) {
                        liveScoreTextView.setText("Live Score: Error fetching data");
                    }
                });

        // Add the request to the queue
        Volley.newRequestQueue(this).add(request);

    }
}