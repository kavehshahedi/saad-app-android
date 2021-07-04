package ir.khu.ie.publications.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import ir.khu.ie.publications.R;
import ir.khu.ie.publications.models.publications.Publication;
import ir.khu.ie.publications.models.responses.app.GetPublicationResponse;
import ir.khu.ie.publications.services.NetworkClientService;
import ir.khu.ie.publications.services.api.AppAPI;
import ir.khu.ie.publications.utils.LoadingDialog;
import ir.khu.ie.publications.utils.ToastMessage;
import ir.khu.ie.publications.views.PublicationActivity;
import per.wsj.library.AndRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {

    private final Context context;
    private final List<Publication> searchResults;

    public SearchRecyclerAdapter(Context context, List<Publication> searchResults) {
        this.context = context;
        this.searchResults = searchResults;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Publication publication = searchResults.get(position);

        holder.publicationName.setText(publication.getTitle());
        holder.universityName.setText(publication.getCreators().getUniversityName());
        holder.associationName.setText(publication.getCreators().getAssociationName());
        holder.rateBar.setRating(publication.getRate());

        Picasso.get().load(publication.getImageUrl()).into(holder.publicationImage);

        holder.card.setOnClickListener(v -> {
            LoadingDialog.showLoadingDialog(context);
            NetworkClientService.getRetrofitClient().create(AppAPI.class).getPublication(publication.getId()).enqueue(new Callback<GetPublicationResponse>() {
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
    public int getItemCount() {
        return searchResults.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public AppCompatImageView publicationImage;
        public AppCompatTextView publicationName, universityName, associationName;
        public AndRatingBar rateBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.itemSearchResultCard);
            publicationImage = itemView.findViewById(R.id.itemSearchResultPublicationImage);
            publicationName = itemView.findViewById(R.id.itemSearchResultPublicationName);
            universityName = itemView.findViewById(R.id.itemSearchResultPublicationUniversityName);
            associationName = itemView.findViewById(R.id.itemSearchResultPublicationAssociationName);
            rateBar = itemView.findViewById(R.id.itemSearchResultPublicationRate);
        }
    }
}
