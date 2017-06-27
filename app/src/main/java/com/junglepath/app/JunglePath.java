package com.junglepath.app;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.junglepath.app.Login.di.DaggerLoginComponent;
import com.junglepath.app.Login.di.LoginComponent;
import com.junglepath.app.Login.di.LoginModule;
import com.junglepath.app.Login.ui.LoginView;
import com.junglepath.app.libs.di.LibsModule;
import com.junglepath.app.main.OnItemClickListener;
import com.junglepath.app.main.di.MainComponent;
import com.junglepath.app.main.di.MainModule;
import com.junglepath.app.main.ui.MainActivity;
import com.junglepath.app.main.ui.MainView;

public class JunglePath extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        initFacebook();
    }

    private void initFacebook() {
        FacebookSdk.sdkInitialize(this);
    }

    //di
    public LoginComponent getLoginComponent(LoginView view){
        return DaggerLoginComponent
                .builder()
                .libsModule(new LibsModule(null, null))
                .loginModule(new LoginModule(view))
                .build();
    }

    public MainComponent getMainComponent(MainView view, MainActivity activity, OnItemClickListener listener){
        return DaggerMainComponent
                .builder()
                .mainModule(new MainModule(view, listener))
                .libsModule(new LibsModule(null, activity))
                .build();
    }

}
