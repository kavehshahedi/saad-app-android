package ir.khu.ie.publications.views;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import ir.khu.ie.publications.R;
import ir.khu.ie.publications.adapters.SearchRecyclerAdapter;
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

        AppCompatButton searchButton = view.findViewById(R.id.fragmentSearchSearchButton);
        AppCompatButton alternativeSearchButton = view.findViewById(R.id.fragmentSearchAlternativeSearchButton);
        AppCompatEditText searchBox = view.findViewById(R.id.fragmentSearchSearchEditText);

        searchButton.setOnClickListener(v -> search(view));
        alternativeSearchButton.setOnClickListener(v -> search(view));
        searchBox.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_UP) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                search(view);
                return true;
            }
            return false;
        });
    }

    private void search(View view) {
        Guideline searchGuideline = view.findViewById(R.id.fragmentSearchGuideline);
        RecyclerView searchRecyclerView = view.findViewById(R.id.fragmentSearchRecyclerView);
        AppCompatEditText searchBox = view.findViewById(R.id.fragmentSearchSearchEditText);

        String searchText = Objects.requireNonNull(searchBox.getText()).toString().trim();
        if (searchText.length() < 3) {
            ToastMessage.showCustomToast(context, getString(R.string.search_text_short));
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
                        if (result.getData().size() > 0) {
                            searchGuideline.setGuidelinePercent(GUIDELINE_POSITION);
                            updateSearchLayout(view);
                            searchRecyclerView.setAdapter(new SearchRecyclerAdapter(context, result.getData()));
                            searchRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                        } else
                            ToastMessage.showCustomToast(context, getString(R.string.search_no_result));
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
    }

    private void updateSearchLayout(View view) {
        view.findViewById(R.id.fragmentSearchSearchButton).setVisibility(View.GONE);
        view.findViewById(R.id.fragmentSearchAlternativeSearchButton).setVisibility(View.VISIBLE);
    }
}