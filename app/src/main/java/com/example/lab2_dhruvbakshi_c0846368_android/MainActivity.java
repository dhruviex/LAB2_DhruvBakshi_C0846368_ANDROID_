package com.example.lab2_dhruvbakshi_c0846368_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    public Boolean onCreateOptionMenu(Menu menu) {

        MenuItem.OnActionExpandListener onActionExpandListener=new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                Toast.makeText(MainActivity.this,"Search Is Expand",Toast.LENGTH_SHORT).show();

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                Toast.makeText(MainActivity.this,"Search Is Collapse",Toast.LENGTH_SHORT).show();

                return true;
            }
        };
        menu.findItem(R.id.action_search).setOnActionExpandListener(onActionExpandListener);
        SearchView searchView=(SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("Search Data here...");
         return true;
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