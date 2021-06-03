package ir.khu.ie.publications.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import ir.khu.ie.publications.R;
import ir.khu.ie.publications.models.responses.app.SearchResponse;
import ir.khu.ie.publications.services.NetworkClientService;
import ir.khu.ie.publications.services.api.AppAPI;
import ir.khu.ie.publications.utils.LoadingDialog;
import ir.khu.ie.publications.utils.ToastMessage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private final float GUIDELINE_POSITION = 0.025f;
    private Context context;

    public SearchFragment() {
        // Required empty public constructor
    }

    public SearchFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Guideline searchGuideline = view.findViewById(R.id.fragmentSearchGuideline);
        AppCompatEditText searchBox = view.findViewById(R.id.fragmentSearchSearchEditText);
        AppCompatButton searchButton = view.findViewById(R.id.fragmentSearchSearchButton);

        searchButton.setOnClickListener(v -> {
            String searchText = Objects.requireNonNull(searchBox.getText()).toString().trim();
            if (searchText.length() < 3) {
                return;
            }

            LoadingDialog.showLoadingDialog(context);
            NetworkClientService.getRetrofitClient().create(AppAPI.class).search(searchText).enqueue(new Callback<SearchResponse>() {
                @Override
                public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                    LoadingDialog.dismissLoadingDialog();
                    if (response.body() != null) {
                        SearchResponse result = response.body();
                        if (result.getStatus().equals("OK")) {
                            searchGuideline.setGuidelinePercent(GUIDELINE_POSITION);

                            
                        } else
                            ToastMessage.showCustomToast(context, result.getMessage());
                    } else
                        ToastMessage.showCustomToast(context, context.getResources().getString(R.string.error_occurred_try_again));
                }

                @Override
                public void onFailure(Call<SearchResponse> call, Throwable t) {
                    LoadingDialog.dismissLoadingDialog();
                    ToastMessage.showCustomToast(context, context.getResources().getString(R.string.error_occurred_try_again));
                }
            });
        });
    }
}