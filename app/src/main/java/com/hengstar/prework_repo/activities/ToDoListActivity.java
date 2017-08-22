package com.hengstar.prework_repo.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hengstar.prework_repo.fragments.EditListItemDialogFragment;
import com.hengstar.prework_repo.R;
import com.hengstar.prework_repo.adapters.ListItemAdapter;
import com.hengstar.prework_repo.models.ListItem;
import com.hengstar.prework_repo.models.User;

import java.util.ArrayList;
import java.util.Collections;

public class ToDoListActivity extends AppCompatActivity implements EditListItemDialogFragment.EditListItemDialogListener {

    private ArrayList<ListItem> todoItems;
    private ListItemAdapter aToDoAdapter;
    private ListView lvItems;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Retrieve current user info
        currentUser = (User) getIntent().getSerializableExtra(LoginActivity.PARAM_KEY_USER_INFO);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Delete it from SQLite DB
                todoItems.get(i).delete();
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
        todoItems = new ArrayList<>();
        // Read all items from SQLite DB
        todoItems.addAll(currentUser.getAllItems());
        Collections.sort(todoItems);
        aToDoAdapter = new ListItemAdapter(this, todoItems);
    }

    public void onAddItem(View view) {
        showEditDialog(-1);
    }

    /**
     *
     * @param itemIndex Item index to be edited. -1 indicates new item.
     */
    private void showEditDialog(int itemIndex) {
        FragmentManager fm = getSupportFragmentManager();
        EditListItemDialogFragment editDialogFragment = itemIndex == -1
                ? EditListItemDialogFragment.newInstance(getString(R.string.add_item), itemIndex, null)
                : EditListItemDialogFragment.newInstance(getString(R.string.edit_item), itemIndex, todoItems.get(itemIndex));
        editDialogFragment.show(fm, "fragment_edit_item");
    }

    @Override
    public void onFinishEditDialog(int itemIndex, ListItem item) {
        if (itemIndex == -1) {
            todoItems.add(item);
        } else {
            todoItems.set(itemIndex, item);
        }
        Collections.sort(todoItems);
        aToDoAdapter.notifyDataSetChanged();
        // Update from SQLite DB
        item.userId = currentUser.userId;
        item.save();
    }
}
