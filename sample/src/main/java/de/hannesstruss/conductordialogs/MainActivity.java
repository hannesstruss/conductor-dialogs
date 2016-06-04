package de.hannesstruss.conductordialogs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;

public class MainActivity extends AppCompatActivity {

  private Router router;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    router = Conductor.attachRouter(this, (ViewGroup) findViewById(R.id.container),
        savedInstanceState);

    if (!router.hasRootController()) {
      router.pushController(RouterTransaction.builder(new HomeController()).build());
    }
  }

  @Override public void onBackPressed() {
    if (!router.handleBack()) {
      super.onBackPressed();
    }
  }
}
