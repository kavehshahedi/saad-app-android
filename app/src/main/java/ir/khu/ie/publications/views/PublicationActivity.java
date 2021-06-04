package ir.khu.ie.publications.views;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import ir.khu.ie.publications.R;
import ir.khu.ie.publications.models.publications.Publication;
import per.wsj.library.AndRatingBar;

public class PublicationActivity extends AppCompatActivity {
    Publication publication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publication);

        publication = new Gson().fromJson(getIntent().getStringExtra("publication"), Publication.class);

        AppCompatImageView publicationImage = findViewById(R.id.publicationActivityPublicationImage);
        Picasso.get().load(publication.getImageUrl()).into(publicationImage);

        AppCompatTextView publicationName = findViewById(R.id.publicationActivityPublicationName);
        publicationName.setText(publication.getTitle());

        AppCompatTextView universityName = findViewById(R.id.publicationActivityUniversityName);
        universityName.setText(publication.getUniversityId());

        AppCompatTextView associationName = findViewById(R.id.publicationActivityAssociationName);
        associationName.setText(publication.getAssociationId());

        AppCompatTextView publicationNumber = findViewById(R.id.publicationActivityPublicationNumber);
        publicationNumber.setText(publication.getNumber());

        AppCompatTextView publicationDescription = findViewById(R.id.publicationActivityPublicationDescription);
        publicationDescription.setText(publication.getFullDescription());

        AppCompatTextView publicationRate = findViewById(R.id.publicationActivityRateNumber);
        publicationRate.setText(String.valueOf(publication.getRate()));

        AndRatingBar rateBar = findViewById(R.id.publicationActivityRateStars);
        rateBar.setRating(publication.getRate());

        AppCompatTextView publicationDownload = findViewById(R.id.publicationActivityNumberOfDownloads);
        publicationDownload.setText(String.valueOf(publication.getDownloadCount()));

        ConstraintLayout editorsChoiceLayout = findViewById(R.id.publicationActivityEditorsChoiceConstraintLayout);
        editorsChoiceLayout.setVisibility(publication.isEditorsChoice() ? View.VISIBLE : View.GONE);

        AppCompatTextView publicationSize = findViewById(R.id.publicationActivityAmountOfData);
        publicationSize.setText(String.valueOf(publication.getSize()));

        AppCompatButton downloadButton = findViewById(R.id.publicationActivityDownloadButton);
        downloadButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(publication.getDownloadUrl()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.android.chrome");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                intent.setPackage(null);
                startActivity(intent);
            }
        });
    }
}
