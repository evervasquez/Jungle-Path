package com.junglepath.app.main;

public class MainInteractorImpl implements MainInteractor{
    private MainRepository repository;

    public MainInteractorImpl(MainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void getListPlaces() {
        this.repository.getListPlaces();
    }
}
