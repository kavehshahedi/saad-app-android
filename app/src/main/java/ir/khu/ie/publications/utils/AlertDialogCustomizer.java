package ir.khu.ie.publications.utils;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;

import java.util.Objects;

import ir.khu.ie.publications.R;

public class AlertDialogCustomizer {

    public static void customizeSingleButtonTexts(Context context, AlertDialog dialog, int color) {
        TextView textView = Objects.requireNonNull(dialog.getWindow()).findViewById(android.R.id.message);
        TextView alertTitle = dialog.getWindow().findViewById(R.id.alertTitle);
        Button button1 = dialog.getWindow().findViewById(android.R.id.button1);

        textView.setTypeface(ResourcesCompat.getFont(context, R.font.shabnam_light));
        alertTitle.setTypeface(ResourcesCompat.getFont(context, R.font.shabnam));
        button1.setTypeface(ResourcesCompat.getFont(context, R.font.shabnam));

        alertTitle.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        alertTitle.setTextSize(16);

        textView.setTextSize(15);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

        button1.setTextColor(context.getResources().getColor(color));
    }

    public static void customizeDoubleButtonTexts(Context context, AlertDialog dialog, int color1, int color2) {
        TextView textView = Objects.requireNonNull(dialog.getWindow()).findViewById(android.R.id.message);
        TextView alertTitle = dialog.getWindow().findViewById(R.id.alertTitle);
        Button button1 = dialog.getWindow().findViewById(android.R.id.button1);
        Button button2 = dialog.getWindow().findViewById(android.R.id.button2);

        textView.setTypeface(ResourcesCompat.getFont(context, R.font.shabnam_light));
        alertTitle.setTypeface(ResourcesCompat.getFont(context, R.font.shabnam));
        button1.setTypeface(ResourcesCompat.getFont(context, R.font.shabnam));
        button2.setTypeface(ResourcesCompat.getFont(context, R.font.shabnam));

        alertTitle.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        alertTitle.setTextSize(16);

        textView.setTextSize(15);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

        button1.setTextColor(context.getResources().getColor(color1));
        button1.setTextColor(context.getResources().getColor(color2));
    }

}
