package ir.khu.ie.publications.adapters;

import android.annotation.SuppressLint;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeCardRecyclerAdapter extends RecyclerView.Adapter<HomeCardRecyclerAdapter.ViewHolder> {
    private final Context context;
    private final List<Publication> publications;

    public HomeCardRecyclerAdapter(Context context, List<Publication> cardsData) {
        this.context = context;
        this.publications = cardsData;
    }

    @NonNull
    @Override
    public HomeCardRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_home_category_card, parent, false);
        return new HomeCardRecyclerAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HomeCardRecyclerAdapter.ViewHolder holder, int position) {
        Publication currentItem = publications.get(position);

        holder.publicationName.setText(currentItem.getTitle());
        holder.publicationDescription.setText(currentItem.getCreators().getAssociationName() + " " + currentItem.getCreators().getUniversityName());
        holder.publicationNumber.setText("شماره " + currentItem.getNumber());
        Picasso.get().load(currentItem.getImageUrl()).into(holder.cardImage);

        holder.publicationDescription.setSelected(true);

        holder.card.setOnClickListener(v -> {
            LoadingDialog.showLoadingDialog(context);
            NetworkClientService.getRetrofitClient().create(AppAPI.class).getPublication(currentItem.getId()).enqueue(new Callback<GetPublicationResponse>() {
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
        return publications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public AppCompatImageView cardImage;
        public AppCompatTextView publicationName;
        public AppCompatTextView publicationNumber;
        public AppCompatTextView publicationDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.itemHomeCategoryCard);
            cardImage = itemView.findViewById(R.id.itemHomeCategoryCardImage);
            publicationName = itemView.findViewById(R.id.itemHomeCategoryCardPublicationName);
            publicationNumber = itemView.findViewById(R.id.itemHomeCategoryCardNumber);
            publicationDescription = itemView.findViewById(R.id.itemHomeCategoryCardDescription);
        }
    }
}
