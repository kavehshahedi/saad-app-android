package ir.khu.ie.publications.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.khu.ie.publications.R;
import ir.khu.ie.publications.models.responses.app.GetMainPageResponse;

public class HomeCategoryRecyclerAdapter extends RecyclerView.Adapter<HomeCategoryRecyclerAdapter.ViewHolder> {

    private final Context context;
    private final List<GetMainPageResponse.Data.Category> categoriesData;

    public HomeCategoryRecyclerAdapter(Context context, List<GetMainPageResponse.Data.Category> categoriesData) {
        this.context = context;
        this.categoriesData = categoriesData;
    }

    @NonNull
    @Override
    public HomeCategoryRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_home_category_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCategoryRecyclerAdapter.ViewHolder holder, int position) {
        GetMainPageResponse.Data.Category currentItem = categoriesData.get(position);

        holder.categoryName.setText(currentItem.getCategoryName());
        holder.publicationsRecyclerView.setAdapter(new HomeCardRecyclerAdapter(context, currentItem.getPublications()));
        holder.publicationsRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, true));
    }

    @Override
    public int getItemCount() {
        return categoriesData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView categoryName;
        public RecyclerView publicationsRecyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryName = itemView.findViewById(R.id.itemHomeCategoryRecyclerCategoryText);
            publicationsRecyclerView = itemView.findViewById(R.id.itemHomeCategoryRecyclerInnerRecycler);
        }
    }
}
