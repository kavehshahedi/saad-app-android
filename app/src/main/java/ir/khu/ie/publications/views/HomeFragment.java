package ir.khu.ie.publications.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

import ir.khu.ie.publications.R;
import ir.khu.ie.publications.adapters.HomeCategoryRecyclerAdapter;
import ir.khu.ie.publications.adapters.HomeSliderAdapter;
import ir.khu.ie.publications.models.responses.app.GetMainPageResponse;
import ir.khu.ie.publications.models.responses.app.GetPublicationResponse;
import ir.khu.ie.publications.services.NetworkClientService;
import ir.khu.ie.publications.services.api.AppAPI;
import ir.khu.ie.publications.utils.LoadingDialog;
import ir.khu.ie.publications.utils.OnBackPressed;
import ir.khu.ie.publications.utils.ToastMessage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.event.OnSlideClickListener;

public class HomeFragment extends Fragment implements OnBackPressed {

    private Context context;
    private List<GetMainPageResponse.Data.Slider> sliders;
    private List<GetMainPageResponse.Data.Category> categoriesData;
    private boolean doubleBackToExitPressedOnce;
    //private ArrayList<HomeCardsModel> cardsData;

    public HomeFragment() {
    }

    public HomeFragment(Context context, List<GetMainPageResponse.Data.Slider> sliders, List<GetMainPageResponse.Data.Category> categoriesData) {
        this.context = context;
        this.sliders = sliders;
        this.categoriesData = categoriesData;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView homeCategoryRecyclerView = view.findViewById(R.id.fragmentHomeRecyclerView);
        HomeCategoryRecyclerAdapter homeCategoryRecyclerAdapter = new HomeCategoryRecyclerAdapter(context, categoriesData);
        homeCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        homeCategoryRecyclerView.setAdapter(homeCategoryRecyclerAdapter);

        Slider slider = view.findViewById(R.id.fragmentHomeSlider);
        HomeSliderAdapter sliderAdapter = new HomeSliderAdapter(sliders);
        slider.setAdapter(sliderAdapter);
        slider.setOnSlideClickListener(position -> {
            LoadingDialog.showLoadingDialog(context);
            NetworkClientService.getRetrofitClient().create(AppAPI.class).getPublication(sliders.get(position).getSliderId()).enqueue(new Callback<GetPublicationResponse>() {
                @Override
                public void onResponse(Call<GetPublicationResponse> call, Response<GetPublicationResponse> response) {
                    LoadingDialog.dismissLoadingDialog();
                    if (response.body() != null) {
                        GetPublicationResponse publication = response.body();
                        if (publication.getStatus().equals("OK")) {
                            context.startActivity(new Intent(context, PublicationActivity.class).putExtra("publication", new Gson().toJson(publication.getData())));
                        } else ToastMessage.showCustomToast(context, publication.getMessage());
                    } else
                        ToastMessage.showCustomToast(context, context.getResources().getString(R.string.error_occurred_try_again));
                }

                @Override
                public void onFailure(Call<GetPublicationResponse> call, Throwable t) {
                    LoadingDialog.dismissLoadingDialog();
                    ToastMessage.showCustomToast(context, context.getResources().getString(R.string.error_occurred_try_again));
                }
            });
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            requireActivity().finishAndRemoveTask();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        ToastMessage.showCustomToast(context, getString(R.string.tap_again_close));

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }
}