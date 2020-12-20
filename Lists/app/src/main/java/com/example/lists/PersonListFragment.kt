package com.example.lists

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlin.random.Random

class PersonListFragment : Fragment(R.layout.fragment_user_list) {
    private var persons = listOf(
            Person.Developer(
                id = 1,
                name = "Анна Александрова",
                avatarLink ="https://avatarko.ru/img/avatar/5/devushka_4066.jpg",
                age = 25,
                programmingLanguage = "C++, Python"
            ),
            Person.Developer(
                id = 2,
                name = "Иван Петров",
                avatarLink ="https://avatarko.ru/img/avatar/13/film_muzhchina_12224.jpg",
                age = 35,
                programmingLanguage = "Java, Kotlin"
            ),
            Person.User(
                id = 3,
                name = "Андрей Фёдоров",
                avatarLink ="https://avatarko.ru/img/avatar/11/film_muzhchina_10363.jpg",
                age = 35
            ),
            Person.Developer(
                id = 4,
                name = "Елена Сергеевна",
                avatarLink ="https://avatarko.ru/img/avatar/6/devushka_5125.jpg",
                age = 25,
                programmingLanguage = "JavaScript, PHP"
            ),
            Person.User(
                id = 5,
                name = "Сергей Шилов",
                avatarLink ="https://avatarko.ru/img/avatar/7/znamenitost_6306.jpg",
                age = 35,
            )
    )
    private lateinit var userList : RecyclerView
    private lateinit var fab : FloatingActionButton

    private var personAdapter : PersonListAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userList = requireView().findViewById(R.id.userList)
        fab = requireView().findViewById(R.id.addFab)
        fab.setOnClickListener {
            addPerson()
        }
        //persons = generatePersons()
        initList()
        personAdapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                toast("Adapter was changed")
                userList.scrollToPosition(0)
            }
        })
        personAdapter?.updatePersons(persons)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        personAdapter = null
    }

    private fun initList() {
        personAdapter = PersonListAdapter({ position -> deletePerson(position) })
        with(userList) {
            adapter = personAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            itemAnimator = ScaleInAnimator()
        }
    }

    private fun deletePerson(position : Int) {
        persons = persons.filterIndexed{ index, user -> index != position }
        personAdapter?.updatePersons(persons)
        //personAdapter?.notifyItemRemoved(position)
    }

    private fun addPerson() {
        val person = persons.random().let {
            when(it) {
                is Person.User -> it.copy(id = Random.nextLong())
                is Person.Developer -> it.copy(id = Random.nextLong())
            }
        }
        persons = listOf(person) + persons
        personAdapter?.updatePersons(persons)
        //personAdapter?.notifyItemInserted(0)
        //userList.scrollToPosition(0)
    }

    private fun toast(msg : String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun generatePersons() : List<Person>{
        val names = listOf(
            "Анна Александрова",
            "Иван Петров",
            "Андрей Фёдоров",
            "Елена Сергеевна",
            "Сергей Шилов",
        )

        val avatars = listOf(
            "https://avatarko.ru/img/avatar/5/devushka_4066.jpg",
            "https://avatarko.ru/img/avatar/13/film_muzhchina_12224.jpg",
            "https://avatarko.ru/img/avatar/11/film_muzhchina_10363.jpg",
            "https://avatarko.ru/img/avatar/7/znamenitost_6306.jpg",
            "https://avatarko.ru/img/avatar/6/devushka_5125.jpg",
        )

        val languages = listOf(
            "C++",
            "Java",
            "Kotlin",
            "Python",
            "JavaScript",
            "PHP"
        )

        return (1 .. 1000).map {
            val id = it.toLong()
            val name = names.random()
            val age = Random.nextInt(18, 55)
            val language = "${languages.random()},  ${languages.random()}"
            val avatar = avatars.random()
            val isDeveloper = Random.nextBoolean()

            if (isDeveloper) {
                Person.Developer(
                    id = id,
                    name = name,
                    avatarLink = avatar,
                    age = age,
                    programmingLanguage = language
                )
            }
            else {
                Person.User(
                    id = id,
                    name = name,
                    avatarLink = avatar,
                    age = age
                )
            }
        }
    }

}