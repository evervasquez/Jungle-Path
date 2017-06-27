package com.junglepath.app.Login;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.junglepath.app.domain.FirebaseDatabaseHelper;
import com.junglepath.app.libs.base.EventBus;


public class LoginRepositoryImpl implements LoginRepository {
    private EventBus eventBus;
    private FirebaseDatabaseHelper helper;
    private FirebaseAuth mAuth;

    public LoginRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void signIn(AuthCredential credential) {

    }

    @Override
    public void checkSession() {

    }
}
