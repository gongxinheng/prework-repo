package com.hengstar.prework_repo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.hengstar.prework_repo.models.ListItem;

public class EditListItemDialogFragment extends DialogFragment implements Button.OnClickListener {
    // Public for sharing
    public static String PARAM_KEY_TITLE = "TITLE";
    public static String PARAM_KEY_EDIT_INDEX = "EDIT_INDEX";
    public static String PARAM_KEY_EDIT_ITEM = "EDIT_ITEM";

    private Button mBtnSave;
    private EditText mEtEditItem;
    private ListItem mOriginalItem;
    private int mItemIndex;

    // Defines the listener interface with a method passing back data result.
    public interface EditListItemDialogListener {
        void onFinishEditDialog(int itemIndex, ListItem item);
    }

    public static EditListItemDialogFragment newInstance(String title, int itemIndex, ListItem item) {
        EditListItemDialogFragment frag = new EditListItemDialogFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_KEY_TITLE, title);
        // Pass current string and its index to be edited
        args.putInt(PARAM_KEY_EDIT_INDEX, itemIndex);
        args.putSerializable(PARAM_KEY_EDIT_ITEM, item);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_edit_item, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnSave = (Button) view.findViewById(R.id.btnSave);
        // Setup a callback when the save button is clicked
        mBtnSave.setOnClickListener(this);
        // Get field from view
        mEtEditItem = (EditText) view.findViewById(R.id.etEditItem);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString(PARAM_KEY_TITLE, getString(R.string.edit_item));
        getDialog().setTitle(title);
        mOriginalItem = (ListItem) getArguments().getSerializable(PARAM_KEY_EDIT_ITEM);
        mItemIndex = getArguments().getInt(PARAM_KEY_EDIT_INDEX);
        mEtEditItem.setText(mOriginalItem.value);
        // Show soft keyboard automatically and request focus to field
        mEtEditItem.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onClick(View view) {
        // Return input text back to activity through the implemented listener
        EditListItemDialogListener listener = (EditListItemDialogListener) getActivity();
        listener.onFinishEditDialog(mItemIndex, new ListItem(mOriginalItem.id, mEtEditItem.getText().toString()));
        // Close the dialog and return back to the parent activity
        dismiss();
    }
}
