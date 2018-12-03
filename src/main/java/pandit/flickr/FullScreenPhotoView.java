package pandit.flickr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class FullScreenPhotoView extends BaseActivity {
    ImageView fullView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullphotoview);
        fullView = findViewById(R.id.fullView);
        Intent intent = getIntent();
        String photoLink = intent.getStringExtra("PHOTO_LINK");

        Picasso.get().load(photoLink)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(fullView);

        fullView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
