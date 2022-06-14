package com.example.lab2_dhruvbakshi_c0846368_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class NewDataActivity extends AppCompatActivity {
    private Product selectProduct;
    private Button deleteButton;
    private EditText nameEditText,descEditText,priceEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_data);
        initWidgets();
        checkForEditProduct();

        
    }

    private void initWidgets() {
        nameEditText = findViewById(R.id.nameEditText);
        descEditText = findViewById(R.id.descriptionEditText);
        priceEditText = findViewById(R.id.priceEditText);
        deleteButton = findViewById(R.id.deleteProductBtn);

    }

    private void checkForEditProduct() {
        Intent previousIntent = getIntent();
        int passedProductID = previousIntent.getIntExtra(Product.PRODUCT_EDIT_EXTRA,-1);
        selectProduct = Product.getProductforID(passedProductID);

        if (selectProduct != null){
            nameEditText.setText(selectProduct.getProdName());
            descEditText.setText(selectProduct.getProdDesc());
            priceEditText.setText(selectProduct.getProdPrice());
        }
        else{
            deleteButton.setVisibility(View.INVISIBLE);
        }

    }


    public void saveList(View view) {
        DatabaseActivity databaseActivity = DatabaseActivity.instanceOfDatabase(this);
        String name = String.valueOf(nameEditText.getText());
        String description = String.valueOf(descEditText.getText());
        String price = String.valueOf(priceEditText.getText());

        if (selectProduct == null) {
            int id = Product.productArrayList.size();
            Product newProduct = new Product(id, name, description, price);
            Product.productArrayList.add(newProduct);

            databaseActivity.addDataToDatabase(newProduct);
        }
        else{
            selectProduct.setProdName(name);
            selectProduct.setProdDesc(description);
            selectProduct.setProdPrice(price);
            databaseActivity.updateProductInDB(selectProduct);
        }
        finish();
    }

    public void DeleteData(View view) {
        DatabaseActivity databaseActivity = DatabaseActivity.instanceOfDatabase(this);
        selectProduct.setDeleted(new Date());
        databaseActivity.updateProductInDB(selectProduct);
        finish();


    }

}