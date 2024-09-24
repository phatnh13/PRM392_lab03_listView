package com.example.lab_03_listview;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FruitAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Fruit> fruitList;

    public FruitAdapter(List<Fruit> fruitList, Context context, int layout) {
        this.fruitList = fruitList;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return fruitList.size();
    }

    @Override
    public Object getItem(int i) {
        return fruitList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            holder = new ViewHolder();
            holder.txtName = convertView.findViewById(R.id.title);
            holder.txtDescription = convertView.findViewById(R.id.description);
            holder.image = convertView.findViewById(R.id.image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Fruit fruit = fruitList.get(i);
        holder.txtName.setText(fruit.getName());
        holder.txtDescription.setText(fruit.getDescription());

        // Use Glide to load the image
        if (fruit.getImageUri() != null) {
            Glide.with(context)
                    .load(fruit.getImageUri())
                    .into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.banana);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView txtName;
        TextView txtDescription;
        ImageView image;
    }
}

