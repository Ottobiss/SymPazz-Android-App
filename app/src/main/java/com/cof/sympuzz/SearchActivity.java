package com.cof.sympuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

public class SearchActivity extends AppCompatActivity {

    ListView userList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        userList = findViewById(R.id.list);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.create_db();

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object listItem = userList.getItemAtPosition(position);
                openDiseaseDataActivity(view);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        db = databaseHelper.open();
        userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE, null);
        String[] headers = new String[]{DatabaseHelper.COLUMN_NAME};
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1}, 0);
        userList.setAdapter(userAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
        userCursor.close();
    }

    private void openDiseaseDataActivity(View view) {
        Intent intent = new Intent(this, DiseaseDataActivity.class);
        startActivity(intent);
    }



}
