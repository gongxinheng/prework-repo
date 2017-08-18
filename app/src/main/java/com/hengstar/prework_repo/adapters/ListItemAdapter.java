package com.hengstar.prework_repo.adapters;

import android.content.Context;
import android.content.res.Resources;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ListItemAdapter extends ArrayAdapter<ListItem> {

    private static Map<ListItem.Priority, Integer> PRIORITY_COLOR_MAP = new HashMap() {
        {
            put(ListItem.Priority.HIGH, 0xffd62f20);
            put(ListItem.Priority.MEDIUM, 0xfff4b53f);
            put(ListItem.Priority.LOW, 0xff0fa83d);
        }
    };

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
        Resources res = convertView.getResources();
        TextView tvItemValue = convertView.findViewById(R.id.tvItemValue);
        tvItemValue.setText(item.title);
        TextView tvDueDate = convertView.findViewById(R.id.tvDueDate);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        tvDueDate.setText(res.getString(R.string.due_date, sdf.format(item.date)));
        // Calculate day difference between now and due date
        long dueDays = TimeUnit.DAYS.convert(item.date.getTime() - new Date().getTime(), TimeUnit.MILLISECONDS);
        if (dueDays < 5) {
            tvDueDate.setTextColor(PRIORITY_COLOR_MAP.get(ListItem.Priority.HIGH));
        } else if (dueDays < 30) {
            tvDueDate.setTextColor(PRIORITY_COLOR_MAP.get(ListItem.Priority.MEDIUM));
        } else {
            tvDueDate.setTextColor(PRIORITY_COLOR_MAP.get(ListItem.Priority.LOW));
        }
        TextView tvPriority = convertView.findViewById(R.id.tvPriority);
        tvPriority.setText(res.getString(R.string.priority, item.priority.toString()));
        tvPriority.setTextColor(PRIORITY_COLOR_MAP.get(item.priority));

        return convertView;
    }
}
