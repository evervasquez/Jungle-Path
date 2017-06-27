package com.junglepath.app.Login;

/*
 *  Copyright (C) 6/25/17 eveR Vásquez.
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

import com.google.firebase.auth.AuthCredential;
import com.junglepath.app.Login.events.LoginEvent;
import com.junglepath.app.Login.ui.LoginView;
import com.junglepath.app.libs.base.EventBus;

import org.greenrobot.eventbus.Subscribe;

public class LoginPresenterImpl implements LoginPresenter {
    private static final String TAG = LoginPresenterImpl.class.getSimpleName();
    private LoginInteractor interactor;
    private EventBus eventBus;
    private LoginView view;

    public LoginPresenterImpl(LoginInteractor interactor, EventBus eventBus,
                              LoginView view) {
        this.interactor = interactor;
        this.eventBus = eventBus;
        this.view = view;
    }

    @Override
    public void onCreate() {
        view.initComponents();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStart() {
        eventBus.register(this);
    }

    @Override
    public void onStop() {
        eventBus.unregister(this);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Subscribe
    @Override
    public void handleSignIn(LoginEvent event) {

    }

    @Override
    public void login(AuthCredential credential) {
        //...
    }

    @Override
    public void checkForAuthenticatedUser() {

    }
}
