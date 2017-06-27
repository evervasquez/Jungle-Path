package com.junglepath.app.main;

import com.junglepath.app.main.events.MainEvent;

public interface MainPresenter {

    void onCreate();

    void onResume();

    void onStart();

    void onStop();

    void onDestroy();

    void handleSubscriber(MainEvent event);
}
