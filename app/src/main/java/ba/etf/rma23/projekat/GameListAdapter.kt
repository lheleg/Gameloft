package ba.etf.rma23.projekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.Math.round

class GameListAdapter (
    private var games: List<Game>,
    private val onItemClicked: (game: Game) -> Unit
    ) : RecyclerView.Adapter<GameListAdapter.GameViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_game, parent, false)
        return GameViewHolder(view)
    }
    override fun getItemCount(): Int = games.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.gameTitle.text = games[position].title;
        holder.gameRating.text = games[position].rating?.let { round(it).toString() };
        holder.gamePlatform.text = games[position].platform
        holder.gameRealiseDate.text = games[position].releaseDate;
        holder.itemView.setOnClickListener{ onItemClicked(games[position]) }
    }
    fun updateGames(games: List<Game>) {
        this.games = games
        notifyDataSetChanged()
    }
    inner class GameViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        val gameTitle: TextView = itemView.findViewById(R.id.item_title_textview)
        val gameRating : TextView = itemView.findViewById(R.id.game_rating_textview)
        val gamePlatform : TextView = itemView.findViewById(R.id.game_platform_textview)
        val gameRealiseDate : TextView = itemView.findViewById(R.id.release_date)
    }
}