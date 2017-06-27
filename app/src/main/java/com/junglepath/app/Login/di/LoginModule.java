package com.junglepath.app.Login.di;

import com.google.firebase.auth.FirebaseAuth;
import com.junglepath.app.Login.LoginInteractor;
import com.junglepath.app.Login.LoginInteractorImpl;
import com.junglepath.app.Login.LoginPresenter;
import com.junglepath.app.Login.LoginPresenterImpl;
import com.junglepath.app.Login.LoginRepository;
import com.junglepath.app.Login.LoginRepositoryImpl;
import com.junglepath.app.Login.ui.LoginView;
import com.junglepath.app.libs.base.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {
    private LoginView view;

    public LoginModule(LoginView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    LoginView providesLoginView() {
        return this.view;
    }

    @Provides
    @Singleton
    LoginPresenter providesLoginPresenter(LoginInteractor interactor, EventBus eventBus,
                                          LoginView view) {
        return new LoginPresenterImpl(interactor, eventBus, view);
    }

    @Provides
    @Singleton
    LoginInteractor providesLoginInteractor(LoginRepository repository) {
        return new LoginInteractorImpl(repository);
    }

    @Provides
    @Singleton
    LoginRepository providesLoginRepository(EventBus eventBus) {
        return new LoginRepositoryImpl(eventBus);
    }

    @Provides
    @Singleton
    FirebaseAuth providesFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }
}
