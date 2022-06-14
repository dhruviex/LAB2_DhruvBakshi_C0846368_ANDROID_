package com.example.lab2_dhruvbakshi_c0846368_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DataAdapter extends ArrayAdapter<Product> {
    public DataAdapter(Context context, List<Product> products)
    {
        super(context,0,products);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Product product = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.data_cell,parent,false);

        TextView name = convertView.findViewById(R.id.cellName);
        TextView description = convertView.findViewById(R.id.cellDescription);
        TextView price = convertView.findViewById(R.id.cellPrice);

        name.setText(product.getProdName());
        description.setText(product.getProdDesc());
        price.setText(product.getProdPrice());

        return convertView;
    }
}
