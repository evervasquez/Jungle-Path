package com.junglepath.app.place;

import com.junglepath.app.place.ui.PlaceView;

public class PlacePresenterImpl implements PlacePresenter {
    private PlaceView view;

    public PlacePresenterImpl(PlaceView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        if (view != null) {

        }
    }

    @Override
    public void onResume() {

    }
}
