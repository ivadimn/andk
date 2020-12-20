package com.example.lists

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.random.Random

class UserListFragment : Fragment(R.layout.fragment_user_list) {
    private var users  = listOf(
            Person.User(
                id = 1,
                    name = "Анна Александрова",
                    avatarLink ="https://avatarko.ru/img/avatar/5/devushka_4066.jpg",
                    age = 25,
                    //isDeveloper=true
            ),
            Person.User(
                id = 2,
                    name = "Иван Петров",
                    avatarLink ="https://avatarko.ru/img/avatar/13/film_muzhchina_12224.jpg",
                    age = 35,
                   // isDeveloper=true
            ),
            Person.User(
                id = 3,
                    name = "Андрей Фёдоров",
                    avatarLink ="https://avatarko.ru/img/avatar/11/film_muzhchina_10363.jpg",
                    age = 35,
                   // isDeveloper=false
            ),
            Person.User(
                id = 4,
                    name = "Елена Сергеевна",
                    avatarLink ="https://avatarko.ru/img/avatar/6/devushka_5125.jpg",
                    age = 25,
                   // isDeveloper=true
            ),
            Person.User(
                id = 5,
                    name = "Сергей Шилов",
                    avatarLink ="https://avatarko.ru/img/avatar/7/znamenitost_6306.jpg",
                    age = 35,
                    //isDeveloper=false
            ),

            )
    private lateinit var userList : RecyclerView
    private lateinit var fab : FloatingActionButton

    private var userAdapter : UserAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userList = requireView().findViewById(R.id.userList)
        fab = requireView().findViewById(R.id.addFab)
        fab.setOnClickListener {
            addUser()
        }
        initList()
        userAdapter?.updateUsers(users)
        userAdapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        userAdapter = null
    }

    private fun initList() {
        userAdapter = UserAdapter({position -> deleteUser(position) })
        with(userList) {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun deleteUser(position : Int) {
        users = users.filterIndexed{ index, user -> index != position }
        userAdapter?.updateUsers(users)
        userAdapter?.notifyItemRemoved(position)
    }

    private fun addUser() {
        val user = users.random()
        users = listOf(user) + users
        userAdapter?.updateUsers(users)
        userAdapter?.notifyItemInserted(0)
        userList.scrollToPosition(0)
    }

    private fun toast(msg : String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }


}