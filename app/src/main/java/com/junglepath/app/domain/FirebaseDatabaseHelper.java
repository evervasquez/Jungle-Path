/*
 *
 *  * Copyright (C) 2016 eveR Vásquez.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.junglepath.app.domain;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase dataReference;

    private final static String SEPARATOR = "___";
    public final static String FIREBASE_URL = "https://junglepath-b0a53.firebaseio.com/";
    private final static String PHARMACIES_PATH = "junglepath-b0a53";
//    private final static String USERS_PATH = "users";
//    private final static String MEDICINES_PATH = "medicines";

    private static class SingletonHolder{
        private static  final FirebaseDatabaseHelper INSTANCE = new FirebaseDatabaseHelper();
    }

    public static FirebaseDatabaseHelper getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public FirebaseDatabaseHelper() {
        this.dataReference = FirebaseDatabase.getInstance();
    }

    public FirebaseDatabase getDataReference() {
        return dataReference;
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
