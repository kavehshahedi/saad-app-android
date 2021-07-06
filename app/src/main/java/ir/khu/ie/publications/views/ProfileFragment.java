package ir.khu.ie.publications.views;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.chaos.view.PinView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Objects;

import ir.khu.ie.publications.R;
import ir.khu.ie.publications.models.responses.Response;
import ir.khu.ie.publications.models.responses.auth.GetAccountResponse;
import ir.khu.ie.publications.services.NetworkClientService;
import ir.khu.ie.publications.services.api.AuthAPI;
import ir.khu.ie.publications.services.api.ProfileAPI;
import ir.khu.ie.publications.utils.LoadingDialog;
import ir.khu.ie.publications.utils.SaveManager;
import ir.khu.ie.publications.utils.ToastMessage;
import ir.khu.ie.publications.utils.Variables;
import retrofit2.Call;
import retrofit2.Callback;

public class ProfileFragment extends Fragment {

    private Context context;
    private GetAccountResponse.Data account;

    private ProfilePageType currentPage;

    private ConstraintLayout mainPage, numberPage, pinPage;

    private String temporaryPhone;

    public ProfileFragment() {
    }

    public ProfileFragment(Context context, GetAccountResponse.Data account) {
        this.context = context;
        this.account = account;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainPage = view.findViewById(R.id.fragmentProfileMainLayout);
        numberPage = view.findViewById(R.id.fragmentProfileEnterNumberLayout);
        pinPage = view.findViewById(R.id.fragmentProfileEnterPinLayout);

        setupMainPage(view);
        setupLoginPage(view);
        setupPinPage(view);

        changePage(ProfilePageType.MAIN);
    }

    private void setupMainPage(View view) {
        AppCompatTextView usernameText = view.findViewById(R.id.fragmentProfileNameTextView);
        AppCompatTextView phoneText = view.findViewById(R.id.fragmentProfilePhoneTextView);
        ConstraintLayout mainPageSingInLayout = view.findViewById(R.id.fragmentProfileSignInLayout);
        ConstraintLayout mainPageEditProfileLayout = view.findViewById(R.id.fragmentProfileEditLayout);

        if (!account.getPhone().equals("-1")) {
            usernameText.setText(account.getUserName());
            phoneText.setText(account.getPhone());
            mainPageSingInLayout.setVisibility(View.INVISIBLE);
            mainPageEditProfileLayout.setVisibility(View.VISIBLE);
        } else {
            usernameText.setText(context.getResources().getString(R.string.guest_user));
            phoneText.setText("");
            mainPageSingInLayout.setVisibility(View.VISIBLE);
            mainPageEditProfileLayout.setVisibility(View.INVISIBLE);
        }

        view.findViewById(R.id.fragmentProfileLoginButton).setOnClickListener(v -> changePage(ProfilePageType.ENTER_NUMBER));
        view.findViewById(R.id.fragmentProfileEditProfileButton).setOnClickListener(v -> openChangeNameSheet(view));
    }

    private void setupLoginPage(View view) {
        AppCompatEditText phoneInput = view.findViewById(R.id.fragmentProfileNumberEditText);
        AppCompatButton processButton = view.findViewById(R.id.fragmentProfileGetOtpCodeButton);
        AppCompatImageView closeButton = view.findViewById(R.id.fragmentProfilePhoneLayoutCancel);

        closeButton.setOnClickListener(v -> closePage());

        phoneInput.setText("");
        processButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.grey));

        phoneInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() != 10) {
                    processButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.grey));
                    processButton.setClickable(false);
                } else {
                    processButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.lightGreen));
                    processButton.setClickable(true);
                }
            }
        });

        processButton.setOnClickListener(v -> {
            String phone = Objects.requireNonNull(phoneInput.getText()).toString().trim().toLowerCase();

            if (phone.length() != 10 || !phone.startsWith("9")) {
                ToastMessage.showCustomToast(context, getString(R.string.invalid_phone_number));
                return;
            }

            phone = "0" + phone;

            LoadingDialog.showLoadingDialog(context);
            String finalPhone = phone;
            NetworkClientService.getRetrofitClient().create(AuthAPI.class).requestLogin(phone).enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    LoadingDialog.dismissLoadingDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equals("OK")) {
                            temporaryPhone = finalPhone;
                            changePage(ProfilePageType.ENTER_PIN);
                        } else ToastMessage.showCustomToast(context, response.body().getMessage());
                    } else
                        ToastMessage.showCustomToast(context, getString(R.string.error_occurred_try_again));
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    LoadingDialog.dismissLoadingDialog();
                    ToastMessage.showCustomToast(context, getString(R.string.error_occurred_try_again));
                }
            });
        });
    }

    private void setupPinPage(View view) {
        PinView pinView = view.findViewById(R.id.fragmentProfilePinView);
        AppCompatButton processButton = view.findViewById(R.id.fragmentProfileContinueButton);
        AppCompatImageView closeButton = view.findViewById(R.id.fragmentProfileEnterPinLayoutCancel);

        closeButton.setOnClickListener(v -> closePage());

        processButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.grey));

        processButton.setOnClickListener(v -> {
            String code = Objects.requireNonNull(pinView.getText()).toString().trim();

            if (code.length() != 4) {
                return;
            }

            LoadingDialog.showLoadingDialog(context);
            NetworkClientService.getRetrofitClient().create(AuthAPI.class).verifyLogin(temporaryPhone, code).enqueue(new Callback<GetAccountResponse>() {
                @Override
                public void onResponse(Call<GetAccountResponse> call, retrofit2.Response<GetAccountResponse> response) {
                    LoadingDialog.dismissLoadingDialog();
                    if (response.body() != null) {
                        GetAccountResponse account = response.body();
                        if (account.getStatus().equals("OK")) {
                            SaveManager.setPhone(context, account.getData().getPhone());
                            SaveManager.setAccessToken(context, account.getData().getAccessToken());
                            Variables.accountData = account.getData();
                            updateAccount(account.getData());

                            ToastMessage.showCustomToast(context, getString(R.string.logged_in_successfully));
                            changePage(ProfilePageType.MAIN);
                            setupMainPage(view);
                        } else ToastMessage.showCustomToast(context, account.getMessage());
                    } else
                        ToastMessage.showCustomToast(context, getString(R.string.error_occurred_try_again));
                }

                @Override
                public void onFailure(Call<GetAccountResponse> call, Throwable t) {
                    LoadingDialog.dismissLoadingDialog();
                    ToastMessage.showCustomToast(context, getString(R.string.error_occurred_try_again));
                }
            });
        });
        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() != 4) {
                    processButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.grey));
                    processButton.setClickable(false);
                } else {
                    processButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.lightGreen));
                    processButton.setClickable(true);
                }
            }
        });
    }

    private void openChangeNameSheet(View view) {
        BottomSheetDialog sheet = new BottomSheetDialog(context);
        sheet.setContentView(R.layout.sheet_edit_name);

        AppCompatEditText nameInput = sheet.findViewById(R.id.sheetEditNameNameEditText);
        AppCompatButton saveButton = sheet.findViewById(R.id.sheetEditNameSaveButton);
        AppCompatButton cancelButton = sheet.findViewById(R.id.sheetEditNameCancelButton);

        Objects.requireNonNull(cancelButton).setOnClickListener(v -> sheet.dismiss());

        Objects.requireNonNull(saveButton).setOnClickListener(v -> {
            String name = Objects.requireNonNull(Objects.requireNonNull(nameInput).getText()).toString().trim();

            if (name.length() < 3) {
                return;
            }

            LoadingDialog.showLoadingDialog(context);
            NetworkClientService.getRetrofitClient().create(ProfileAPI.class)
                    .editName(Variables.accountData.getPhone(), name)
                    .enqueue(new Callback<GetAccountResponse>() {
                        @Override
                        public void onResponse(Call<GetAccountResponse> call, retrofit2.Response<GetAccountResponse> response) {
                            LoadingDialog.dismissLoadingDialog();
                            if (response.body() != null) {
                                GetAccountResponse account = response.body();
                                if (account.getStatus().equals("OK")) {
                                    Variables.accountData.setUserName(account.getData().getUserName());
                                    updateAccount(account.getData());
                                    setupMainPage(view);
                                    ToastMessage.showCustomToast(context, getString(R.string.updated_profile_successfully));

                                    sheet.dismiss();
                                } else ToastMessage.showCustomToast(context, account.getMessage());
                            } else
                                ToastMessage.showCustomToast(context, getString(R.string.error_occurred_try_again));
                        }

                        @Override
                        public void onFailure(Call<GetAccountResponse> call, Throwable t) {
                            LoadingDialog.dismissLoadingDialog();
                            ToastMessage.showCustomToast(context, getString(R.string.error_occurred_try_again));
                        }
                    });
        });

        saveButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.grey));
        saveButton.setClickable(false);

        Objects.requireNonNull(nameInput).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() < 3 || s.toString().equals(account.getUserName())) {
                    saveButton.setClickable(false);
                    saveButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.grey));
                } else {
                    saveButton.setClickable(true);
                    saveButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.lightGreen));
                }
            }
        });

        sheet.show();
    }

    private void updateAccount(GetAccountResponse.Data account) {
        this.account = account;
    }

    private void closePage() {
        if (currentPage == ProfilePageType.MAIN) return;

        if (currentPage == ProfilePageType.ENTER_NUMBER) changePage(ProfilePageType.MAIN);
        else changePage(ProfilePageType.ENTER_NUMBER);
    }

    private void changePage(ProfilePageType type) {
        currentPage = type;
        switch (type) {
            case MAIN:
                mainPage.setVisibility(View.VISIBLE);
                numberPage.setVisibility(View.INVISIBLE);
                pinPage.setVisibility(View.INVISIBLE);
                break;
            case ENTER_NUMBER:
                mainPage.setVisibility(View.INVISIBLE);
                numberPage.setVisibility(View.VISIBLE);
                pinPage.setVisibility(View.INVISIBLE);
                break;
            case ENTER_PIN:
                mainPage.setVisibility(View.INVISIBLE);
                numberPage.setVisibility(View.INVISIBLE);
                pinPage.setVisibility(View.VISIBLE);
                break;
        }
    }

    private enum ProfilePageType {
        MAIN,
        ENTER_NUMBER,
        ENTER_PIN
    }
}