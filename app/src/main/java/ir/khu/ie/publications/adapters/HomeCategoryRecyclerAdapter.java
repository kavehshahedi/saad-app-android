package ir.khu.ie.publications.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

import ir.khu.ie.publications.R;
import ir.khu.ie.publications.models.responses.app.GetCategoryResponse;
import ir.khu.ie.publications.models.responses.app.GetMainPageResponse;
import ir.khu.ie.publications.services.NetworkClientService;
import ir.khu.ie.publications.services.api.AppAPI;
import ir.khu.ie.publications.utils.LoadingDialog;
import ir.khu.ie.publications.utils.ToastMessage;
import ir.khu.ie.publications.views.CategoryActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        holder.moreButton.setOnClickListener(v -> {
            LoadingDialog.showLoadingDialog(context);
            NetworkClientService.getRetrofitClient().create(AppAPI.class).getCategory(currentItem.getCategoryId()).enqueue(new Callback<GetCategoryResponse>() {
                @Override
                public void onResponse(Call<GetCategoryResponse> call, Response<GetCategoryResponse> response) {
                    LoadingDialog.dismissLoadingDialog();
                    if (response.body() != null) {
                        GetCategoryResponse category = response.body();
                        if (category.getStatus().equals("OK")) {
                            if (category.getData().getPublications().size() > 0) {
                                context.startActivity(new Intent(context, CategoryActivity.class).putExtra("category", new Gson().toJson(category.getData())));
                            } else
                                ToastMessage.showCustomToast(context, context.getString(R.string.no_data_for_category_err));
                        } else
                            ToastMessage.showCustomToast(context, category.getMessage());
                    } else
                        ToastMessage.showCustomToast(context, context.getResources().getString(R.string.error_occurred_try_again));
                }

                @Override
                public void onFailure(Call<GetCategoryResponse> call, Throwable t) {
                    Log.e("Err", t.getMessage());
                    LoadingDialog.dismissLoadingDialog();
                    ToastMessage.showCustomToast(context, context.getResources().getString(R.string.error_occurred_try_again));
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return categoriesData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView categoryName;
        public RecyclerView publicationsRecyclerView;
        public AppCompatTextView moreButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryName = itemView.findViewById(R.id.itemHomeCategoryRecyclerCategoryText);
            publicationsRecyclerView = itemView.findViewById(R.id.itemHomeCategoryRecyclerInnerRecycler);
            moreButton = itemView.findViewById(R.id.itemHomeCategoryRecyclerMoreText);
        }
    }
}
