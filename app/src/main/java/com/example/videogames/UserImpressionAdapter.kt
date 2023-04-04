package com.example.videogames

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class UserImpressionAdapter(private var impressions: List<UserImpression>
) : RecyclerView.Adapter<UserImpressionAdapter.ImpressionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImpressionViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_impression, parent, false)
        return ImpressionViewHolder(view)
    }

    override fun getItemCount(): Int = impressions.size

    override fun onBindViewHolder(holder: ImpressionViewHolder, position: Int) {
        val impression = impressions[position]

        holder.gameUser.text = impression.username

        if (impression is UserRating) {
            holder.gameRating.rating = impression.rating.toFloat()
            holder.gameRating.visibility = View.VISIBLE
            holder.gameReview.visibility = View.GONE
        } else if ((impression is UserReview)){
            holder.gameReview.text = impression.review
            holder.gameRating.visibility = View.GONE
            holder.gameReview.visibility = View.VISIBLE
        }
    }

    fun updateImpressions(impressions: List<UserImpression>) {
        this.impressions = impressions
        notifyDataSetChanged()
    }

    inner class ImpressionViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        val gameUser: TextView = itemView.findViewById(R.id.username_textview)
        val gameRating : RatingBar = itemView.findViewById(R.id.rating_bar)
        val gameReview : TextView = itemView.findViewById(R.id.review_textview)
    }
}