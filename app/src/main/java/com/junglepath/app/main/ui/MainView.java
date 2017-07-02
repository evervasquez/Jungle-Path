package com.junglepath.app.main.ui;

import com.junglepath.app.db.entities.Category;
import com.junglepath.app.db.entities.Place;

import java.util.List;

public interface MainView {

    void initComponents();

    void showProgress();

    void hideProgress();

    void showCategories(List<Category> categories);

    void showCategoriesErrors(String messageError);

    void hideElements();

    void showElements();

}
