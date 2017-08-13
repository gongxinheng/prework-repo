package com.hengstar.prework_repo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static String PARAM_KEY_EDIT_INDEX = "EDIT_INDEX";
    public static String PARAM_KEY_EDIT_TEXT = "EDIT_TEXT";
    private final int REQUEST_CODE = 1;

    private ArrayList<String> todoItems;
    private ArrayAdapter<String> aToDoAdapter;
    private ListView lvItems;
    private EditText etEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.editText);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                todoItems.remove(i);
                updateAndSave();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent iEdit = new Intent(MainActivity.this, EditItemActivity.class);
                // Pass current string and its index to be edited
                iEdit.putExtra(PARAM_KEY_EDIT_INDEX, i);
                iEdit.putExtra(PARAM_KEY_EDIT_TEXT, lvItems.getItemAtPosition(i).toString());
                startActivityForResult(iEdit, REQUEST_CODE);
            }
        });
    }

    public void populateArrayItems() {
        readItems();
        aToDoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todoItems);
    }

    public void readItems() {
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.txt");
        try {
            todoItems = new ArrayList<>(FileUtils.readLines(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeItems() {
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.txt");
        try {
            FileUtils.writeLines(file, todoItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAddItem(View view) {
        aToDoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        // TODO: Question: Is notifyDataSetChanged necessary?
        updateAndSave();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent iData) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            int i = iData.getIntExtra(PARAM_KEY_EDIT_INDEX, -1);
            // TODO: if (i == -1) // Something wrong
            String text = iData.getExtras().getString(PARAM_KEY_EDIT_TEXT);
            todoItems.set(i, text);
            updateAndSave();
        }
    }

    /**
     * Update changes and save it persistently
     */
    private void updateAndSave() {
        aToDoAdapter.notifyDataSetChanged();
        writeItems();
    }
}

