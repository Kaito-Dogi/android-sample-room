package app.doggy.roomsample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(
    private val context: Context,
    //private var listener: OnItemClickListener
): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val items: MutableList<User> = mutableListOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val firstNameText: TextView = view.findViewById(R.id.first_name_text)
        val lastNameText: TextView = view.findViewById(R.id.last_name_text)
        val ageText: TextView = view.findViewById(R.id.age_text)
//        val container: ConstraintLayout = view.findViewById(R.id.container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cell_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.firstNameText.text = item.firstName
        holder.lastNameText.text = item.lastName
        holder.ageText.text = item.age.toString()

//        holder.container.setOnClickListener {
//            listener.onItemClick(item)
//        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    //引数にとったデータをリストに追加
    fun addAll(items: MutableList<User>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

//    interface OnItemClickListener {
//        fun onItemClick(item: User)
//    }
}