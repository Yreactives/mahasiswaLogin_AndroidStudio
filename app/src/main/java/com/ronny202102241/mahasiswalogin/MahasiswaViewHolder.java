package com.ronny202102241.mahasiswalogin;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MahasiswaViewHolder extends RecyclerView.ViewHolder {
    public ImageView _jkImageView;
    public TextView _jkTextView;
    public TextView _jpTextView;
    public TextView _namaTextView;
    public TextView _nimTextView;

    public MahasiswaViewHolder(@NonNull View itemView) {
        super(itemView);

        _jkImageView = (ImageView) itemView.findViewById(R.id.jkImageView);
        _jkTextView = textfindview(R.id.jkTextView);
        _jpTextView = textfindview(R.id.jpTextView);
        _namaTextView = textfindview(R.id.namaTextView);
        _nimTextView = textfindview(R.id.nimTextView);
    }

    public TextView textfindview(int id){
        return (TextView) itemView.findViewById(id);
    }
}

