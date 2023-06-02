package ba.etf.rma23.projekat

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GameDetailsFragment : Fragment() {
    private lateinit var game: Game
    private lateinit var title : TextView
    private lateinit var cover : ImageView
    private lateinit var platform : TextView
    private lateinit var realiseDate : TextView
    private lateinit var esrbRating : TextView
    private lateinit var developer : TextView
    private lateinit var publisher : TextView
    private lateinit var genre : TextView
    private lateinit var description : TextView
    private lateinit var impressions: RecyclerView
    private lateinit var impressionAdapter: UserImpressionAdapter
    private var impressionsList = listOf<UserImpression>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.activity_game_detail, container, false)
        title = view.findViewById(R.id.item_title_textview)
        platform = view.findViewById(R.id.platform_textview)
        realiseDate = view.findViewById(R.id.release_date)
        esrbRating = view.findViewById(R.id.esrb_rating_textview)
        developer = view.findViewById(R.id.developer_textview)
        publisher = view.findViewById(R.id.publisher_textview)
        genre = view.findViewById(R.id.genre_textview)
        description = view.findViewById(R.id.description_textview)
        cover = view.findViewById(R.id.cover_imageview)
        impressions = view.findViewById(R.id.user_impression_list)
        impressions.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        arguments?.getInt("title")?.let {
            getGame(it)
        }
        impressionAdapter = UserImpressionAdapter(arrayListOf())
        impressions.adapter = impressionAdapter
        impressionAdapter.updateImpressions(impressionsList)
        return view;
    }
    private fun populateDetails() {
        title.text = game.title
        platform.text = game.platform
        realiseDate.text = game.releaseDate
        esrbRating.text = game.esrbRating
        developer.text = game.developer
        publisher.text = game.publisher
        genre.text = game.genre
        description.text = game.description
        impressionsList = game.userImpressions?.sortedByDescending { it.timestamp } ?: emptyList()
        val context: Context = cover.getContext()
        var id = 0;
        Glide.with(context)
            .load("https:"+ game.coverImage)
            .placeholder(R.drawable.picture1)
            .error(id)
            .fallback(id)
            .into(cover);
    }

    private fun getGame(id : Int){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{
            val result = GamesRepository.getGameById(id)
            when (result) {
                is Game -> onSuccess(result)
                else-> onError()
            }
        }
    }

    fun onSuccess(game : Game){
        val toast = Toast.makeText(context, "id done", Toast.LENGTH_SHORT)
        toast.show()
        this.game = game
        populateDetails()
    }
    fun onError() {
        val toast = Toast.makeText(context, "Id error", Toast.LENGTH_SHORT)
        toast.show()
    }
}