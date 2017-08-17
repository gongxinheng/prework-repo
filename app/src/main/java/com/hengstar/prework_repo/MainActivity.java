package com.hengstar.prework_repo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.hengstar.prework_repo.adapters.ListItemAdapter;
import com.hengstar.prework_repo.models.ListItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EditListItemDialogFragment.EditListItemDialogListener {

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
                showEditDialog(i);
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

    /**
     *
     * @param itemIndex Item index to be edited
     */
    private void showEditDialog(int itemIndex) {
        FragmentManager fm = getSupportFragmentManager();
        EditListItemDialogFragment editDialogFragment = EditListItemDialogFragment.newInstance(
                getString(R.string.edit_item), itemIndex, todoItems.get(itemIndex));
        editDialogFragment.show(fm, "fragment_edit_item");
    }

    @Override
    public void onFinishEditDialog(int itemIndex, ListItem item) {
        todoItems.set(itemIndex, item);
        aToDoAdapter.notifyDataSetChanged();
        // Update from SQLite DB
        SimpleEditorDatabaseHelper.getInstance(this).updateItem(item);
    }
}
