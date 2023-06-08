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
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.getAge
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.setAge
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*

class HomeFragment : Fragment() {
    private lateinit var games: RecyclerView
    private lateinit var gamesAdapter: GameListAdapter
    private lateinit var gameList: List<Game>
    private lateinit var nav: BottomNavigationView
    private lateinit var homeItem: MenuItem
    private lateinit var detailsItem: MenuItem
    private lateinit var searchText: EditText
    private lateinit var searchButton: AppCompatImageButton
    private lateinit var searchFavoritesButton: AppCompatImageButton
    private lateinit var ageButton: AppCompatImageButton
    private lateinit var safeButton: AppCompatImageButton
    private lateinit var sortButton: AppCompatImageButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_home, container, false)
        games = view.findViewById(R.id.game_list)
        searchText = view.findViewById(R.id.search_query_edittext)
        arguments?.getString("search")?.let {
            searchText.setText(it)
        }
        searchButton = view.findViewById(R.id.search_button)
        searchFavoritesButton = view.findViewById(R.id.search_favorites_button)
        ageButton = view.findViewById(R.id.age_button)
        safeButton = view.findViewById(R.id.safe_button)
        sortButton = view.findViewById(R.id.sort_button)
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
            onClick()
        }
        searchFavoritesButton.setOnClickListener{
            onClickFavorites()
        }
        ageButton.setOnClickListener{
            onClickSetAge()
        }
        safeButton.setOnClickListener{
            onClickSafety()
        }
        sortButton.setOnClickListener{
            onClickSort()
        }
        getFavorites()
        gamesAdapter = GameListAdapter(arrayListOf()) { game -> showGameDetails(game) }
        games.adapter = gamesAdapter
        return view
    }
    private fun onClick() {
        val toast = Toast.makeText(context, "Searching", Toast.LENGTH_SHORT)
        toast.show()
        search(searchText.text.toString())
    }
    private fun onClickFavorites() {
        val toast = Toast.makeText(context, "Searching favorites", Toast.LENGTH_SHORT)
        toast.show()
        searchFavorites(searchText.text.toString())
    }
    private fun onClickSetAge() {
        try {
            val ageSet = setAge(searchText.text.toString().toInt())
            if (!ageSet) throw Exception()
        }catch(e: Exception) {
            val toast = Toast.makeText(context, "Wrong input", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
    private fun onClickSafety() {
        val toast = Toast.makeText(context, "Removing nonsafe", Toast.LENGTH_SHORT)
        toast.show()
        removeNonsafeGames()
    }
    private fun onClickSort() {
        val toast = Toast.makeText(context, "Sorting", Toast.LENGTH_SHORT)
        toast.show()
        sortCurrentGames()
    }

    private fun sortCurrentGames(){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{
            val result = GamesRepository.sortGames()
            gamesAdapter.updateGames(result)
        }
    }
    private fun removeNonsafeGames(){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{
            val result = AccountGamesRepository.removeNonSafe()
            getFavorites()
        }
    }
    fun getFavorites() = CoroutineScope(Job() + Dispatchers.Main).launch {
        val result = AccountGamesRepository.getSavedGames()
        gameList = result
        gamesAdapter.updateGames(result)
    }

    fun search(query : String){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{
            val age = getAge()
            var result = emptyList<Game>()
            if (age != null) {
                if(age < 18)
                    result = GamesRepository.getGamesSafe(query)!!
                else if(age >= 18)
                    result = GamesRepository.getGamesByName(query)!!
            }
            gamesAdapter.updateGames(result)
        }
    }
    fun searchFavorites(query : String){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{
            val result = AccountGamesRepository.getGamesContainingString(query)
            gamesAdapter.updateGames(result)
        }
    }
    fun showGameDetails(game: Game) {
        val bundle = Bundle().apply {
            game.id?.let { putInt("title", it) }
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