package com.example.videogames

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videogames.GameData.Companion.getAll
import com.example.videogames.GameData.Companion.getDetails

class GameDetailsActivity : AppCompatActivity() {
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
    private lateinit var homeButton : Button
    private lateinit var detailsButton : Button
    private lateinit var impressions: RecyclerView
    private lateinit var impressionAdapter: UserImpressionAdapter
    private var impressionsList = listOf<UserImpression>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_detail)
        title = findViewById(R.id.game_title_textview)
        platform = findViewById(R.id.platform_textview)
        realiseDate = findViewById(R.id.release_date)
        esrbRating = findViewById(R.id.esrb_rating_textview)
        developer = findViewById(R.id.developer_textview)
        publisher = findViewById(R.id.publisher_textview)
        genre = findViewById(R.id.genre_textview)
        description = findViewById(R.id.description_textview)
        homeButton = findViewById(R.id.home_button)
        detailsButton = findViewById(R.id.details_button)
        cover = findViewById(R.id.cover_imageview)
        impressions = findViewById(R.id.user_impression_list)
        impressions.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        val extras = intent.extras
        if (extras != null) {
            game = getGameByTitle(extras.getString("game_title_textview",""))
            populateDetails()
        } else {
            finish()
        }
        homeButton.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra("ENABLE", "YES")}
            startActivity(intent)
        }
        impressionAdapter = UserImpressionAdapter(arrayListOf())
        impressions.adapter = impressionAdapter
        impressionAdapter.updateImpressions(impressionsList)
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
        games.addAll(getAll())
        val game = getDetails(name)
        return game?: Game("Test","Test","Test",0.0,"Test","Test","Test", "Test", "Test", "Test", listOf())
    }
}