package de.hannesstruss.conductordialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.bluelinelabs.conductor.Controller;

import java.util.HashSet;
import java.util.Set;

public class DialogManager {
  private final Controller controller;
  private Set<Combo> combos;

  public DialogManager(Controller controller) {
    this.controller = controller;
    this.combos = new HashSet<>();

    controller.addLifecycleListener(new Controller.LifecycleListener() {
      @Override public void postCreateView(@NonNull Controller controller, @NonNull View view) {
        for (Combo combo : combos) {
          combo.dialog = combo.factory.createDialog(controller.getActivity());
          combo.dialog.show();
        }
      }

      @Override public void preDestroyView(@NonNull Controller controller, @NonNull View view) {
        Set<Combo> persistedCombos = new HashSet<>();

        for (Combo combo : combos) {
          if (combo.dialog.isShowing()) {
            combo.dialog.dismiss();
            combo.dialog = null;
            persistedCombos.add(combo);
          }
        }
        combos = persistedCombos;
      }

      @Override public void preDestroy(@NonNull Controller controller) {
        combos.clear();
      }
    });
  }

  public Dialog showDialog(DialogFactory factory) {
    return showDialog(null, factory);
  }

  public Dialog showDialog(String tag, DialogFactory factory) {
    Dialog dialog = factory.createDialog(controller.getActivity());
    dialog.show();

    combos.add(new Combo(dialog, tag, factory));

    return dialog;
  }

  public Dialog findDialog(String tag) {
    if (tag == null) {
      throw new NullPointerException("tag == null");
    }

    for (Combo combo : combos) {
      if (tag.equals(combo.tag) && combo.dialog != null && combo.dialog.isShowing()) {
        return combo.dialog;
      }
    }

    return null;
  }

  static class Combo {
    public Dialog dialog;
    public final String tag;
    public final DialogFactory factory;

    public Combo(Dialog dialog, String tag, DialogFactory factory) {
      this.dialog = dialog;
      this.tag = tag;
      this.factory = factory;
    }
  }

  public interface DialogFactory {
    Dialog createDialog(Context context);
  }
}
