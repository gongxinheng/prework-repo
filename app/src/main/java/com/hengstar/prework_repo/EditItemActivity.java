package com.hengstar.prework_repo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.hengstar.prework_repo.models.ListItem;

public class EditItemActivity extends AppCompatActivity {
    private EditText etEditItem;
    private int index; // To store index of item being edited
    private ListItem item; // Store the original list item

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        etEditItem = (EditText) findViewById(R.id.etEditItem);
        item = (ListItem) getIntent().getSerializableExtra(MainActivity.PARAM_KEY_EDIT_ITEM);
        // Initialize text with original text
        etEditItem.setText(item.value);
        index = getIntent().getIntExtra(MainActivity.PARAM_KEY_EDIT_INDEX, -1);
        // @TODO
        // if (index == -1) // Something wrong
    }

    public void onSave(View view) {
        Intent iData = new Intent();
        item.value = etEditItem.getText().toString();
        iData.putExtra(MainActivity.PARAM_KEY_EDIT_INDEX, index);
        iData.putExtra(MainActivity.PARAM_KEY_EDIT_ITEM, item);
        // Update from SQLite DB
        SimpleEditorDatabaseHelper.getInstance(this).updateItem(item);
        setResult(RESULT_OK, iData);
        finish();
    }
}
