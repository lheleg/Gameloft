package com.example.videogames

import android.R.id.button2
import android.R.id.button3
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videogames.GameData.Companion.getAll


class HomeActivity : AppCompatActivity() {
    private lateinit var games: RecyclerView
    private lateinit var gamesAdapter: GameListAdapter
    private lateinit var detailsButton: Button
    private var gamesList = getAll()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        games = findViewById(R.id.game_list)
        games.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        detailsButton = findViewById(R.id.details_button)
        detailsButton.setOnClickListener { finish() }
        gamesAdapter = GameListAdapter(arrayListOf()) { game -> showGameDetails(game) }
        games.adapter = gamesAdapter
        gamesAdapter.updateGames(gamesList)
        val extras = intent.extras
        if (extras != null) {
            detailsButton.isEnabled = true
        }
    }
    private fun showGameDetails(game: Game) {
        val intent = Intent(this, GameDetailsActivity::class.java).apply {
            putExtra("game_title_textview", game.title)
        }
        startActivity(intent)
    }
}