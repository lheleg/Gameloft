package ba.etf.rma23.projekat

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import ba.etf.rma23.projekat.data.repositories.GetSwaggerResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var games: RecyclerView
    private lateinit var gamesAdapter: GameListAdapter

    private lateinit var nav: BottomNavigationView
    private lateinit var homeItem: MenuItem
    private lateinit var detailsItem: MenuItem
    private lateinit var searchText: EditText
    private lateinit var searchButton: AppCompatImageButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_home, container, false)
        games = view.findViewById(R.id.game_list)
        searchText = view.findViewById(R.id.search_query_edittext)
        arguments?.getString("search")?.let {
            searchText.setText(it)
        }
        searchButton = view.findViewById(R.id.search_button)
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
        searchButton.setOnClickListener{
            onClick();
        }
        getFavorites()
        gamesAdapter = GameListAdapter(arrayListOf()) { game -> showGameDetails(game) }
        games.adapter = gamesAdapter
        return view
    }
    private fun onClick() {
        val toast = Toast.makeText(context, "Search start", Toast.LENGTH_SHORT)
        toast.show()
        search(searchText.text.toString())
    }
    fun getFavorites(){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        // Create a new coroutine on the UI thread
        scope.launch{
            // Opcija 1
            val result = AccountGamesRepository.getSavedGames()
            // Display result of the network request to the user
            when (result) {
                is List<Game> -> onSuccess1(result)
                else-> onError1()
            }
        }
    }

    fun onSuccess1(games : List<Game>){
        val toast = Toast.makeText(context, "Favorite done", Toast.LENGTH_SHORT)
        toast.show()
        gamesAdapter.updateGames(games)
    }
    fun onError1() {
        val toast = Toast.makeText(context, "Favorite error", Toast.LENGTH_SHORT)
        toast.show()
    }
    fun search(query : String){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        // Create a new coroutine on the UI thread
        scope.launch{
            // Opcija 1
            val result = GamesRepository.getGamesByName(query)
            // Display result of the network request to the user
            when (result) {
                is List<Game> -> onSuccess(result)
                else-> onError()
            }
        }
    }
    fun onSuccess(games : List<Game>){
        val toast = Toast.makeText(context, "Search done", Toast.LENGTH_SHORT)
        toast.show()
        gamesAdapter.updateGames(games)
    }
    fun onError() {
        val toast = Toast.makeText(context, "Search error", Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun showGameDetails(game: Game) {
        val bundle = Bundle().apply {
            putString("title", searchText.text.toString())
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