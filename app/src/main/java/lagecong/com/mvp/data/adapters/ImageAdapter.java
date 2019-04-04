package lagecong.com.mvp.data.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import lagecong.com.mvp.R;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.SliderViewHolder> {

    private List<String> mListResource;


    public ImageAdapter(List<String> listImage){
        mListResource=listImage;
    }


    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_image,parent,false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        final int indexPosition = position;
        Picasso.get().load(mListResource.get(indexPosition))
                .placeholder(R.drawable.image_loader)
                .error(R.drawable.image_error)
                .into(holder.mImage);



        holder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent newIntent=new Intent(v.getContext(), ImageZoom.class);
//                newIntent.putExtra("url",mListResource.get(indexPosition));
//                v.getContext().startActivity(newIntent);
//                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    ((AppCompatActivity) v.getContext()).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return mListResource.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImage;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.imageView);
        }
    }
}
