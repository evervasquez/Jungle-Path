package com.junglepath.app.main.ui;

import com.junglepath.app.db.entities.Place;

import java.util.List;

public interface MainView {

    void initComponents();

    void showProgress();

    void hideProgress();

    void showListPharmacies(List<Place> places);

    void showListPharmaciesErrors(String messageError);

    void hideElements();

    void showElements();

}
