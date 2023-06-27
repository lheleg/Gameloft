package ba.etf.rma23.projekat

import android.app.PendingIntent.getActivity
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ba.etf.rma23.projekat.data.repositories.AppDatabase
import ba.etf.rma23.projekat.data.repositories.GameReview
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.main_home)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.homeItem)
            val navView: BottomNavigationView = findViewById(R.id.bottom_nav)
            navView.setupWithNavController(navController)
        }else if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.main_home)
            val game = emptyList<Game>()
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_home_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.homeItem)
            val navHostFragment1 =
                supportFragmentManager.findFragmentById(R.id.nav_detail_fragment) as NavHostFragment
            val navController1 = navHostFragment1.navController
            val bundle = Bundle().apply {
                putString("title", game[0].title)
            }
            navController1.navigate(R.id.gameDetailsItem, bundle)
        }

        //naredni dio je dodan jer se testovi nisu mogli pokrenuti ukoliko baza nije inicijalizovana, odnosno ukoliko je prazna
        var db = AppDatabase.getInstance(this)
        val gameReview = GameReview(3, null, 100)
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{
            db!!.gameReviewDao().insertGameReview(gameReview)
        }
    }
}
