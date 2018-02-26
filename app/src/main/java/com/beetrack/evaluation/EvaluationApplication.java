package com.beetrack.evaluation;

import android.app.Application;
import android.provider.Settings;

import java.security.SecureRandom;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by mbot on 2/23/18.
 */

public class EvaluationApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        initRealm();
    }

    private void initRealm() {
        Realm.init(getApplicationContext());
        realmBuilder();
    }

    private void realmBuilder() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("beetrack.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build();

        Realm.setDefaultConfiguration(config);
    }
}
