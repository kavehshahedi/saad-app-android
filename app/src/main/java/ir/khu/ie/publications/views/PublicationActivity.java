package ir.khu.ie.publications.views;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.squareup.picasso.Picasso;

import ir.khu.ie.publications.R;
import ir.khu.ie.publications.models.publications.Publication;

public class PublicationActivity extends AppCompatActivity {
    Publication publication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publication);

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
