package com.example.viewmodel.model

import com.example.viewmodel.Generator
import kotlin.random.Random

class EntityRepository {


    fun addEntity(entity : Entity, entities : List<Entity>) : List<Entity> {
        return listOf(entity) + entities
    }

    fun deleteEntity(position : Int, entities : List<Entity>) : List<Entity> {
        return entities.filterIndexed {index, _ -> index != position}
    }


    fun generateEntities(num: Int) : List<Entity>{
        return (1 .. num).map {
            val id = Generator.nextId()
            val country = Generator.getCountry()
            val isActor = Random.nextBoolean()

            if (isActor) {
                Entity.Actor(
                    id = id,
                    name = Generator.getNameActor(),
                    imageLink = Generator.getImageActor(),
                    country = country,
                    birthday = Generator.getBirthday(),
                    films = Generator.getFilm()
                )
            }
            else {
                Entity.Car(
                    id = id,
                    name = Generator.getNameCar(),
                    imageLink = Generator.getImageCar(),
                    country = country,
                    power = Generator.getPower(),
                    maxSpeed = Generator.getMaxSpeed()
                )
            }
        }
    }
}