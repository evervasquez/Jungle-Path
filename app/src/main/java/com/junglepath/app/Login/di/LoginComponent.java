package com.junglepath.app.Login.di;

import com.junglepath.app.Login.ui.LoginActivity;
import com.junglepath.app.libs.di.LibsModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {LibsModule.class, LoginModule.class})
public interface LoginComponent {
    void inject(LoginActivity activity);
}
