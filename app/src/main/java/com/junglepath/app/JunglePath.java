package com.junglepath.app;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;
import com.junglepath.app.Login.di.DaggerLoginComponent;
import com.junglepath.app.Login.di.LoginComponent;
import com.junglepath.app.Login.di.LoginModule;
import com.junglepath.app.Login.ui.LoginView;
import com.junglepath.app.detail.di.DaggerDetailComponent;
import com.junglepath.app.detail.di.DetailComponent;
import com.junglepath.app.detail.di.DetailModule;
import com.junglepath.app.detail.ui.DetailActivity;
import com.junglepath.app.detail.ui.DetailView;
import com.junglepath.app.libs.di.LibsModule;
import com.junglepath.app.main.ui.OnItemSearchClickListener;
import com.junglepath.app.place.OnItemClickListener;
import com.junglepath.app.main.di.DaggerMainComponent;
import com.junglepath.app.main.di.MainComponent;
import com.junglepath.app.main.di.MainModule;
import com.junglepath.app.main.ui.MainActivity;
import com.junglepath.app.main.ui.MainView;
import com.junglepath.app.place.di.DaggerPlaceComponent;
import com.junglepath.app.place.di.PlaceComponent;
import com.junglepath.app.place.di.PlaceModule;
import com.junglepath.app.place.ui.PlaceFragment;
import com.junglepath.app.place.ui.PlaceView;

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

    public MainComponent getMainComponent(Context context, MainView view,
                                          MainActivity activity,
                                          OnItemSearchClickListener listener){
        return DaggerMainComponent
                .builder()
                .mainModule(new MainModule(context, view, listener))
                .libsModule(new LibsModule(null, activity))
                .build();
    }

    public PlaceComponent getPlaceComponent(PlaceView view, PlaceFragment fragment,
                                            OnItemClickListener listener){
        return DaggerPlaceComponent
                .builder()
                .placeModule(new PlaceModule(view, listener))
                .libsModule(new LibsModule(fragment, null))
                .build();
    }

    public DetailComponent getDetailComponent(DetailView view, DetailActivity activity){
        return DaggerDetailComponent
                .builder()
                .libsModule(new LibsModule(null, activity))
                .detailModule(new DetailModule(view))
                .build();
    }
}
