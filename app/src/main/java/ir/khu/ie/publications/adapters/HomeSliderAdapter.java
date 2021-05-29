package ir.khu.ie.publications.adapters;

import android.util.Log;

import java.util.List;

import ir.khu.ie.publications.models.responses.app.GetMainPageResponse;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class HomeSliderAdapter extends SliderAdapter {

    public List<GetMainPageResponse.Data.Slider> sliders;

    public HomeSliderAdapter(List<GetMainPageResponse.Data.Slider> sliders) {
        this.sliders = sliders;
    }

    @Override
    public int getItemCount() {
        return sliders.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
        Log.e("Slider", sliders.get(position).getSliderImageUrl());
        viewHolder.bindImageSlide(sliders.get(position).getSliderImageUrl());
    }
}
