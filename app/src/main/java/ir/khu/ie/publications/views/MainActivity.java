package ir.khu.ie.publications.views;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import ir.khu.ie.publications.R;
import ir.khu.ie.publications.adapters.ViewPagerAdapter;
import ir.khu.ie.publications.models.responses.app.GetMainPageResponse;
import ir.khu.ie.publications.services.NetworkClientService;
import ir.khu.ie.publications.services.api.AppAPI;
import ir.khu.ie.publications.utils.LoadingDialog;
import ir.khu.ie.publications.utils.Variables;
import me.ibrahimsn.lib.SmoothBottomBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getMainPage();
    }

    private void getMainPage() {
        LoadingDialog.showLoadingDialog(MainActivity.this);
        NetworkClientService.getRetrofitClient().create(AppAPI.class).getMainPage(Variables.mainPageRetrieveCount).enqueue(new Callback<GetMainPageResponse>() {
            @Override
            public void onResponse(Call<GetMainPageResponse> call, Response<GetMainPageResponse> response) {
                LoadingDialog.dismissLoadingDialog();
                if (response.body() != null) {
                    GetMainPageResponse mainPage = response.body();
                    if (mainPage.getStatus().equals("OK")) {
                        initializeViewPage(mainPage);
                    } else {
                        Log.e("Error", mainPage.getMessage());
                    }
                } else {
                    Log.e("Error", "Body is null");
                }
            }

            @Override
            public void onFailure(Call<GetMainPageResponse> call, Throwable t) {
                LoadingDialog.dismissLoadingDialog();
                Log.e("Error", t.getMessage());
            }
        });
    }

    private void initializeViewPage(GetMainPageResponse mainPage) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        adapter.addFragment(new ProfileFragment(MainActivity.this, Variables.accountData), getResources().getString(R.string.profile));
        adapter.addFragment(new SearchFragment(MainActivity.this), getResources().getString(R.string.search));
        adapter.addFragment(new HomeFragment(MainActivity.this, mainPage.getData().getSliders(), mainPage.getData().getCategories()), getResources().getString(R.string.home));

        ViewPager viewPager = findViewById(R.id.mainActivityViewPager);
        viewPager.setAdapter(adapter);

        SmoothBottomBar bottomBar = findViewById(R.id.mainActivityNavigationBar);
        bottomBar.setOnItemSelectedListener(i -> {
            viewPager.setCurrentItem(i);
            return false;
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                bottomBar.setItemActiveIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        viewPager.setCurrentItem(2);
    }
}