package com.hengstar.prework_repo.models;

import android.support.annotation.NonNull;

import com.hengstar.prework_repo.ToDoDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * ListItem model - make it class to be extensible
 * Serializable for intent to transmit
 */
@Table(database = ToDoDatabase.class)
public class ListItem extends BaseModel implements Serializable, Comparable<ListItem> {
    @Override
    public int compareTo(@NonNull ListItem item) {
        long dif = this.dueDate.getTime() - item.dueDate.getTime();
        if (dif == 0) {
            return 0;
        } else if (dif > 0) {
            return 1;
        } else {
            return -1;
        }
    }

    public enum Priority {
        HIGH, MEDIUM, LOW
    }

    // Default list item used in adding new item
    public static final ListItem DEFAULT_ITEM = new ListItem(-1, "", new Date(), ListItem.Priority.MEDIUM);

    @Column
    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public String title;

    @Column
    public Date dueDate;

    @Column
    public Priority priority;

    public ListItem(){

    }

    public ListItem(long id, String title, Date date, Priority priority) {
        super();
        this.id = id;
        this.title = title;
        this.dueDate = date;
        this.priority = priority;
    }

    // Used for list view to display
    @Override
    public String toString() {
        return title;
    }
}
