package org.samples.blassioli.reddit;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        this.initializeLeakDetection();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(getApplicationModule())
                .build();
    }

    public ApplicationModule getApplicationModule() {
        return new ApplicationModule(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    private void initializeLeakDetection() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
    }
}