package com.junglepath.app.detail.di;

import com.junglepath.app.detail.DetailPresenter;
import com.junglepath.app.detail.DetailPresenterImpl;
import com.junglepath.app.detail.ui.DetailView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DetailModule {
    private DetailView view;

    public DetailModule(DetailView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    DetailView providesDetailView() {
        return this.view;
    }

    @Provides
    @Singleton
    DetailPresenter providesDetailPresenter(DetailView view){
        return new DetailPresenterImpl(view);
    }
}
