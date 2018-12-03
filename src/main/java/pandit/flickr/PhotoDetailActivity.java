package pandit.flickr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        activateToolbar(true);
        ImageView photoImage = findViewById(R.id.photo_image);

        Intent intent = getIntent();
        final Photo photo = (Photo) intent.getSerializableExtra(PHOTO_TRANSFER);
        if (photo != null) {
            TextView photoTitle = findViewById(R.id.photo_title);
            photoTitle.setText("Title: " + photo.getTitle());

            TextView photoTags = findViewById(R.id.photo_tags);
            photoTags.setText("Tags: " + photo.getTags());

            TextView photoAuthor = findViewById(R.id.photo_author);
            photoAuthor.setText("Author: " + photo.getAuthor());

            Picasso.get().load(photo.getLink())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(photoImage);
        }

        photoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String photoLink = photo.getLink();
                if (!photoLink.equals("")){
                    startActivity((new Intent(PhotoDetailActivity.this,FullScreenPhotoView.class)).putExtra("PHOTO_LINK",photoLink));
                }
            }
        });
    }
}
