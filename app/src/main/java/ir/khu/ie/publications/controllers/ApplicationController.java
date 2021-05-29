package ir.khu.ie.publications.controllers;

import android.app.Application;

import ir.khu.ie.publications.services.PicassoImageLoadingService;
import ss.com.bannerslider.Slider;

public class ApplicationController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Slider.init(new PicassoImageLoadingService());
    }
}
