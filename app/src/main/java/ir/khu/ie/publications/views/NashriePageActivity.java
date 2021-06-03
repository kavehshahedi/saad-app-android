package ir.khu.ie.publications.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import ir.khu.ie.publications.R;
import ir.khu.ie.publications.models.publications.Publication;

public class NashriePageActivity extends AppCompatActivity {
    Publication publication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nashriepage);

        //String sardabirName
        //String geraphistName

        ImageView nashrieImage = (ImageView)findViewById(R.id.NashriePage_NashrieImage);
        Picasso.get().load(publication.getImageUrl()).into(nashrieImage);

        TextView nashrieName = (TextView)findViewById(R.id.NashriePage_NashrieName);
        nashrieName.setText(publication.getTitle());

        TextView uniName = (TextView)findViewById(R.id.NashriePage_UniversityName);
        nashrieName.setText(publication.getUniversityId());

        TextView associationName = (TextView)findViewById(R.id.NashriePage_AssociationName);
        nashrieName.setText(publication.getAssociationId());

        TextView nashrieNumber = (TextView)findViewById(R.id.NashriePage_NashrieNumber);
        nashrieName.setText(publication.getNumber());

        TextView fullDesc = (TextView)findViewById(R.id.NashriePage_Description);
        nashrieName.setText(publication.getFullDescription());

        Button downloadUrl = (Button)findViewById(R.id.NashriePage_DownloadButton);
    }
}
