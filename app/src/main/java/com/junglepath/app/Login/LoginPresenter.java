package com.junglepath.app.Login;

import com.google.firebase.auth.AuthCredential;
import com.junglepath.app.Login.events.LoginEvent;

public interface LoginPresenter {
    void onCreate();

    void onResume();

    void onStart();

    void onStop();

    void onDestroy();

    void login(AuthCredential credential);

    void checkForAuthenticatedUser();

    void handleSignIn(LoginEvent event);
}
