package ir.khu.ie.publications.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;

import ir.khu.ie.publications.R;
import ir.khu.ie.publications.adapters.SearchRecyclerAdapter;
import ir.khu.ie.publications.models.responses.app.GetMainPageResponse.Data.Category;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Context context = this;

        Category category = new Gson().fromJson(getIntent().getStringExtra("category"), Category.class);

        AppCompatTextView title = findViewById(R.id.categoryActivityTitle);
        title.setText(category.getCategoryName());

        RecyclerView recyclerView = findViewById(R.id.categoryActivityRecyclerView);
        recyclerView.setAdapter(new SearchRecyclerAdapter(context, category.getPublications()));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }
}