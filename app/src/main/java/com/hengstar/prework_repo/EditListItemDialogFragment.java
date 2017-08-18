package com.hengstar.prework_repo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hengstar.prework_repo.models.ListItem;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditListItemDialogFragment extends DialogFragment implements Button.OnClickListener {
    // Public for sharing
    public static String PARAM_KEY_TITLE = "TITLE";
    public static String PARAM_KEY_EDIT_INDEX = "EDIT_INDEX";
    public static String PARAM_KEY_EDIT_ITEM = "EDIT_ITEM";

    private static Map<Integer, ListItem.Priority> RESID_PRIORITY_MAP = new HashMap() {
        {
            put(R.id.rbPriorityHigh, ListItem.Priority.HIGH);
            put(R.id.rbPriorityMedium, ListItem.Priority.MEDIUM);
            put(R.id.rbPriorityLow, ListItem.Priority.LOW);
        }
    };

    private static Map<ListItem.Priority, Integer> PRIORITY_RESID_MAP = new HashMap() {
        {
            put(ListItem.Priority.HIGH, R.id.rbPriorityHigh);
            put(ListItem.Priority.MEDIUM, R.id.rbPriorityMedium);
            put(ListItem.Priority.LOW, R.id.rbPriorityLow);
        }
    };

    private Button mBtnSave;
    private EditText mEtEditItem;
    private ListItem mOriginalItem;
    private int mItemIndex;
    private DatePicker dpDueDate;
    private RadioGroup rgPriority;
    private TextView mTvEditItemHint;

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
        mBtnSave = view.findViewById(R.id.btnSave);
        // Setup a callback when the save button is clicked
        mBtnSave.setOnClickListener(this);
        // Get field from view
        mEtEditItem = view.findViewById(R.id.etEditItem);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString(PARAM_KEY_TITLE, getString(R.string.edit_item));
        getDialog().setTitle(title);
        mTvEditItemHint = view.findViewById(R.id.tvEditItemHint);
        mOriginalItem = (ListItem) getArguments().getSerializable(PARAM_KEY_EDIT_ITEM);
        // Adding a new item
        if (mOriginalItem == null) {
            mOriginalItem = ListItem.DEFAULT_ITEM;
            mTvEditItemHint.setText(getString(R.string.add_item));
        } else {
            // Editing an item
            mTvEditItemHint.setText(getString(R.string.edit_item));
        }
        mItemIndex = getArguments().getInt(PARAM_KEY_EDIT_INDEX);
        mEtEditItem.setText(mOriginalItem.title);
        // Show soft keyboard automatically and request focus to field
        /*
        mEtEditItem.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);*/

        dpDueDate = view.findViewById(R.id.dpDueDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mOriginalItem.date);
        dpDueDate.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        rgPriority = view.findViewById(R.id.rgPriority);
        rgPriority.check(PRIORITY_RESID_MAP.get(mOriginalItem.priority));
    }

    @Override
    public void onClick(View view) {
        // Return input text back to activity through the implemented listener
        EditListItemDialogListener listener = (EditListItemDialogListener) getActivity();
        Calendar calendar = Calendar.getInstance();
        calendar.set(dpDueDate.getYear(), dpDueDate.getMonth(), dpDueDate.getDayOfMonth(), 0, 0, 0);
        Date date = calendar.getTime();
        listener.onFinishEditDialog(mItemIndex, new ListItem(mOriginalItem.id,
                                                             mEtEditItem.getText().toString(),
                                                             date,
                                                             RESID_PRIORITY_MAP.get(rgPriority.getCheckedRadioButtonId())));
        // Close the dialog and return back to the parent activity
        dismiss();
    }
}
