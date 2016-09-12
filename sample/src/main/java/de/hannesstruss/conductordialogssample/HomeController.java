package de.hannesstruss.conductordialogssample;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;

public class HomeController extends Controller {
  @NonNull @Override
  protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
    View view = inflater.inflate(R.layout.controller_home, container, false);

    view.findViewById(R.id.btn_proceed).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        getRouter().pushController(RouterTransaction.builder(new MainController())
            .pushChangeHandler(new HorizontalChangeHandler())
            .popChangeHandler(new HorizontalChangeHandler())
            .build());
      }
    });

    return view;
  }
}
