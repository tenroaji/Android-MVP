package lagecong.com.mvp.data.adapters;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import lagecong.com.mvp.ArtikelDetail.DetailActivity;
import lagecong.com.mvp.R;
import lagecong.com.mvp.commons.ItemLoadingViewHolder;
import lagecong.com.mvp.data.model.ModelArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class AdapterArticle extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ModelArticleResponse> data;
    private static final int TYPE_LOADING = 1;
    private static final int TYPE_ITEM = 2;
    private List mListData;
    private boolean isLoading = false;

    public AdapterArticle(){
        data = new ArrayList<>();
    }

    public void updateAdapter(@NonNull List<ModelArticleResponse> list){
        data = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void setLoading(boolean show) {
        if(show && !isLoading){
            isLoading = true;
            final int position = data.size();
            data.add(position,null);
            notifyDataSetChanged();
        }else if(!show && isLoading){
            isLoading = false;
            final int position = (data.size()-1);
            data.remove(position);
            notifyDataSetChanged();
        }
    }

    @SuppressWarnings("unchecked")
    public void clearList(){
//        boolean isHaveHead = false;
//        if(data.size() > 0){
//            isHaveHead = true;
//        }
        data.clear();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_article, parent, false);
//        return new AdapterArticle.MyViewHolder(mView);

        View mView;
        RecyclerView.ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case TYPE_LOADING:
                mView = inflater.inflate(R.layout.aaa_item_view_loader, parent, false);
                holder = new ItemLoadingViewHolder(mView);
                break;
            case TYPE_ITEM:
            default:
                mView = inflater.inflate(R.layout.layout_list_article, parent, false);
                holder = new MyViewHolder(mView);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderView, final int position) {
        int type = getItemViewType(position);
        final int indexPosition = position;//jangan gunakan position sebagai final parameter;
        switch (type) {

            case TYPE_ITEM:
                MyViewHolder holder = (MyViewHolder) holderView;
                final String title = data.get(indexPosition).getTitle();
                final String isi = data.get(indexPosition).getContent();
                final String tanggal = data.get(indexPosition).getPublished();
                final String imageUrl = data.get(indexPosition).getImage();
                final String id = ""+data.get(indexPosition).getId_target();
                holder.tanggal.setText(tanggal);
                holder.title.setText(title);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.isi.setText(Html.fromHtml(isi,Html.FROM_HTML_MODE_LEGACY));
                }else {
                    holder.isi.setText(Html.fromHtml(isi));
                }
                if(!TextUtils.isEmpty(data.get(indexPosition).getImage())){
                    Picasso.get().load(data.get(indexPosition).getImage())
                            .placeholder(R.drawable.image_loader)
                            .error(R.drawable.image_error)
                            .into(holder.icon);
                } else holder.icon.setVisibility(View.GONE);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent newIntent=new Intent(v.getContext(), DetailActivity.class);
                        newIntent.putExtra(DetailActivity.KEY_TARGET_ID,id);
                        newIntent.putExtra(DetailActivity.KEY_TITLE,title);
                        newIntent.putExtra(DetailActivity.KEY_DATE,tanggal);
                        newIntent.putExtra(DetailActivity.KEY_URL_IMAGE,imageUrl);
                        newIntent.putExtra(DetailActivity.KEY_ACTION,"post");

                        v.getContext().startActivity(newIntent);
                        ((AppCompatActivity) v.getContext()).overridePendingTransition(R.anim.enter, R.anim.exit);
                    }
                });
                break;
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public int getItemViewType(int position) {
        if(data.get(position) == null && isLoading){
            return TYPE_LOADING;
        }else {
            return TYPE_ITEM;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView isi;
        public TextView tanggal;
        public ImageView icon;
        public MyViewHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.tvTitle);
            isi=itemView.findViewById(R.id.tvIsi);
            tanggal=itemView.findViewById(R.id.tvTanggal);
            icon=itemView.findViewById(R.id.imageView);
        }
    }
}