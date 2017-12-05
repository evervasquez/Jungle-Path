package com.junglepath.app.main;

import org.json.JSONObject;

public class MainInteractorImpl implements MainInteractor{
    private MainRepository repository;

    public MainInteractorImpl(MainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void getListPlaces(JSONObject obj) {
        this.repository.getListPlaces(obj);
    }
}
