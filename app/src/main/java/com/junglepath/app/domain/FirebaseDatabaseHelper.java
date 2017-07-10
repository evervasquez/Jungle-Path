
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
