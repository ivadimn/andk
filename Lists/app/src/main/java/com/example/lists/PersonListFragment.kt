package com.example.lists

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import jp.wasabeef.recyclerview.animators.ScaleInAnimator

class PersonListFragment : Fragment(R.layout.fragment_user_list) {
    private var persons = listOf(
            Person.Developer(
                    name = "Анна Александрова",
                    avatarLink ="https://avatarko.ru/img/avatar/5/devushka_4066.jpg",
                    age = 25,
                    programmingLanguage = "C++, Python"
            ),
            Person.Developer(
                    name = "Иван Петров",
                    avatarLink ="https://avatarko.ru/img/avatar/13/film_muzhchina_12224.jpg",
                    age = 35,
                    programmingLanguage = "Java, Kotlin"
            ),
            Person.User(
                    name = "Андрей Фёдоров",
                    avatarLink ="https://avatarko.ru/img/avatar/11/film_muzhchina_10363.jpg",
                    age = 35
            ),
            Person.Developer(
                    name = "Елена Сергеевна",
                    avatarLink ="https://avatarko.ru/img/avatar/6/devushka_5125.jpg",
                    age = 25,
                    programmingLanguage = "JavaScript, PHP"
            ),
            Person.User(
                    name = "Сергей Шилов",
                    avatarLink ="https://avatarko.ru/img/avatar/7/znamenitost_6306.jpg",
                    age = 35,
            )
    )
    private lateinit var userList : RecyclerView
    private lateinit var fab : FloatingActionButton

    private var personAdapter : PersonAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userList = requireView().findViewById(R.id.userList)
        fab = requireView().findViewById(R.id.addFab)
        fab.setOnClickListener {
            addPerson()
        }
        initList()
        personAdapter?.updatePersons(persons)
        personAdapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        personAdapter = null
    }

    private fun initList() {
        personAdapter = PersonAdapter({ position -> deletePerson(position) })
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
        personAdapter?.notifyItemRemoved(position)
    }

    private fun addPerson() {
        val person = persons.random()
        persons = listOf(person) + persons
        personAdapter?.updatePersons(persons)
        personAdapter?.notifyItemInserted(0)
        userList.scrollToPosition(0)
    }

    private fun toast(msg : String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

}