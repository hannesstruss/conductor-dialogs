package de.hannesstruss.conductordialogs;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class App extends Application {
  public RefWatcher refWatcher;

  @Override public void onCreate() {
    super.onCreate();

    refWatcher = LeakCanary.install(this);
  }
}
