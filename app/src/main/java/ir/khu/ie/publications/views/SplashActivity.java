package ir.khu.ie.publications.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.wang.avi.AVLoadingIndicatorView;

import ir.khu.ie.publications.R;
import ir.khu.ie.publications.models.responses.auth.GetAccountResponse;
import ir.khu.ie.publications.services.NetworkClientService;
import ir.khu.ie.publications.services.api.AuthAPI;
import ir.khu.ie.publications.utils.AlertDialogCustomizer;
import ir.khu.ie.publications.utils.SaveManager;
import ir.khu.ie.publications.utils.Variables;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import top.wefor.circularanim.CircularAnim;

public class SplashActivity extends AppCompatActivity {

    private AVLoadingIndicatorView indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        indicator = findViewById(R.id.splashActivityLoadingIndicator);

        getAccount();
    }

    private void getAccount() {
        NetworkClientService.getRetrofitClient().create(AuthAPI.class)
                .getAccount(SaveManager.loadPhone(SplashActivity.this), SaveManager.loadAccessToken(SplashActivity.this))
                .enqueue(new Callback<GetAccountResponse>() {
            @Override
            public void onResponse(Call<GetAccountResponse> call, Response<GetAccountResponse> response) {
                if (response.body() != null) {
                    GetAccountResponse account = response.body();
                    if (account.getStatus().equals("OK")) {
                        Variables.accountData = account.getData();
                        SaveManager.setAccessToken(SplashActivity.this, account.getData().getAccessToken());
                        SaveManager.setPhone(SplashActivity.this, account.getData().getPhone());

                        if (!account.getMessage().equals(""))
                            showSingleButtonAlertDialog(account.getMessage(), getString(R.string.informal_ok), ((dialog, which) -> loadMainActivity()));
                        else loadMainActivity();
                    } else {
                        showTwoButtonAlertDialog(account.getMessage(), getString(R.string.retry), (dialog, which) -> getAccount(),
                                getString(R.string.exit), (dialog, which) -> finishAndRemoveTask());
                    }
                } else {
                    showTwoButtonAlertDialog(getString(R.string.error_occurred_try_again), getString(R.string.retry), (dialog, which) -> getAccount(),
                            getString(R.string.exit), (dialog, which) -> finishAndRemoveTask());
                }
            }

            @Override
            public void onFailure(Call<GetAccountResponse> call, Throwable t) {
                showTwoButtonAlertDialog(getString(R.string.error_occurred_try_again), getString(R.string.retry), (dialog, which) -> getAccount(),
                        getString(R.string.exit), (dialog, which) -> finishAndRemoveTask());
            }
        });
    }

    private void loadMainActivity() {
        try {
            indicator.post(() -> CircularAnim.fullActivity(SplashActivity.this, indicator)
                    .colorOrImageRes(R.color.lightBlue)
                    .duration(750)
                    .go(() -> new Handler().postDelayed(() -> {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }, 500)));
        } catch (Exception ignored) {
        }
    }

    private void showSingleButtonAlertDialog(String message, String buttonName, DialogInterface.OnClickListener clickListener) {
        runOnUiThread(() -> {
            AlertDialog dialog = new AlertDialog.Builder(SplashActivity.this)
                    .setTitle(R.string.app_name)
                    .setMessage(message)
                    .setPositiveButton(buttonName, clickListener)
                    .setCancelable(false)
                    .show();
            AlertDialogCustomizer.customizeSingleButtonTexts(SplashActivity.this, dialog, R.color.lightBlue);
        });
    }

    private void showTwoButtonAlertDialog(String message, String positiveButtonName, DialogInterface.OnClickListener positiveCallback,
                                          String negativeButtonName, DialogInterface.OnClickListener negativeCallback) {
        runOnUiThread(() -> {
            AlertDialog dialog = new AlertDialog.Builder(SplashActivity.this)
                    .setTitle(R.string.app_name)
                    .setMessage(message)
                    .setPositiveButton(positiveButtonName, positiveCallback)
                    .setNegativeButton(negativeButtonName, negativeCallback)
                    .setCancelable(false)
                    .show();
            AlertDialogCustomizer.customizeDoubleButtonTexts(SplashActivity.this, dialog, R.color.lightBlue, R.color.lightBlue);
        });
    }
}