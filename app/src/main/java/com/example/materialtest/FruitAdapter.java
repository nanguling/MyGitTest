package com.example.materialtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private Context mContext;

    public FruitAdapter(List<Fruit> fruits) {
        this.fruits = fruits;
    }

    private List<Fruit> fruits = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView fruitIamge;
        TextView fruitName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            fruitIamge = (ImageView) itemView.findViewById(R.id.fruitImage);
            fruitName = (TextView) itemView.findViewById(R.id.fruitName);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.fruit_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruit fruit = fruits.get(position);
        holder.fruitName.setText(fruit.getName());
        //load()方法加载图片，可以传入URL地址、本地路径、id资源
        //into()方法将图片设置到具体的某一个ImageView中
        Glide.with(mContext).load(fruit.getImageId()).into(holder.fruitIamge);
    }

    @Override
    public int getItemCount() {
        return fruits.size();
    }
}
