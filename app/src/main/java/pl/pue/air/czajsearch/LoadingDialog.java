package pl.pue.air.czajsearch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

public class LoadingDialog {

    private View view;
    private Context context;
    private Activity activity;
    private AlertDialog dialog;

    // to activity view
    LoadingDialog(Activity activity){
        this.activity = activity;
    }

    // to fragment view
    LoadingDialog(View v) {
        this.view = v;
    }

    void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));

        dialog = builder.create();
        dialog.show();
    }

    void startLoadingDialogContext(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        View view = LayoutInflater.from(v.getContext()).inflate(
                R.layout.loading_dialog, null
        );
        builder.setView(view);

        dialog = builder.create();
        dialog.show();
    }


    void dismissDialog() {
        dialog.dismiss();
    }

}
