package com.example.lab2_dhruvbakshi_c0846368_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView dataListView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        setDataAdapter();
        loadFromDBToMemory();
        setOnClickListener();


    }

    private void setOnClickListener() {
        dataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Product selectProduct = (Product) dataListView.getItemAtPosition(position);
                Intent editProductIntent = new Intent(getApplicationContext(),NewDataActivity.class);
                editProductIntent.putExtra(Product.PRODUCT_EDIT_EXTRA, selectProduct.getProdId());
                startActivity(editProductIntent);
            }
        });
    }

    private void loadFromDBToMemory() {
        DatabaseActivity databaseActivity = DatabaseActivity.instanceOfDatabase(this);
        databaseActivity.populateDataListArray();
    }


    private void initWidgets() {

        dataListView = findViewById(R.id.dataListView);
    }
    private void setDataAdapter() {
      DataAdapter dataAdapter = new DataAdapter(getApplicationContext(),Product.nonDeletedProduct());
      dataListView.setAdapter(dataAdapter);

    }
    public void NewList(View view) {
        Intent newDataIntent = new Intent(this, NewDataActivity.class);
        startActivity(newDataIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDataAdapter();
    }
}