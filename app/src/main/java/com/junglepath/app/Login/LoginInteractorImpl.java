package com.junglepath.app.Login;

import com.google.firebase.auth.AuthCredential;

public class LoginInteractorImpl implements LoginInteractor {
    private LoginRepository repository;

    public LoginInteractorImpl(LoginRepository repository) {
        this.repository = repository;
    }

    @Override
    public void checkSession() {

    }

    @Override
    public void doSignIn(AuthCredential credential) {

    }
}
