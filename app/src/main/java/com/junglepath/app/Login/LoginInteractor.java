package com.junglepath.app.Login;


import com.google.firebase.auth.AuthCredential;

public interface LoginInteractor {
    void checkSession();
    void doSignIn(AuthCredential credential);
}
