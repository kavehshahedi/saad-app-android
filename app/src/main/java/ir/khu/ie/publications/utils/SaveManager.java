package ir.khu.ie.publications.utils;

import android.content.Context;

import static android.content.Context.MODE_PRIVATE;

public class SaveManager {

    public static String loadPhone (Context context) {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("phone", "-1");
    }

    public static void setPhone (Context context, String phone) {
        context.getSharedPreferences("_", MODE_PRIVATE).edit().putString("phone", phone).apply();
    }

    public static String loadAccessToken (Context context) {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("access_token", "-1");
    }

    public static void setAccessToken (Context context, String accessToken) {
        context.getSharedPreferences("_", MODE_PRIVATE).edit().putString("access_token", accessToken).apply();
    }

}
