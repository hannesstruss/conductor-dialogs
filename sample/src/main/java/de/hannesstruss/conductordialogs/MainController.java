package de.hannesstruss.conductordialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bluelinelabs.conductor.Controller;

public class MainController extends Controller {
  public static final String PROGRESS_DIALOG_TAG = "MyProgressDialog";
  private DialogManager dialogManager;

  @NonNull @Override
  protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
    if (dialogManager == null) {
      dialogManager = new DialogManager(this);
    }

    View view = inflater.inflate(R.layout.controller_main, container, false);

    view.findViewById(R.id.btn_show_dialog).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        showDialog();
      }
    });

    view.findViewById(R.id.btn_long_operation).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        doLongOperation();
      }
    });

    return view;
  }

  private void showDialog() {
    dialogManager.showDialog(new DialogManager.DialogFactory() {
      @Override public Dialog createDialog(Context context) {
        return new AlertDialog.Builder(context)
            .setMessage("This is a pretty nice dialog.")
            .setPositiveButton("Cool!", new DialogInterface.OnClickListener() {
              @Override public void onClick(DialogInterface dialog, int which) {
                cool();
              }
            })
            .setCancelable(false)
            .show();
      }
    });
  }

  private void doLongOperation() {
    dialogManager.showDialog(PROGRESS_DIALOG_TAG, new DialogManager.DialogFactory() {
      @Override public Dialog createDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("This shouldn't take too long...");
        return dialog;
      }
    });

    getView().postDelayed(new Runnable() {
      @Override public void run() {
        Dialog dialog = dialogManager.findDialog(PROGRESS_DIALOG_TAG);
        if (dialog != null) {
          dialog.dismiss();
        }
      }
    }, 5000);
  }

  @Override protected void onDestroyView(View view) {
    super.onDestroyView(view);
  }

  @Override protected void onDestroy() {
    ((App) getActivity().getApplicationContext()).refWatcher.watch(this);

    super.onDestroy();
  }

  private void cool() {
    Toast.makeText(getActivity(), "Cool!", Toast.LENGTH_LONG).show();
  }
}
