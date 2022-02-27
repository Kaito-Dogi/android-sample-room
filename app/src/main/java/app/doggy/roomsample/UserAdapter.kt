package app.doggy.roomsample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.doggy.roomsample.databinding.UserListItemBinding

class UserAdapter(
    private var clickListener: OnItemClickListener,
    private var longClickListener: OnItemLongClickListener,
): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val items: MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = items[position]
        holder.binding.firstNameText.text = item.firstName
        holder.binding.lastNameText.text = item.lastName
        holder.binding.ageText.text = item.age.toString()

        holder.binding.container.setOnClickListener {
            clickListener.onItemClick(item)
        }

        holder.binding.container.setOnLongClickListener {
            longClickListener.onItemLongClick(item)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class UserViewHolder(val binding: UserListItemBinding) : RecyclerView.ViewHolder(binding.root)

    //引数にとったデータをリストに追加
    fun addAll(items: MutableList<User>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(item: User)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(item: User)
    }
}
