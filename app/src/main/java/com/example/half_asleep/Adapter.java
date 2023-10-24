package com.example.half_asleep;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Holder> {

    ArrayList<String> list;
    ArrayList<String> list_name;
    ArrayList<String> list_prf;
    ArrayList<String> list_date;
    ArrayList<String> list_thumb;
    ArrayList<String> list_content;
    Adapter(ArrayList<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_commu, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.content.setText(list.get(position));

    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}

class Holder extends RecyclerView.ViewHolder {
    TextView content,name;
    ImageView prf,thumb;


    public Holder(@NonNull View itemView) {
        super(itemView);
        content = itemView.findViewById(R.id.tv_content);
        name = itemView.findViewById(R.id.name);
        prf = itemView.findViewById(R.id.prf);
        thumb = itemView.findViewById(R.id.iv_pic);

    }
}