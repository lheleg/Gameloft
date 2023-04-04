package com.example.videogames

import java.time.LocalDateTime

class GameData {
    companion object{
        fun getAll() : List<Game>{
            return listOf(
                Game("League of Legends", "Mac, Windows", "27.10.2009", 4.2, "league", "Everyone 10+", "Riot Games", "Riot Games", "Fantasy Violence",
                    "League of Legends is a fast-paced, competitive online game that blends the speed and intensity of an RTS with RPG elements. Two teams of powerful champions, each with a unique design and playstyle, battle head-to-head across multiple battlefields and game modes.",
                    listOf(UserRating("mori999",  LocalDateTime.of(2015, 1, 1, 12, 30, 0).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(), 5.0),
                        UserRating("lolo7",  LocalDateTime.of(2019, 10, 18, 12, 30, 0).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(), 4.0),
                        UserReview("_selena", LocalDateTime.of(2018, 7, 13, 15, 32, 22).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(), "Love it. Great for playing with friends."),
                        UserReview("samsmm", LocalDateTime.of(2019, 12, 11, 11, 32, 22).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(), "So much fun!!"),
                        UserRating("liliaa",  LocalDateTime.of(2020, 6, 23, 10, 30, 2).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(), 3.7),
                    )),
                Game("Minecraft", "Linux", "18.11.2011", 4.5, "minecraft", "Everyone 10+", "Mojang Studios", "Xbox Game Studios", "Adventure",
                    "Minecraft focuses on allowing the player to explore, interact with, and modify a dynamically-generated map made of one-cubic-meter-sized blocks. In addition to blocks, the environment features plants, mobs, and items.",
                    listOf(UserReview("leca44", LocalDateTime.of(2021, 9, 11, 12, 32, 22).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(), "Goooood<3"),
                        UserReview("martin55", LocalDateTime.of(2022, 12, 30, 11, 32, 22).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(), "Amazing game. Fun world, engaging story."),
                        UserRating("lola88",  LocalDateTime.of(2020, 1, 12, 10, 30, 2).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(), 3.5),
                        UserRating("sejo22",  LocalDateTime.of(2023, 1, 21, 10, 40, 0).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(), 2.5),
                        UserReview("__sele", LocalDateTime.of(2019, 8, 13, 15, 32, 22).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(), "So boring."))),
                Game("Fortnite", "Xbox", "25.7.2017", 4.0, "fortnite", "Teen", "Epic games", "Epic games", "Adventure",
                    "Fortnite is the completely free online game where you and your friends fight to be the last one standing in Battle Royale, join forces to make your own Creative games, or catch a live show at Party Royale.",
                    listOf(UserRating("leo123", LocalDateTime.of(2018, 5, 24, 10, 36, 0).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),4.1),
                        UserRating("12anna", LocalDateTime.of(2019, 6, 23, 10, 30, 0).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),4.7))),
                Game("KartRider: Drift", "Windows", "10.1.2023", 3.2, "kartrider", "Everyone", "NEXON Korea", "Nexon", "Racing",
                    "KartRider: Drift is the only free-to-play, cross-platform kart racer where arcade thrills and the fastest drift fueled competition meet deep player-created kart and character customization. No limits. All drift.",
                    listOf(UserRating("xxaustina",  LocalDateTime.of(2023, 1, 1, 10, 30, 0).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(), 4.5),
                        UserRating("hani23", LocalDateTime.of(2023, 2, 22, 15, 36, 55).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),3.1))),
                Game("The Sims", "Linux, Windows", "4.2.2000", 4.3, "sims", "Teen", "Maxis", "Electronic Arts", "Simulator",
                    "The Sims is a life-building simulation with emphasis on intimate details of individual characters rather than expansion of an entire society or civilization. You are in control of nearly every aspect of the lives of the individuals who make up your chosen population in the neighborhood.",
                    listOf(UserRating("mani__",  LocalDateTime.of(2001, 3, 1, 10, 30, 0).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(), 4.5),
                        UserReview("hani23", LocalDateTime.of(2016, 12, 2, 17, 36, 55).toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),"Best game. You can simulate anything that you want."))),
            )
        }

        fun getDetails(title:String): Game? {
            val list = getAll()
            for (item in list) {
                if (item.title == title) return item
            }
            return null
        }
    }
}