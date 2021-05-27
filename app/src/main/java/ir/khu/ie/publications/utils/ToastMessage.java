package ir.khu.ie.publications.utils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

import ir.khu.ie.publications.R;

public class ToastMessage {

    public static void showCustomToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        View view = toast.getView();
        TextView text = view.findViewById(android.R.id.message);
        text.setTypeface(ResourcesCompat.getFont(context, R.font.shabnam));
        text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        text.setTextSize(12);
        toast.show();
    }

    public static void showCustomToast(Context context, String message, boolean isLong) {
        Toast toast = Toast.makeText(context, message, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        View view = toast.getView();
        TextView text = view.findViewById(android.R.id.message);
        text.setTypeface(ResourcesCompat.getFont(context, R.font.shabnam));
        text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        text.setTextSize(12);
        toast.show();
    }

}
