package com.junglepath.app.main.di;

import com.junglepath.app.domain.FirebaseDatabaseHelper;
import com.junglepath.app.libs.base.EventBus;
import com.junglepath.app.main.MainInteractor;
import com.junglepath.app.main.MainInteractorImpl;
import com.junglepath.app.main.MainPresenter;
import com.junglepath.app.main.MainPresenterImpl;
import com.junglepath.app.main.MainRepository;
import com.junglepath.app.main.MainRepositoryImpl;
import com.junglepath.app.main.ui.MainView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    private MainView view;

    public MainModule(MainView view) {
        this.view = view;
    }

    @Singleton
    @Provides
    MainView providesMainView() {
        return this.view;
    }

    @Singleton
    @Provides
    MainPresenter providesMainPresenter(MainView view, MainInteractor interactor, EventBus eventBus) {
        return new MainPresenterImpl(view, interactor, eventBus);
    }

    @Singleton
    @Provides
    MainInteractor providesMainInteractor(MainRepository repository) {
        return new MainInteractorImpl(repository);
    }

    @Singleton
    @Provides
    MainRepository providesMainRepository(EventBus eventBus, FirebaseDatabaseHelper helper) {
        return new MainRepositoryImpl(eventBus, helper);
    }

    @Singleton
    @Provides
    FirebaseDatabaseHelper getFirebaseDatabaseHelper() {
        return FirebaseDatabaseHelper.getInstance();
    }
}
