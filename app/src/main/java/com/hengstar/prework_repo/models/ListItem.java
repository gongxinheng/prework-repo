package com.hengstar.prework_repo.models;

import java.io.Serializable;

/**
 * ListItem model - make it class to be extensible
 * Serializable for intent to transmit
 */
public class ListItem implements Serializable {
    public long id;
    public String value;

    public ListItem(long id, String value) {
        this.id = id;
        this.value = value;
    }

    // Used for list view to display
    @Override
    public String toString() {
        return value;
    }
}
