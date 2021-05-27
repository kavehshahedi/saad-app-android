package ir.khu.ie.publications.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.Objects;

import ir.khu.ie.publications.R;

public class LoadingDialog {

    private static Dialog dialog;

    public static void showLoadingDialog(Context context) {
        dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_loading);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        AVLoadingIndicatorView indicator = dialog.findViewById(R.id.loadingDialogIndicator);
        indicator.show();

        dialog.show();
    }

    public static void dismissLoadingDialog() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }
}
