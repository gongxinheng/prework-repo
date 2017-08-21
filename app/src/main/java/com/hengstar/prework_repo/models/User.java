package com.hengstar.prework_repo.models;

import com.hengstar.prework_repo.ToDoDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(database = ToDoDatabase.class)
public class User extends BaseModel implements Serializable {
    @Column
    @PrimaryKey(autoincrement = true)
    public long userId;

    @Column
    @Unique
    public String userName;

    @Column
    public String password;

    @Column
    public Date registerDate;

    List<ListItem> items;

    public User() {

    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        registerDate = new Date();
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "items")
    public List<ListItem> getAllItems() {
        if (items == null || items.isEmpty()) {
            items = SQLite.select()
                    .from(ListItem.class)
                    .where(ListItem_Table.userId_id.eq(userId))
                    .queryList();
        }
        return items;
    }
}
