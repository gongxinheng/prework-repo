package com.hengstar.prework_repo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    private EditText etEditItem;
    private int i; // To store index of item being edited

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        etEditItem = (EditText) findViewById(R.id.etEditItem);
        // Initialize text with original text
        etEditItem.setText(getIntent().getStringExtra(MainActivity.PARAM_KEY_EDIT_TEXT));
        i = getIntent().getIntExtra(MainActivity.PARAM_KEY_EDIT_INDEX, -1);
        // @TODO
        // if (i == -1) // Something wrong
    }

    public void onSave(View view) {
        Intent iData = new Intent();
        iData.putExtra(MainActivity.PARAM_KEY_EDIT_INDEX, i);
        iData.putExtra(MainActivity.PARAM_KEY_EDIT_TEXT, etEditItem.getText().toString());
        setResult(RESULT_OK, iData);
        finish();
    }
}
