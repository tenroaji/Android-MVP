package lagecong.com.mvp.data.adapters

import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import lagecong.com.mvp.R
import lagecong.com.mvp.commons.ItemLoadingViewHolder
import lagecong.com.mvp.data.model.ModelArticleResponse
import java.util.*

class AdapterArticleS : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: MutableList<ModelArticleResponse>? = null
    private val mListData: List<*>? = null
    val dataEmpety :ModelArticleResponse? = null
    private var isLoading = false

    init {
        data = ArrayList()
    }

    fun updateAdapter(list: List<ModelArticleResponse>) {
        data = ArrayList(list)
        notifyDataSetChanged()
    }

    fun setLoading(show: Boolean) {
        if (show && !isLoading) {
            isLoading = true
            val position = data!!.size
            data!!.add(position, dataEmpety!!)
//            dataEmpety?.let { data!!.add(position, it) }
            notifyDataSetChanged()
        } else if (!show && isLoading) {
            isLoading = false
            val position = data!!.size - 1
            data!!.removeAt(position)
            notifyDataSetChanged()
        }
    }

    fun clearList() {
        //        boolean isHaveHead = false;
        //        if(data.size() > 0){
        //            isHaveHead = true;
        //        }
        data!!.clear()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_article, parent, false);
        //        return new AdapterArticle.MyViewHolder(mView);

        val mView: View
        val holder: RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            TYPE_LOADING -> {
                mView = inflater.inflate(R.layout.aaa_item_view_loader, parent, false)
                holder = ItemLoadingViewHolder(mView)
            }
            TYPE_ITEM -> {
                mView = inflater.inflate(R.layout.layout_list_article, parent, false)
                holder = MyViewHolder(mView)
            }
            else -> {
                mView = inflater.inflate(R.layout.layout_list_article, parent, false)
                holder = MyViewHolder(mView)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holderView: RecyclerView.ViewHolder, position: Int) {
        val type = getItemViewType(position)
        when (type) {

            TYPE_ITEM -> {
                val holder = holderView as MyViewHolder
                val title = data!![position].title
                val isi = data!![position].content
                val tanggal = data!![position].published
                val imageUrl = data!![position].image
                val id = "" + data!![position].id_target!!
                holder.tanggal.text = tanggal
                holder.title.text = title
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.isi.text = Html.fromHtml(isi, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    holder.isi.text = Html.fromHtml(isi)
                }
                if (!TextUtils.isEmpty(data!![position].image)) {
                    Picasso.get().load(data!![position].image)
                        .placeholder(R.drawable.image_loader)
                        .error(R.drawable.image_error)
                        .into(holder.icon)
                } else
                    holder.icon.visibility = View.GONE
            }
        }//                holder.itemView.setOnClickListener(new View.OnClickListener() {
        //                    @Override
        //                    public void onClick(View v) {
        //                        Intent newIntent=new Intent(v.getContext(),DetailActivity.class);
        //                        newIntent.putExtra(DetailActivity.KEY_TARGET_ID,id);
        //                        newIntent.putExtra(DetailActivity.KEY_TITLE,title);
        //                        newIntent.putExtra(DetailActivity.KEY_DATE,tanggal);
        //                        newIntent.putExtra(DetailActivity.KEY_URL_IMAGE,imageUrl);
        //                        newIntent.putExtra(DetailActivity.KEY_ACTION,MenuTypeUtil.TYPE_POSTS);
        //
        //                        v.getContext().startActivity(newIntent);
        //                        ((AppCompatActivity) v.getContext()).overridePendingTransition(R.anim.enter, R.anim.exit);
        //                    }
        //                });

    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (data!![position] == null && isLoading) {
            TYPE_LOADING
        } else {
            TYPE_ITEM
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var isi: TextView
        var tanggal: TextView
        var icon: ImageView

        init {
            title = itemView.findViewById(R.id.tvTitle)
            isi = itemView.findViewById(R.id.tvIsi)
            tanggal = itemView.findViewById(R.id.tvTanggal)
            icon = itemView.findViewById(R.id.imageView)
        }
    }

    companion object {
        private val TYPE_LOADING = 1
        private val TYPE_ITEM = 2
    }
}