package com.hengstar.prework_repo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.hengstar.prework_repo.adapters.ListItemAdapter;
import com.hengstar.prework_repo.models.ListItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Public for EditItemActivity to use
    public static String PARAM_KEY_EDIT_INDEX = "EDIT_INDEX";
    public static String PARAM_KEY_EDIT_ITEM = "EDIT_ITEM";
    private final int REQUEST_CODE = 1;

    private ArrayList<ListItem> todoItems;
    private ListItemAdapter aToDoAdapter;
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
                // Delete it from SQLite DB
                SimpleEditorDatabaseHelper.getInstance(MainActivity.this).deleteItem(todoItems.get(i).id);
                todoItems.remove(i);
                aToDoAdapter.notifyDataSetChanged();

                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent iEdit = new Intent(MainActivity.this, EditItemActivity.class);
                // Pass current string and its index to be edited
                iEdit.putExtra(PARAM_KEY_EDIT_INDEX, i);
                iEdit.putExtra(PARAM_KEY_EDIT_ITEM, todoItems.get(i));
                startActivityForResult(iEdit, REQUEST_CODE);
            }
        });
    }

    public void populateArrayItems() {
        // Read stored items from SQLite DB
        todoItems = SimpleEditorDatabaseHelper.getInstance(this).getAllItems();
        aToDoAdapter = new ListItemAdapter(this, todoItems);
    }

    public void onAddItem(View view) {
        String value = etEditText.getText().toString();
        // Insert it into SQLite DB
        long id = SimpleEditorDatabaseHelper.getInstance(this).addItem(value);
        // TODO: if (id == -1) // something wrong
        aToDoAdapter.add(new ListItem(id, value));
        etEditText.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent iData) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            int index = iData.getIntExtra(PARAM_KEY_EDIT_INDEX, -1);
            // TODO: if (i == -1) // Something wrong
            ListItem item = (ListItem) iData.getSerializableExtra(PARAM_KEY_EDIT_ITEM);
            // Id doesn't change
            todoItems.set(index, item);
            aToDoAdapter.notifyDataSetChanged();
        }
    }
}
