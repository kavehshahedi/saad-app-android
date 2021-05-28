package ir.khu.ie.publications.views;

import android.os.Bundle;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import ir.khu.ie.publications.R;
import ir.khu.ie.publications.adapters.ViewPagerAdapter;
import ir.khu.ie.publications.models.adapters.HomeCardsModel;
import ir.khu.ie.publications.models.adapters.HomeCategoryModel;
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

        String imageUrl ="https://lh3.googleusercontent.com/proxy/l3UP1a_afAHgLcIrwjwbllJvHKOBdoukSlvKadBSEhgbhjVD4Y2Kq2uJGvIevaxe_KTbW-eu3R60ImG42Ubm1vOrTof28xVuzHE6o-dxtH-d9Ma8SQjCfQRB";

        ArrayList<HomeCardsModel> cardData = new ArrayList<>();
        cardData.add(new HomeCardsModel(imageUrl,"انجمن کامپیوتر","این مجله بسیار خفن و آب آور است"));
        cardData.add(new HomeCardsModel(imageUrl,"انجمن علوم","این مجله عجیب است"));
        cardData.add(new HomeCardsModel(imageUrl,"انجمن اسکلا","این مجله دیگر چه کوفتی است اصلا نمی دانم"));
        cardData.add(new HomeCardsModel(imageUrl,"انجمن روانیا","این مجله را باید سوزاند از بس کسشر است"));
        cardData.add(new HomeCardsModel(imageUrl,"انجمن بیکارا","تف تو این مجله کی به اینا مجوز داده"));
        cardData.add(new HomeCardsModel(imageUrl,"انجمن پلشتا","اوه مای فاکینگ گاد آیم دان"));
        cardData.add(new HomeCardsModel(imageUrl,"انجمن فاک میرطاهری","نایس"));

        ArrayList<HomeCategoryModel> categoryData = new ArrayList<>();
        categoryData.add(new HomeCategoryModel("1","دسته بندی 1",cardData));
        categoryData.add(new HomeCategoryModel("2","دسته بندی 2",cardData));
        categoryData.add(new HomeCategoryModel("3","دسته بندی 3",cardData));
        categoryData.add(new HomeCategoryModel("4","دسته بندی 4",cardData));
        categoryData.add(new HomeCategoryModel("5","دسته بندی 5",cardData));
        categoryData.add(new HomeCategoryModel("6","دسته بندی 6",cardData));
        categoryData.add(new HomeCategoryModel("7","دسته بندی 7",cardData));
        categoryData.add(new HomeCategoryModel("8","دسته بندی 8",cardData));

        adapter.addFragment(new HomeFragment(MainActivity.this, categoryData), getResources().getString(R.string.home));

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