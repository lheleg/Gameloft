package com.example.videogames

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {
    private lateinit var games: RecyclerView
    private lateinit var gamesAdapter: GameListAdapter
    private var gamesList = GameData.getAll()
    private lateinit var nav: BottomNavigationView
    private lateinit var homeItem: MenuItem
    private lateinit var detailsItem: MenuItem

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_home, container, false)
        games = view.findViewById(R.id.game_list)
        games.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            nav = requireActivity().findViewById(R.id.bottom_nav)
            homeItem = nav.menu.findItem(R.id.homeItem)
            detailsItem = nav.menu.findItem(R.id.gameDetailsItem)
        }
        gamesAdapter = GameListAdapter(arrayListOf()) { game -> showGameDetails(game) }
        games.adapter = gamesAdapter
        gamesAdapter.updateGames(gamesList)
        return view
    }
    private fun showGameDetails(game: Game) {
        val bundle = Bundle().apply {
            putString("title", game.title)
        }
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            homeItem.isEnabled = true
            detailsItem.isEnabled = true

            nav.selectedItemId = R.id.gameDetailsItem
            navController.navigate(R.id.gameDetailsItem, bundle)
        }
        else if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            val navHostFragment1 = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_detail_fragment) as NavHostFragment
            val navController1 = navHostFragment1.navController
            navController1.navigate(R.id.gameDetailsItem, bundle)
        }
    }
}