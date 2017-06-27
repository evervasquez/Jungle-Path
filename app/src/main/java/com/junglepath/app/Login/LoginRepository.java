package com.junglepath.app.Login;


import com.google.firebase.auth.AuthCredential;

public interface LoginRepository {
    void signIn(AuthCredential credential);
    void checkSession();
}
