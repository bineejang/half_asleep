package com.example.half_asleep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.EditText;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class SelectDiaryActivity extends AppCompatActivity {

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
    private String updateUrl = "http://58.126.238.66:9900/edit_diary";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_diary);

        String diaryId = getIntent().getStringExtra("diary_id");

        // EditText 및 저장 버튼 참조
        EditText contentEditText = findViewById(R.id.et_content);
        Button saveButton = findViewById(R.id.btn_save_diary);
        ImageView imageView = findViewById(R.id.iv_foodpic);
        TextView DateTextView = findViewById(R.id.diary_date); // TextView 참조

        // 서버로부터 데이터 요청 (Volley 사용)
        String url = "http://58.126.238.66:9900/get_diary_info";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("diary_id", diaryId); // 선택한 일기의 ID를 요청에 포함
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String[] imageUrl = {""}; // imageUrl 변수를 미리 선언
        String requestBody = jsonBody.toString(); // requestBody 변수를 선언하고 초기화

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // JSON 데이터 파싱
                            JSONObject jsonObject = new JSONObject(response);
                            String diaryDate = jsonObject.getString("diary_date");
                            String diaryContent = jsonObject.getString("diary_content");

                            // 데이터를 UI에 설정
                            contentEditText.setText(diaryContent);

                            JSONArray imagesArray = jsonObject.getJSONArray("images");
                            if (imagesArray.length() > 0) {
                                JSONObject firstImageObject = imagesArray.getJSONObject(0);
                                String firstImageData = firstImageObject.getString("image_data");
                                imageUrl[0] = "data:image/jpeg;base64," + firstImageData;
                            }

                            Glide.with(SelectDiaryActivity.this).load(imageUrl[0]).into(imageView);

                            // 날짜 포맷 변경
                            String formattedDate = formatDate(diaryDate);
                            DateTextView.setText(formattedDate); // 형식화된 날짜 표시
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 오류 처리
                        error.printStackTrace();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 수정된 일기 내용 가져오기
                String editContent = contentEditText.getText().toString();

                // 수정된 내용을 서버에 업데이트 또는 저장
                StringRequest updateRequest = new StringRequest(Request.Method.POST, updateUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // 업데이트 성공
                                // 원하는 동작 수행
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // 오류 처리
                                error.printStackTrace();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("diary_id", diaryId);
                        params.put("diary_content", editContent);
                        return params;
                    }
                };
                requestQueue.add(updateRequest);
            }
        });
    }
}
