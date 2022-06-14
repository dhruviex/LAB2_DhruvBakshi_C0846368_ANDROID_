package com.example.lab2_dhruvbakshi_c0846368_android;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseActivity extends SQLiteOpenHelper {
private static DatabaseActivity databaseActivity;
    private static final String DATABASE_NAME ="product.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME="Product";
    private static final String COUNTER="counter";

    private static final String db_id="Id";
    private static final String db_prodName="Name";
    private static final String db_prodDesc="Description";
    private static final String db_prodPrice="Price";
    private static final String db_deleted="deleted";

 private static final DateFormat dateformat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");




    public DatabaseActivity(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    public static DatabaseActivity instanceOfDatabase(Context context){
        if(databaseActivity == null)
            databaseActivity = new DatabaseActivity(context);
        return databaseActivity;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       StringBuilder sql;
       sql = new StringBuilder()
               .append(" Create Table ")
               .append(TABLE_NAME)
               .append("(")
               .append(COUNTER)
               .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
               .append(db_id)
               .append(" INT, ")
               .append(db_prodName)
               .append(" TEXT, ")
               .append(db_prodDesc)
               .append(" TEXT, ")
               .append(db_prodPrice)
               .append(" TEXT, ")
               .append(db_deleted)
               .append(" TEXT) ");

       sqLiteDatabase.execSQL(sql.toString());

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//    switch (oldVersion)
//    {
//        case 1 :
//            sqLiteDatabase.execSQL("ALTER TABLE "  + TABLE_NAME  + " ADD COLUMN " + NEW_COLUMN + "TEXT");
//        case 2 :
//            sqLiteDatabase.execSQL("ALTER TABLE "  + TABLE_NAME  + " ADD COLUMN " + NEW_COLUMN + "TEXT");
//    }
    }

    public void addDataToDatabase(Product product){

     SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(db_id,product.getProdId());
        contentValues.put(db_prodName,product.getProdName());
        contentValues.put(db_prodDesc,product.getProdDesc());
        contentValues.put(db_prodPrice,product.getProdPrice());
        contentValues.put(db_deleted,getStringFromDate(product.getDeleted()));

        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
    }

    public void populateDataListArray()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
    try(Cursor result = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_NAME , null)) {


        if (result.getCount() != 0){
            while (result.moveToNext()){
                int id = result.getInt(1);
                String name = result.getString(2);
                String description = result.getString(3);
                String price = result.getString(4);
                String stringDeleted = result.getString(5);
                Date deleted = getDateFromString(stringDeleted);
                Product product = new Product(id,name,description,price,deleted);
                Product.productArrayList.add(product);
            }

        }
    }
    }
    public void updateProductInDB(Product product){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(db_id,product.getProdId());
        contentValues.put(db_prodName,product.getProdName());
        contentValues.put(db_prodDesc,product.getProdDesc());
        contentValues.put(db_prodPrice,product.getProdPrice());
        contentValues.put(db_deleted,getStringFromDate(product.getDeleted()));

        sqLiteDatabase.update(TABLE_NAME,contentValues,db_id + " = ? " , new String[]{String.valueOf(product.getProdId())});
    }
    private String getStringFromDate(Date date) {
        if(date == null)
            return null;
        return dateformat.format(date);
    }

    private Date getDateFromString(String string){

        try {
            return dateformat.parse(string);
        }catch (ParseException | NullPointerException e){
            return null;
        }

    }

}
