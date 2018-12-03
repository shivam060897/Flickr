package pandit.flickr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class DirectPhotoDisplay extends BaseActivity {
    ImageView fullDirectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directphotodisplay);
        fullDirectView = findViewById(R.id.fullDirectView);

        Intent intent = getIntent();
        final Photo photo = (Photo) intent.getSerializableExtra(PHOTO_TRANSFER);
        if (photo != null){
            Picasso.get().load(photo.getLink())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(fullDirectView);
        }

        fullDirectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
