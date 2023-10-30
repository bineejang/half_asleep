package com.example.half_asleep;

import static com.example.half_asleep.Viewprofile.StringToBitmap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class View_post extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_commu_detail);
        String url = "http://58.126.238.66:9900/posts/";
        RequestQueue queue;
        queue = Volley.newRequestQueue(this);
        String postID = getIntent().getStringExtra("postID");
        url = url + postID;
        ImageView postimg = findViewById(R.id.iv_foodpic);
        TextView Date = findViewById(R.id.tv_date);
        EditText content = findViewById(R.id.et_content);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            //응답받은 JSONObject 에서 데이터 꺼내오기
            @Override
            public void onResponse(JSONObject response) {
                try {
                    postimg.setImageBitmap(StringToBitmap(response.getString("image_data")));
                    Date.setText(response.getString("post_date"));
                    content.setText(response.getString("post_content"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(View_post.this, "error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
    }
}
