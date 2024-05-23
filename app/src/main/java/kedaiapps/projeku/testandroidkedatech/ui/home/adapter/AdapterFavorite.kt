package kedaiapps.projeku.testandroidkedatech.ui.home.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kedaiapps.projeku.testandroidkedatech.R
import kedaiapps.projeku.testandroidkedatech.databinding.ItemHomeBinding
import kedaiapps.projeku.testandroidkedatech.db.table.FavoriteTable
import kedaiapps.projeku.testandroidkedatech.ext.inflate

class AdapterFavorite (
    private val onClick: (FavoriteTable) -> Unit
) : RecyclerView.Adapter<AdapterFavorite.ViewHolder>() {

    var items: MutableList<FavoriteTable> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_home))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as? ViewHolder)?.bind(items.getOrNull(position)!!)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView){
        private var binding = ItemHomeBinding.bind(containerView)

        fun bind(data: FavoriteTable){
            with(binding){

                Glide.with(itemView.rootView).load(data.background_image)
                    .apply(
                        RequestOptions()
                            .transform(RoundedCorners(16))
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .dontAnimate()
                    ).into(binding.image)

                binding.judul.text = data.name
                binding.date.text = "Release date "+data.released
                binding.rating.text = data.rating

                line.setOnClickListener {
                    onClick(data)
                }
            }
        }
    }

    fun insertData(data : List<FavoriteTable>){
        data.forEach {
            items.add(it)
            notifyDataSetChanged()
        }
    }

    fun clearData() {
        if (items.isNotEmpty()) {
            items.clear()
            notifyDataSetChanged()
        }
    }
}