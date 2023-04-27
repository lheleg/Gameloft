package com.example.videogames

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
        arguments?.getString("title")?.let {
            game = getGameByTitle(it)
            populateDetails()
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
        impressionsList = game.userImpressions.sortedByDescending { it.timestamp }
        val context: Context = cover.context
        var id: Int = context.resources
            .getIdentifier(game.coverImage, "drawable", context.packageName)
        if (id===0) id=context.resources
            .getIdentifier("picture1", "drawable", context.packageName)
        cover.setImageResource(id)
    }
    private fun getGameByTitle(name:String): Game{
        val games: ArrayList<Game> = arrayListOf()
        games.addAll(GameData.getAll())
        val game = GameData.getDetails(name)
        return game?: Game("Test","Test","Test",0.0,"Test","Test","Test", "Test", "Test", "Test", listOf())
    }
}