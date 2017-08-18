package com.hengstar.prework_repo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hengstar.prework_repo.R;
import com.hengstar.prework_repo.models.ListItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListItemAdapter extends ArrayAdapter<ListItem> {
    public ListItemAdapter(Context context, ArrayList<ListItem> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ListItem item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView tvItemValue = convertView.findViewById(R.id.tvItemValue);
        tvItemValue.setText(item.title);
        TextView tvDueDate = convertView.findViewById(R.id.tvDueDate);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        tvDueDate.setText(sdf.format(item.date));

        return convertView;
    }
}
