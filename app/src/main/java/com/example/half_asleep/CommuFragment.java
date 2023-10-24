package com.example.half_asleep;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommuFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CommuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommuFragment newInstance(String param1, String param2) {
        CommuFragment fragment = new CommuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    ArrayList<String> list;
    ArrayList<String> list_name;
    ArrayList<String> list_prf;
    ArrayList<String> list_date;
    ArrayList<String> list_thumb;
    ArrayList<String> list_content;
    String myId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commu, container, false);
        list = new ArrayList<>();
        list.add("사용자 1");
        list.add("사용자 2");
        list.add("사용자 3");
        list.add("사용자 4");


        RequestQueue queue;
        queue = Volley.newRequestQueue(getContext());
        String url ="http://58.126.238.66:9900/get_posts_friends/";

        url = url+myId;
/*
        JsonObjectRequest jsonObjectRequest_i = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            //응답받은 JSONObject 에서 데이터 꺼내오기
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray id = response.getJSONArray("id");
                    for (int i = 0; i < id.length(); i++) {
                    JSONObject idobj = id.getJSONObject(i);
                    list.add(idobj.getString("id"));
                    }
                    JSONArray prf = response.getJSONArray("profileImage");
                    for (int i = 0; i < id.length(); i++) {
                        JSONObject idobj = prf.getJSONObject(i);
                        list_prf.add(idobj.getString("profileImage"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest_i);
*/

        Adapter adapter = new Adapter(list);

        RecyclerView recyclerView = view.findViewById(R.id.rv_diary);

        recyclerView.setAdapter(adapter);

        return view;
    }
}