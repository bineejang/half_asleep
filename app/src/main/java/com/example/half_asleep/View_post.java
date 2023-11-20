package com.example.half_asleep;

import static com.example.half_asleep.Viewprofile.StringToBitmap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class View_post extends AppCompatActivity {
    String myPin;
    ArrayList<CommentEntry> list = new ArrayList<CommentEntry>();
    CommentEntry Entry;
    private String formatDate(String dateString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss 'GMT'", Locale.ENGLISH);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH);
            Date date = inputFormat.parse(dateString);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString; // 포맷 변경에 실패한 경우, 원래 날짜 값을 그대로 반환
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_commu_detail);
        String url = "http://58.126.238.66:9900/posts/";
        RequestQueue queue;
        queue = Volley.newRequestQueue(this);
        SharedPreferences pref;
        SharedPreferences.Editor editor;
        pref = getSharedPreferences("pin", 0);
        myPin = pref.getString("pin","");
        String postID = getIntent().getStringExtra("postID");
        url = url + postID;
        ImageView postimg = findViewById(R.id.iv_foodpic);
        TextView Date = findViewById(R.id.tv_date);
        EditText content = findViewById(R.id.et_content);
        ImageView Edit_post = findViewById(R.id.iv_edit);
        ImageView Del_post = findViewById(R.id.iv_trash);

        RecyclerView recyclerView = findViewById(R.id.comment);

        Edit_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(View_post.this, Edit_post.class);
                BitmapDrawable drawable = (BitmapDrawable) postimg.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("image", byteArray);
                intent.putExtra("content",content.getText().toString());
                intent.putExtra("postID",postID);
                startActivity(intent);
                finish();
            }
        });
        Del_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String url = "http://58.126.238.66:9900/posts/";
                    url= url +postID;
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
                    //응답받은 JSONObject 에서 데이터 꺼내오기
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(View_post.this, "삭제 완료" , Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(View_post.this, "error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(jsonObjectRequest);
            }
        });

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            //응답받은 JSONObject 에서 데이터 꺼내오기
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String pin;
                    postimg.setImageBitmap(StringToBitmap(response.getString("image_data")));
                    Date.setText(formatDate(response.getString("post_date")));
                    content.setText(response.getString("post_content"));
                    pin=response.getString("pin");

                    if(!pin.equals(myPin)){
                        Edit_post.setVisibility(View.GONE);
                        Del_post.setVisibility(View.GONE);
                    }
                    CommentAdapter commentAdapter = new CommentAdapter(list);
                    String url_c = "http://58.126.238.66:9900/comments/";
                    url_c = url_c+postID;
                    JsonArrayRequest jsonObjectRequest_c = new JsonArrayRequest(Request.Method.GET, url_c, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject idobj = response.getJSONObject(i);
                                    Entry = new CommentEntry(
                                            idobj.getString("comment_id"),
                                            idobj.getString("member_name"),
                                            idobj.getString("comment_date"),
                                            idobj.getString("member_prf"),
                                            idobj.getString("pin"),
                                            idobj.getString("comment_content")
                                    );
                                    list.add(Entry);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            recyclerView.setAdapter(commentAdapter);
                        }

                        //응답받은 JSONObject 에서 데이터 꺼내오
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            content.setText(error.toString());
                        }
                    });
                    queue.add(jsonObjectRequest_c);

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
