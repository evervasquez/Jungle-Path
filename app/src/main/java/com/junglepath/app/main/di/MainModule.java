package com.junglepath.app.main.di;

import android.content.Context;

import com.junglepath.app.db.entities.Place;
import com.junglepath.app.domain.FirebaseDatabaseHelper;
import com.junglepath.app.libs.base.EventBus;
import com.junglepath.app.libs.base.ImageLoader;
import com.junglepath.app.main.MainInteractor;
import com.junglepath.app.main.MainInteractorImpl;
import com.junglepath.app.main.MainPresenter;
import com.junglepath.app.main.MainPresenterImpl;
import com.junglepath.app.main.MainRepository;
import com.junglepath.app.main.MainRepositoryImpl;
import com.junglepath.app.main.ui.MainView;
import com.junglepath.app.main.ui.OnItemSearchClickListener;
import com.junglepath.app.main.ui.adapters.SearchableAdapter;
import com.junglepath.app.place.OnItemClickListener;
import com.junglepath.app.place.ui.adapters.PlaceAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    private MainView view;
    private Context context;
    private OnItemSearchClickListener listener;

    public MainModule(Context context, MainView view, OnItemSearchClickListener listener) {
        this.view = view;
        this.context = context;
        this.listener = listener;
    }

    @Singleton
    @Provides
    MainView providesMainView() {
        return this.view;
    }

    @Singleton
    @Provides
    Context providesContext() {
        return this.context;
    }

    @Singleton
    @Provides
    OnItemSearchClickListener providesOnItemSearchClickListener() {
        return this.listener;
    }

    @Singleton
    @Provides
    SearchableAdapter providesSearchableAdapter(Context context, List<Place> places,
                                                ImageLoader imageLoader, OnItemSearchClickListener listener) {
        return new SearchableAdapter(context, places, imageLoader, listener);
    }

    @Singleton
    @Provides
    List<Place> providesPlaceList() {
        return new ArrayList<Place>();
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
