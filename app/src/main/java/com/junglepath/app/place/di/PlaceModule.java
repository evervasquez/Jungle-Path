package com.junglepath.app.place.di;
/*
 *  Copyright (C) 7/2/17 eveR Vásquez.
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

import com.junglepath.app.db.entities.Place;
import com.junglepath.app.libs.base.ImageLoader;
import com.junglepath.app.place.OnItemClickListener;
import com.junglepath.app.place.PlacePresenter;
import com.junglepath.app.place.PlacePresenterImpl;
import com.junglepath.app.place.ui.PlaceView;
import com.junglepath.app.place.ui.adapters.PlaceAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class PlaceModule {
    private PlaceView view;
    private OnItemClickListener listener;

    public PlaceModule(PlaceView view, OnItemClickListener listener) {
        this.view = view;
        this.listener = listener;
    }

    @Singleton
    @Provides
    PlaceView providesPlaceView() {
        return this.view;
    }

    @Singleton
    @Provides
    PlaceAdapter providesPlaceAdapter(List<Place> places, ImageLoader imageLoader,
                                         OnItemClickListener listener) {
        return new PlaceAdapter(places, imageLoader, listener);
    }

    @Singleton
    @Provides
    List<Place> providesPlaceList() {
        return new ArrayList<Place>();
    }

    @Singleton
    @Provides
    OnItemClickListener providesOnItemClickListener() {
        return this.listener;
    }

    @Singleton
    @Provides
    PlacePresenter providesPlacePresenter(PlaceView view) {
        return new PlacePresenterImpl(view);
    }
}
