package ba.etf.rma23.projekat

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GameReview
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository
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
    private var impressionsList = ArrayList<UserImpression>()
    private lateinit var save_button: ImageButton
    private lateinit var unsave_button: ImageButton
    private lateinit var ratingBar: RatingBar
    private lateinit var submitRating: Button
    private lateinit var reviewEditText: EditText

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
        save_button = view.findViewById(R.id.search_button)
        unsave_button = view.findViewById(R.id.unsave_button)
        ratingBar = view.findViewById(R.id.simple_rating_bar)
        submitRating = view.findViewById(R.id.button)
        reviewEditText = view.findViewById<EditText>(R.id.reviewEditText)
        impressions.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        arguments?.getInt("title")?.let {
            getGame(it)
        }
        save_button.setOnClickListener{
            saveThisGame(game)
        }
        unsave_button.setOnClickListener{
            unsaveThisGame(game)
        }

        var rating: Int? = null
        var review: String? = null

        submitRating?.setOnClickListener {
            rating = ratingBar.rating.toInt()
            review = reviewEditText.text.toString()
            if(review == "") review = null
            if(rating == 0) rating = null
            context?.let { it1 -> makeNewReview(it1,GameReview(rating,review,game.id!!)) }
        }

        return view;
    }
    private suspend fun populateDetails() {
        title.text = game.title
        platform.text = game.platform
        realiseDate.text = game.releaseDate
        esrbRating.text = game.esrbRating
        developer.text = game.developer
        publisher.text = game.publisher
        genre.text = game.genre
        description.text = game.description
        for (review in game.id?.let { GameReviewsRepository.getReviewsForGame(it) }!!){
            if (review.rating != null)
                impressionsList.add(UserRating(review.student!!,0,review.rating!!))
            if (review.review != null)
                impressionsList.add(UserReview(review.student!!,0, review.review!!))
        }
        val context: Context = cover.getContext()
        var id = 0;
        Glide.with(context)
            .load("https:"+ game.coverImage)
            .placeholder(R.drawable.picture1)
            .error(id)
            .fallback(id)
            .into(cover);
        impressionAdapter = UserImpressionAdapter(impressionsList)
        impressions.adapter = impressionAdapter
        impressionAdapter.updateImpressions(impressionsList)
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
    suspend fun onSuccess(game : Game){
        val toast = Toast.makeText(context, "id done", Toast.LENGTH_SHORT)
        toast.show()
        this.game = game
        populateDetails()
    }
    fun onError() {
        val toast = Toast.makeText(context, "Id error", Toast.LENGTH_SHORT)
        toast.show()
    }
    private fun saveThisGame(game: Game){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{
            val result = AccountGamesRepository.saveGame(game)
            if (result?.id == game?.id)
                onSuccess1()
            else onError1()
        }
    }
    fun onSuccess1(){
        val toast = Toast.makeText(context, "Game saved to favorites", Toast.LENGTH_SHORT)
        toast.show()
    }
    fun onError1() {
        val toast = Toast.makeText(context, "Game saving error", Toast.LENGTH_SHORT)
        toast.show()
    }
    private fun unsaveThisGame(game: Game){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{
            val result = AccountGamesRepository.removeGame(game?.id)
            if (result == true)
                onSuccess2()
            else onError2()
        }
    }
    fun onSuccess2(){
        val toast = Toast.makeText(context, "Game deleted from favorites", Toast.LENGTH_SHORT)
        toast.show()
    }
    fun onError2() {
        val toast = Toast.makeText(context, "Game deleting error", Toast.LENGTH_SHORT)
        toast.show()
    }
    private fun makeNewReview(context: Context,gameReview: GameReview){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{
            GameReviewsRepository.sendReview(context,gameReview)
        }
    }
}