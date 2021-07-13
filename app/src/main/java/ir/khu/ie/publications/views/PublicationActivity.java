package ir.khu.ie.publications.views;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ir.khu.ie.publications.R;
import ir.khu.ie.publications.adapters.PublicationCommentRecyclerAdapter;
import ir.khu.ie.publications.adapters.PublicationInformationRecyclerAdapter;
import ir.khu.ie.publications.models.publications.Publication;
import ir.khu.ie.publications.models.publications.PublicationInformation;
import ir.khu.ie.publications.models.responses.app.SetFavoriteResponse;
import ir.khu.ie.publications.services.NetworkClientService;
import ir.khu.ie.publications.services.api.AppAPI;
import ir.khu.ie.publications.utils.LoadingDialog;
import ir.khu.ie.publications.utils.ToastMessage;
import ir.khu.ie.publications.utils.Variables;
import per.wsj.library.AndRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.zamani.persiandate.PersianDate;

public class PublicationActivity extends AppCompatActivity {
    Publication publication;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publication);

        context = this;

        publication = new Gson().fromJson(getIntent().getStringExtra("publication"), Publication.class);

        AppCompatImageView publicationImage = findViewById(R.id.publicationActivityPublicationImage);
        Picasso.get().load(publication.getImageUrl()).into(publicationImage);

        AppCompatTextView publicationDescription = findViewById(R.id.publicationActivityPublicationDescription);
        publicationDescription.setText(publication.getFullDescription());

        AppCompatTextView publicationRate = findViewById(R.id.publicationActivityRateNumber);
        publicationRate.setText(String.valueOf(publication.getRate()));

        AndRatingBar rateBar = findViewById(R.id.publicationActivityRateStars);
        rateBar.setRating(publication.getRate());

        AppCompatTextView publicationDownload = findViewById(R.id.publicationActivityNumberOfDownloads);
        publicationDownload.setText(String.valueOf(getDownloadsText(publication.getDownloadCount())));

        ConstraintLayout editorsChoiceLayout = findViewById(R.id.publicationActivityEditorsChoiceConstraintLayout);
        editorsChoiceLayout.setVisibility(publication.isEditorsChoice() ? View.VISIBLE : View.GONE);

        AppCompatTextView publicationSize = findViewById(R.id.publicationActivityAmountOfData);
        publicationSize.setText(String.valueOf(publication.getSize()));

        AppCompatButton downloadButton = findViewById(R.id.publicationActivityDownloadButton);
        downloadButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(publication.getDownloadUrl().trim()));
            intent.setPackage("com.android.chrome");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                intent.setPackage(null);
                startActivity(intent);
            }
        });

        boolean contains = false;
        AppCompatImageView bookmarkImage = findViewById(R.id.publicationActivityBookmarkImage);
        for (String bookmark : Variables.accountData.getFavoritePublications()) {
            if (bookmark.equals(publication.getId())) {
                contains = true;
                break;
            }
        }

        if (contains)
            bookmarkImage.setColorFilter(ContextCompat.getColor(context, R.color.lightBlue), android.graphics.PorterDuff.Mode.MULTIPLY);
        else
            bookmarkImage.setColorFilter(ContextCompat.getColor(context, R.color.lightGrey), android.graphics.PorterDuff.Mode.MULTIPLY);

        bookmarkImage.setOnClickListener(v -> {
            LoadingDialog.showLoadingDialog(context);
            NetworkClientService.getRetrofitClient().create(AppAPI.class).setFavorite(publication.getId(), Variables.accountData.getPhone())
                    .enqueue(new Callback<SetFavoriteResponse>() {
                        @Override
                        public void onResponse(Call<SetFavoriteResponse> call, Response<SetFavoriteResponse> response) {
                            LoadingDialog.dismissLoadingDialog();

                            if (response.body() != null) {
                                SetFavoriteResponse fav = response.body();
                                if (fav.getStatus().equals("OK")) {
                                    bookmarkImage.setColorFilter(
                                            ContextCompat.getColor(context, fav.getData().isBookmark() ? R.color.lightBlue : R.color.lightGrey),
                                            android.graphics.PorterDuff.Mode.MULTIPLY);
                                    Variables.accountData.setFavoritePublications(fav.getData().getFavoritePublications());
                                } else ToastMessage.showCustomToast(context, fav.getMessage());
                            } else
                                ToastMessage.showCustomToast(context, getResources().getString(R.string.error_occurred_try_again));
                        }

                        @Override
                        public void onFailure(Call<SetFavoriteResponse> call, Throwable t) {
                            LoadingDialog.dismissLoadingDialog();
                            ToastMessage.showCustomToast(context, getResources().getString(R.string.error_occurred_try_again));
                        }
                    });
        });

        List<PublicationInformation> information = new ArrayList<>();
        if (publication.getCreators().getUniversityName() != null && !publication.getCreators().getUniversityName().isEmpty())
            information.add(new PublicationInformation(getString(R.string.university), publication.getCreators().getUniversityName()));
        if (publication.getCreators().getAssociationName() != null && !publication.getCreators().getAssociationName().isEmpty())
            information.add(new PublicationInformation(getString(R.string.association), publication.getCreators().getAssociationName()));
        if (publication.getCreators().getCeo() != null && !publication.getCreators().getCeo().isEmpty())
            information.add(new PublicationInformation(getString(R.string.ceo), publication.getCreators().getCeo()));
        if (publication.getCreators().getPageDesigner() != null && !publication.getCreators().getPageDesigner().isEmpty())
            information.add(new PublicationInformation(getString(R.string.page_designer), publication.getCreators().getPageDesigner()));
        if (publication.getCreators().getCoverDesigner() != null && !publication.getCreators().getCoverDesigner().isEmpty())
            information.add(new PublicationInformation(getString(R.string.cover_designer), publication.getCreators().getCoverDesigner()));
        if (publication.getNumber() != null && !publication.getNumber().isEmpty())
            information.add(new PublicationInformation(getString(R.string.number), publication.getNumber()));
        if (publication.getReleasedDate() != null) {
            PersianDate date = new PersianDate(publication.getReleasedDate());
            String seasonName = "";
            if (date.getShMonth() >= 1 && date.getShMonth() <= 3)
                seasonName = getString(R.string.spring);
            if (date.getShMonth() >= 4 && date.getShMonth() <= 6)
                seasonName = getString(R.string.summer);
            if (date.getShMonth() >= 7 && date.getShMonth() <= 9)
                seasonName = getString(R.string.fall);
            if (date.getShMonth() >= 10 && date.getShMonth() <= 12)
                seasonName = getString(R.string.winter);

            information.add(new PublicationInformation(getString(R.string.release_date), seasonName + " " + date.getShYear()));
        }

        PublicationInformationRecyclerAdapter informationAdapter = new PublicationInformationRecyclerAdapter(context, information);
        RecyclerView informationRecyclerView = findViewById(R.id.publicationActivityInformationRecycler);
        informationRecyclerView.setAdapter(informationAdapter);
        informationRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        ScrollView scrollView = findViewById(R.id.publicationActivityScrollView);
        scrollView.smoothScrollTo(0, 0);

        RecyclerView commentsRecyclerView = findViewById(R.id.publicationActivityCommentsRecycler);
        commentsRecyclerView.setAdapter(new PublicationCommentRecyclerAdapter(context, publication.getComments()));
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        commentsRecyclerView.setClickable(false);
        commentsRecyclerView.setNestedScrollingEnabled(false);
    }

    private String getDownloadsText(int downloads) {
        String result = "";
        if(downloads >= 0 && downloads < 5) result = "~5";
        else if (downloads >= 5 && downloads <= 10) result = "~10";
        else if (downloads > 10 && downloads <= 50) result = "+10";
        else if (downloads > 50 && downloads <= 100) result = "+50";
        else if (downloads > 100 && downloads <= 200) result = "+100";
        else if (downloads > 200 && downloads <= 500) result = "+200";
        else if (downloads > 500 && downloads <= 1000) result = "+500";
        else if (downloads > 1000 && downloads <= 5000) result = "+1000";
        else if (downloads > 5000 && downloads <= 10000) result = "+5000";
        else if (downloads > 10000) result = "+10000";

        return result;
    }
}
