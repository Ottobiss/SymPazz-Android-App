package com.cof.sympuzz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DiseaseDataActivity extends AppCompatActivity {

    ListView userList;

    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_data);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.create_db();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        db = databaseHelper.open();
//        userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE, null);
//        String[] headers = new String[]{DatabaseHelper.COLUMN_DESCRIPTION, DatabaseHelper.COLUMN_REASONS};
//        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
//                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2, android.R.id.text3}, 0);
//        userList.setAdapter(userAdapter);
//    }


}
