package com.example.videogames

import android.content.pm.ActivityInfo
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.PositionAssertions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.UiDevice
import org.hamcrest.CoreMatchers.allOf
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OwnEspressoTests {


    /* U ovom scenariju testira se dizajn i performanse aplikacije u Portrait orijentaciji.
    * Najprije provjeravamo raspored elemenata u Home fragmentu (koji se prikazuje pri pokretanju aplikacije u Portrait orijentaciji),
    * zatim raspored i tacnosti prikazanih podataka konkretno za igricu pod rednim brojem 1 (na home su prikazani title, release date i rating).
    * Provjeravamo da li je bottom navigation onemogućen. Zatim ispitujemo regularnost prelazenja iz jednog u drugi fragment, nakon klika na
    * pomenutu igricu. Provjeravamo novi raspored elemenata u GameDetails fragmentu, zatim se navigacijom (dio homeItem) vraćamo na home, te se
    * navigacijom (dio detailsItem) vracamo na detalje, gdje provjeravamo da li su ispisani detalji za igricu koja je posljednja otvorena,
    * u konkrentnom slucaju za igricu pod rednim brojem 1.
    * */

    private lateinit var device: UiDevice

    @get:Rule
    var activityRule : ActivityScenarioRule<HomeActivity> = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setToPortrait() {
        activityRule.scenario.onActivity { activity ->activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT }
    }
    fun setToLandscape() {
        activityRule.scenario.onActivity { activity ->activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE }
    }

    @Test
    fun prviScenario(){
        setToPortrait()
        onView(withId(R.id.game_list)).check(isCompletelyBelow(withId(R.id.search_query_edittext)))
        onView(withId(R.id.game_list)).check(isCompletelyAbove(withId(R.id.bottom_nav)))
        onView(withId(R.id.search_button)).check(isTopAlignedWith(withId(R.id.search_query_edittext)))

        var igra = GameData.getAll().get(1)
        onView(withText(igra.title)).check(matches(isCompletelyDisplayed()))
        onView(withText(igra.releaseDate)).check(matches(isCompletelyDisplayed()))
        onView(withText(igra.rating.toString())).check(matches(isCompletelyDisplayed()))

        onView(withId(R.id.gameDetailsItem)).check { view, _ ->
            assertFalse("gameDatailsItem treba biti onemogućen", view.isEnabled)
        }
        onView(withId(R.id.homeItem)).check { view, _ ->
            assertFalse("homeItem treba biti onemogućen", view.isEnabled)
        }

        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<ViewHolder>(allOf(
            hasDescendant(withText(igra.title)),
            hasDescendant(withText(igra.releaseDate)),
            hasDescendant(withText(igra.rating.toString()))
        ),click()))
        onView(withId(R.id.item_title_textview)).check(isCompletelyAbove(withId(R.id.detailsLinear)))
        onView(withId(R.id.headingAbout)).check(isCompletelyAbove(withId(R.id.headingDesc)))
        onView(withId(R.id.user_impression_list)).check(isCompletelyBelow(withId(R.id.detailsLinear)))
        onView(withId(R.id.detailsLinear)).check(isCompletelyAbove(withId(R.id.bottom_nav)))

        onView(withId(R.id.homeItem)).perform(click())
        onView(withId(R.id.gameDetailsItem)).perform(click())
        onView(withText(igra.genre)).check(matches(isCompletelyDisplayed()))
        onView(withText(igra.esrbRating)).check(matches(isCompletelyDisplayed()))
        onView(withText(igra.developer)).check(matches(isCompletelyDisplayed()))
        onView(withText(igra.description)).check(matches(isCompletelyDisplayed()))
        onView(withText(igra.publisher)).check(matches(isCompletelyDisplayed()))
    }


    /* U ovom scenariju testira se dizajn i performanse aplikacije u Landscape orijentaciji.
    * Najprije provjeravamo raspored elemenata u Home fragmentu (koji se prikazuje na lijevoj strani ekrana pri Landscape orijentaciji),
    * u GameDetails fragmentu (koji se prikazuje na desnoj strani ekrana pri Landscape orijentaciji), te njihov medjusobni odnos.
    * Zatim ispitujemo raspored i tacnost prikazanih podataka konkretno za igricu pod rednim brojem 6. Testiramo podatke specificne za pojedine
    * fragmente, to je npr. rating za Home i genre i esrbRating za GamesDetail). Podatke za GamesDetail provjeravamo nakon klika na pomenutu igricu.
    * Uzeta je igrica pod rednim brojem 6 kako bismo morali skrolati, kako Home tako i DetailsFragment, da bismo provjerili tacnost podataka.
    * */

    @Test
    fun drugiScenario(){
        setToLandscape()
        onView(withId(R.id.game_list)).check(isCompletelyBelow(withId(R.id.search_query_edittext)))
        onView(withId(R.id.headingAbout)).check(isCompletelyAbove(withId(R.id.headingDesc)))
        onView(withId(R.id.user_impression_list)).check(isCompletelyBelow(withId(R.id.detailsLinear)))
        onView(withId(R.id.search_button)).check(isCompletelyLeftOf(withId(R.id.detailsLinear)))
        onView(withId(R.id.game_list)).check(isCompletelyLeftOf(withId(R.id.user_impression_list)))

        var igra = GameData.getAll().get(5)
        onView(allOf(withId(R.id.game_list))).perform(swipeUp())
        onView(withText(igra.rating.toString())).check(matches(isCompletelyDisplayed()))

        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<ViewHolder>(allOf(
            hasDescendant(withText(igra.title)),
            hasDescendant(withText(igra.releaseDate)),
            hasDescendant(withText(igra.rating.toString()))
        ),click()))

        onView(allOf(withId(R.id.detailsLinear))).perform(scrollTo(), click())
        onView(withText(igra.genre)).check(matches(isCompletelyDisplayed()))
        onView(withText(igra.esrbRating)).check(matches(isCompletelyDisplayed()))
    }

    /* U ovom scenariju testira se prelazak iz Portrait u Landscape orijentaciju i obratno.
    * Predvidjeno je da se prilikom svakog prelaska iz jedne orijentacije u drugu prikaze stanje koje se dobije prilikom startanja aplikacije u pojedinim
    * orijentacijama. To znaci da prilikom prelaska u Landscape orijentaciju u desnom dijelu ekrana prikazu detalji prve igrice, bez obzira na prethodno
    * stanje u Portrait orijetaciji. Takodjer, prilikom prelaska u Portrait orijentaciju ne uvazavamo prethodno stanje u Landscape orijentaciji, vec prikazujemo
    * listu igrica, kao da prethodno niti jedna igrica nije bila selektovana. Testiranje istog postizemo time sto prvobitno startamo aplikaciju u
    * Portrait orijentaciji i selektujemo igricu pod rednim brojem 2, zatim predjemo u Landscape i provjeravamo da li su prikazani detalji prve igrice, a ne one
    * koja je prethodno selektovana. Zatim prelazimo u Portrait orijentaciju te provjeravamo da li je u pocetnom stanju. To postizemo provjerom onemogucenosti navigacije.
    * */

    @Test
    fun treciScenario() {
        setToPortrait()
        var igraPort = GameData.getAll().get(2)
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<ViewHolder>(allOf(
            hasDescendant(withText(igraPort.title)),
            hasDescendant(withText(igraPort.releaseDate)),
            hasDescendant(withText(igraPort.rating.toString()))
        ),click()))

        setToLandscape()
        var igraLand = GameData.getAll().get(0)
        onView(allOf(withId(R.id.detailsLinear))).perform(scrollTo(), click())
        onView(withText(igraLand.genre)).check(matches(isCompletelyDisplayed()))
        onView(withText(igraLand.esrbRating)).check(matches(isCompletelyDisplayed()))

        setToPortrait()
        igraPort = GameData.getAll().get(0)
        onView(withText(igraPort.title)).check(matches(isCompletelyDisplayed()))

        onView(withId(R.id.gameDetailsItem)).check { view, _ ->
            assertFalse("gameDatailsItem treba biti onemogućen", view.isEnabled)
        }
        onView(withId(R.id.homeItem)).check { view, _ ->
            assertFalse("homeItem treba biti onemogućen", view.isEnabled)
        }
    }
}