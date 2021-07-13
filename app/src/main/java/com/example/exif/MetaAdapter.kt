//import android.text.format.DateFormat
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.exif.model.Meta
//import io.realm.OrderedRealmCollection
//import io.realm.RealmRecyclerViewAdapter
//
//class MetaAdapter(data: OrderedRealmCollection<Meta>) :
//    RealmRecyclerViewAdapter<Meta, MetaAdapter.ViewHolder>(data, true) {
//
//    init {
//        setHasStableIds(true)
//    }
//
//    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val id: TextView? = null
//        var name: TextView? = null
//
//        init {
//            　//ビューホルダーの情報がレイアウトのどれと対応するか
//            　　id = view.findViewById(R.id.textview)
//            　　name = view.findViewById(R.id.textview2)
//        }
//
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
//                MetaAdapter.ViewHolder {
//            val inflater = LayoutInflater.from(parent.context)
//            val view = inflater.inflate(
//                android.R.layout.simple_list_item_2,
//                parent, false
//            )
//            return RecyclerView.ViewHolder(view)
//        }
//
//        override fun onBindViewHolder(holder: MetaAdapter.ViewHolder, position: Int) {
//            val schedule: Meta? = getItem(position)
//            holder.date.text = DateFormat.format("yyyy/MM/dd HH:mm", meta?.date)
//            holder.title.text = meta?.title
//            holder.itemView.setOnClickListener {
//                listener?.invoke(meta?.id)
//            }
//        }
//
//        override fun getItemId(position: Int): Long {
//            return getItem(position)?.id ?: 0
//        }
//    }