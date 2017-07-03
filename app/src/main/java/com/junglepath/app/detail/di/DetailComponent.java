package com.junglepath.app.detail.di;
/*
 *  Copyright (C) 7/3/17 eveR Vásquez.
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

import com.junglepath.app.detail.ui.DetailActivity;
import com.junglepath.app.libs.di.LibsModule;
import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {LibsModule.class, DetailModule.class})
public interface DetailComponent {
    void inject(DetailActivity activity);
}
