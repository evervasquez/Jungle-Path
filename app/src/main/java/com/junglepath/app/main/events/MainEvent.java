package com.junglepath.app.main.events;

import com.junglepath.app.db.entities.Category;

import java.util.ArrayList;
import java.util.List;

public class MainEvent {
    public final static int LIST_SUCCESS = 1;
    public final static int LIST_ERROR = 0;
    private ArrayList<Category> categories;

    private int eventType;
    private String errorMessage;

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
