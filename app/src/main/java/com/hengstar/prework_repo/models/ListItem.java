package com.hengstar.prework_repo.models;

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
public class ListItem extends BaseModel implements Serializable {
    @Column
    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public String title;

    @Column
    public Date date;

    public ListItem(){

    }

    public ListItem(long id, String title, Date date) {
        super();
        this.id = id;
        this.title = title;
        this.date = date;
    }

    // Used for list view to display
    @Override
    public String toString() {
        return title;
    }
}
