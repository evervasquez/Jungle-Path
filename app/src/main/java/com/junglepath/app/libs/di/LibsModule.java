/*
 *  Copyright (C) 2016 eveR Vásquez.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package com.junglepath.app.libs.di;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.junglepath.app.libs.GlideImageLoader;
import com.junglepath.app.libs.GreenRobotEventBus;
import com.junglepath.app.libs.Utils;
import com.junglepath.app.libs.base.EventBus;
import com.junglepath.app.libs.base.ImageLoader;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LibsModule {

    private Fragment fragment;
    private AppCompatActivity activity;

    public LibsModule(Fragment fragment, AppCompatActivity activity) {
        this.fragment = fragment;
        this.activity = activity;
    }

    @Provides
    @Singleton
    Utils providesUtils(){
        return new Utils();
    }

    @Provides
    @Singleton
    File providesFile() {
        return new File("");
    }

    @Provides
    @Singleton
    EventBus provideEventBus(org.greenrobot.eventbus.EventBus eventBus) {
        return new GreenRobotEventBus(eventBus);
    }

    @Provides
    @Singleton
    org.greenrobot.eventbus.EventBus provideLibraryEventBus() {
        return org.greenrobot.eventbus.EventBus.getDefault();
    }

    @Provides
    @Singleton
    ImageLoader provideImageLoader(RequestManager requestManager) {
        return new GlideImageLoader(requestManager);
    }

    @Provides
    @Singleton
    RequestManager provideRequestManager() {
        if (fragment != null) {
            return Glide.with(fragment);
        } else return Glide.with(activity);
    }


    @Provides
    @Singleton
    AppCompatActivity provideActivity() {
        return this.activity;
    }

    @Provides
    @Singleton
    Fragment provideFragment() {
        return this.fragment;
    }
}
