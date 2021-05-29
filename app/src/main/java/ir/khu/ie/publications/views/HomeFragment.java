package ir.khu.ie.publications.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.khu.ie.publications.R;
import ir.khu.ie.publications.adapters.HomeCategoryRecyclerAdapter;
import ir.khu.ie.publications.adapters.HomeSliderAdapter;
import ir.khu.ie.publications.models.responses.app.GetMainPageResponse;
import ss.com.bannerslider.Slider;

public class HomeFragment extends Fragment {

    private Context context;
    private List<GetMainPageResponse.Data.Slider> sliders;
    private List<GetMainPageResponse.Data.Category> categoriesData;
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
    }
}