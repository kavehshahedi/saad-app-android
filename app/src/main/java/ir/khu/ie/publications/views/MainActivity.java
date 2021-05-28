package ir.khu.ie.publications.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import ir.khu.ie.publications.R;
import ir.khu.ie.publications.adapters.ViewPagerAdapter;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViewPage();
    }

    private void initializeViewPage() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        //adapter.addFragment(new HomeFragment(MainActivity.this, new ArrayList<>()), getResources().getString(R.string.home));

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

        //viewPager.setCurrentItem(2);
    }
}