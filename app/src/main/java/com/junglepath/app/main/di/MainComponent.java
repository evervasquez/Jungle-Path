package com.junglepath.app.main.di;

import com.junglepath.app.libs.di.LibsModule;
import com.junglepath.app.main.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MainModule.class, LibsModule.class})
public interface MainComponent {
    void inject(MainActivity activity);
}
