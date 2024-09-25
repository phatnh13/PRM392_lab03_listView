package com.example.lab_03_listview;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Activity1 extends AppCompatActivity {
    ListView listView;
    ArrayList<String> list;
    EditText editText;
    Button btnAdd;
    Button btnEdit;
    Button btnRemove;
    int itemClickPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity1);

        listView = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.textView);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnEdit = (Button) findViewById(R.id.btnUpdate);
        btnRemove = (Button) findViewById(R.id.btnDelete);
        list = new ArrayList<>();
        list.add("Android");
        list.add("PHP");
        list.add("IOS");
        list.add("Unity");
        list.add("ASP.NET");
        list.add("Java");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                Activity1.this,
                android.R.layout.simple_list_item_1,
                list
        );

        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String item = list.get(position);
            itemClickPosition = position;
            editText.setText(item);
            Toast.makeText(Activity1.this, item, Toast.LENGTH_SHORT).show();
        });

//        listView.setOnItemLongClickListener((parent, view, position, id) -> {
//            list.remove(position);
//            adapter.notifyDataSetChanged();
//            return true;
//        });

        btnAdd.setOnClickListener(v -> {
            String item = editText.getText().toString();
            list.add(item);
            itemClickPosition = list.size() - 1;
            adapter.notifyDataSetChanged();
        });

        btnEdit.setOnClickListener(v -> {
            String item = editText.getText().toString();
            list.set(itemClickPosition, item);
            adapter.notifyDataSetChanged();
        });

        btnRemove.setOnClickListener(v -> {
            list.remove(itemClickPosition);
            editText.setText("");
            adapter.notifyDataSetChanged();
        });
    }
}
