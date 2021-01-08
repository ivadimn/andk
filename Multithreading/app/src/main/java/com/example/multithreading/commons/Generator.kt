package com.example.multithreading.commons


import kotlin.random.Random

object  Generator {
    private val namesActor = listOf(
        "Anjelina Joly",
        "Bred Pitt",
        "Kit Harington",
        "Nikolas Keidg",
        "Sharon Stoun",
        "Jan Klod van Damme",
        "Silvestr Stallone",
        "Bruce Willis"
    )

    private val namesCar = listOf(
        "BMW X5",
        "Mersedes Bens S500",
        "Porshe Caien",
        "Kia Sorento Prime",
        "Audi Q5",
        "Kia Optima",
        "Shcoda Codiaqu",
        "Volkswagen Toureg"
    )

    private val countries = listOf(
        "Italy",
        "Germany",
        "USA",
        "England",
        "Spain",
        "France",
        "Japan",
    )

    private val films = listOf(
        "Основной инстинкт",
        "Мистер и миссис Смит",
        "Схватка",
        "Игра престолов",
        "Крепкий орешек",
        "Хороший, плохой, злой",
        "Крестный отец",
        "Вид на убийство"
    )

    private val carImages = listOf(
        "https://avatarko.ru/img/avatar/29/avto_28145.jpg",
        "https://avatarko.ru/img/avatar/14/avto_13419.jpg",
        "https://avatarko.ru/img/avatar/16/avto_15028.jpg",
        "https://avatarko.ru/img/avatar/3/avto_2943.jpg",
        "https://avatarko.ru/img/avatar/28/avto_27389.jpg",
        "https://avatarko.ru/img/avatar/2/avto_tehnika_1564.jpg",
        "https://avatarko.ru/img/avatar/26/avto_25335.jpg",
        "https://avatarko.ru/img/avatar/25/avto_24487.jpg",
        "https://avatarko.ru/img/avatar/28/avto_27084.jpg",
        "https://avatarko.ru/img/avatar/9/avto_8980.jpg"
    )

    private val actorImages = listOf(
        "https://fs.kinomania.ru/image/file/person_photo/5/07/5076930fc7f10f77ed0bc7da5a541a91.240.301.jpeg",
        "https://fs.kinomania.ru/image/file/person_photo/2/dd/2dd42ed2fa8e919dddd1691f340db9ec.240.301.jpeg",
        "https://fs.kinomania.ru/image/file/person_photo/1/cc/1cc4515dfdd7f97d0d90eeca27e3cd44.240.301.jpeg",
        "https://fs.kinomania.ru/image/file/person_photo/8/69/86922294d7fbe61409b75ceab201c48d.240.301.jpeg",
        "https://fs.kinomania.ru/image/file/person_photo/9/45/945e8d3187baf64ac6aa36b3367683fd.240.301.jpeg",
        "https://fs.kinomania.ru/image/file/person_photo/6/7e/67eb74290cbcaefead1d1912048a91f3.240.301.jpeg",
        "https://fs.kinomania.ru/image/file/person_photo/a/b3/ab3d450d02b53ba44801d2b7373d302e.240.301.jpeg",
        "https://fs.kinomania.ru/image/file/person_photo/8/64/864c4c32632ecde7996523f16349d65c.240.301.jpeg",
        "https://fs.kinomania.ru/image/file/person_photo/1/ba/1ba2442e2779b9378ebd19ac95b67119.240.301.jpeg",
        "https://fs.kinomania.ru/image/file/person_photo/0/1a/01a2a6803aa99e5ef6b3bc390bfa9485.240.301.jpeg",
        "https://avatarko.ru/img/avatar/11/film_znamenitost_10607.jpg"
    )

    private val interiors = listOf(
        "https://images.unsplash.com/photo-1608118939885-ac618d5d101a?ixid=MXwxMjA3fDB8MHx0b3BpYy1mZWVkfDF8Ul9GeW4tR3d0bHd8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=60",
        "https://images.unsplash.com/photo-1600493504591-aa1849716b36?ixid=MXwxMjA3fDB8MHx0b3BpYy1mZWVkfDN8Ul9GeW4tR3d0bHd8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=60",
        "https://images.unsplash.com/photo-1572251774336-17ceaed13251?ixid=MXwxMjA3fDB8MHx0b3BpYy1mZWVkfDZ8Ul9GeW4tR3d0bHd8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=60",
        "https://images.unsplash.com/photo-1574284571463-e2094bedee23?ixid=MXwxMjA3fDB8MHx0b3BpYy1mZWVkfDV8Ul9GeW4tR3d0bHd8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=60",
        "https://images.unsplash.com/photo-1608118962146-e5def6d0e685?ixid=MXwxMjA3fDB8MHx0b3BpYy1mZWVkfDJ8Ul9GeW4tR3d0bHd8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=60",
        "https://images.unsplash.com/photo-1546967900-1bea5f16b69d?ixid=MXwxMjA3fDB8MHx0b3BpYy1mZWVkfDR8Ul9GeW4tR3d0bHd8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=60",
        "https://images.unsplash.com/photo-1589911057719-81914cd42d3d?ixid=MXwxMjA3fDB8MHx0b3BpYy1mZWVkfDl8Ul9GeW4tR3d0bHd8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=60",
        "https://images.unsplash.com/photo-1556908450-5b307b76debd?ixid=MXwxMjA3fDB8MHx0b3BpYy1mZWVkfDh8Ul9GeW4tR3d0bHd8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=60",
        "https://images.unsplash.com/photo-1601614516082-01a594263674?ixid=MXwxMjA3fDB8MHx0b3BpYy1mZWVkfDExfFJfRnluLUd3dGx3fHxlbnwwfHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=60"
    )

    private var id : Long = 0
    fun nextId() : Long = ++id

    fun getNameActor() : String = namesActor.random()
    fun getNameCar() : String = namesCar.random()
    fun getImageCar() : String = carImages.random()
    fun getImageActor() :String = actorImages.random()
    fun getCountry() : String = countries.random()
    fun getPower() : Int = Random.nextInt(100, 750)
    fun getMaxSpeed() : Int = Random.nextInt(180, 400)
    fun getBirthday() : String {
        val day = Random.nextInt(1, 30)
        val month = Random.nextInt(1, 12)
        val strDay = if (day < 10) "0$day" else "$day"
        val strMonth = if (month < 10) "0$month" else "$month"

        return "${strDay}.${strMonth}.${Random.nextInt(1935, 1991)}"
    }

    fun getFilm() : String = films.random()

    fun getImageItem() = interiors.random()


}