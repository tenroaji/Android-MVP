package lagecong.com.mvp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import lagecong.com.mvp.utils.ZoomableImageView;

public class ImageZoom extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom);

        final ZoomableImageView zoomableImageView = findViewById(R.id.zoom);
        Intent mIntent = getIntent();
        Integer image = mIntent.getIntExtra("image", 0);
        String url = mIntent.getStringExtra("url");
        if (image != 0){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),image);
            zoomableImageView.setImageBitmap(bitmap);}
        else{
            Picasso.get().load(url).into(new PicassoTargetZoomableImageView(zoomableImageView));
        }

        ImageView close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    final class PicassoTargetZoomableImageView implements Target{
        private final ZoomableImageView mImageView;
        private final TextView textLoading;

        private PicassoTargetZoomableImageView(ZoomableImageView imageView){
            mImageView = imageView;
            mImageView.setTag(this);
            textLoading = findViewById(R.id.text_loading);
            textLoading.setText(getResources().getString(R.string.text_memuat_gambar));
            textLoading.setVisibility(View.VISIBLE);
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            mImageView.setImageBitmap(bitmap);
            textLoading.setVisibility(View.GONE);
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            textLoading.setText("Gagal memuat gambar");
            textLoading.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    }
}
