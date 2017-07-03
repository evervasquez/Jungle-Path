package com.junglepath.app.main;
import com.junglepath.app.db.entities.Category;
import com.junglepath.app.libs.base.EventBus;
import com.junglepath.app.main.events.MainEvent;
import com.junglepath.app.main.ui.MainView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class MainPresenterImpl implements MainPresenter {
    private static final String TAG = MainPresenterImpl.class.getSimpleName();
    private MainView view;
    private MainInteractor interactor;
    private EventBus eventBus;

    public MainPresenterImpl(MainView view, MainInteractor interactor, EventBus eventBus) {
        this.view = view;
        this.interactor = interactor;
        this.eventBus = eventBus;
    }

    @Override
    public void onCreate() {
        view.initComponents();
    }

    @Override
    public void onResume() {
        interactor.getListPlaces();
    }

    @Override
    public void onStart() {
        eventBus.register(this);
    }

    @Override
    public void onStop() {
        eventBus.unregister(this);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Subscribe
    @Override
    public void handleSubscriber(MainEvent event) {
        switch (event.getEventType()) {
            case MainEvent.LIST_SUCCESS:
                showListCategories(event.getCategories());
                break;
            case MainEvent.LIST_ERROR:
                view.showCategoriesErrors(event.getErrorMessage());
                break;
        }
    }

    public void showListCategories(ArrayList<Category> categories) {
        view.hideElements();
        view.showProgress();
        view.showCategories(categories);
        view.hideProgress();
        view.showElements();
    }
}
