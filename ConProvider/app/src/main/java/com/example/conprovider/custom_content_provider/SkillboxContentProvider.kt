package com.example.conprovider.custom_content_provider

import android.content.*
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.example.conprovider.BuildConfig
import com.squareup.moshi.Moshi

class SkillboxContentProvider : ContentProvider() {

    private lateinit var userPrefs : SharedPreferences
    private lateinit var coursesPrefs : SharedPreferences

    private val userAdapter = Moshi.Builder().build().adapter(User::class.java)
    private val courseAdapter = Moshi.Builder().build().adapter(Course::class.java)

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITIES, PATH_USERS, TYPE_USERS)
        addURI(AUTHORITIES, PATH_COURSES, TYPE_COURSES)
        addURI(AUTHORITIES, "${PATH_USERS}/#", TYPE_USERS_ID)
        addURI(AUTHORITIES, "${PATH_COURSES}/#", TYPE_COURSES_ID)

    }

    override fun onCreate(): Boolean {
        userPrefs = context!!.getSharedPreferences("user_shared_prefs", Context.MODE_PRIVATE)
        coursesPrefs = context!!.getSharedPreferences("courses_shared_prefs", Context.MODE_PRIVATE)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when(uriMatcher.match(uri)) {
            TYPE_USERS -> { getAllUsersCursor()}
            //TYPE_COURSES -> {}
            //TYPE_USERS_ID -> {}
            //TYPE_COURSES_ID -> {}
            else -> null
        }
    }

    private fun getAllUsersCursor() : Cursor {
        val users = userPrefs.all.mapNotNull {
            val userJsonString = it.value as String
            userAdapter.fromJson(userJsonString)
        }
        val cursor = MatrixCursor(arrayOf(COLUMN_USER_ID, COLUMN_USER_NAME, COLUMN_USER_AGE))
        users.forEach {
            cursor.newRow()
                .add(it.id)
                .add(it.name)
                .add(it.age)
            //cursor.addRow(arrayOf(it.id, it.name, it.age))
        }
        return cursor
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        values ?: return null
        return when(uriMatcher.match(uri)) {
            TYPE_USERS -> saveUser(values)
            else -> null
        }
    }

    private fun saveUser(values: ContentValues) : Uri? {
        val id = values.getAsLong(COLUMN_USER_ID) ?: return null
        val name = values.getAsString(COLUMN_USER_NAME) ?: return null
        val age = values.getAsInteger(COLUMN_USER_AGE) ?: return null
        val user = User(id, name, age)
        userPrefs.edit()
            .putString(id.toString(), userAdapter.toJson(user))
            .commit()

        return Uri.parse("content://$AUTHORITIES/$PATH_USERS/$id")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return when(uriMatcher.match(uri)) {
            TYPE_USERS_ID -> deleteUser(uri)
            else -> 0
        }
    }

    private fun deleteUser(uri: Uri) : Int {
        val id = uri.lastPathSegment?.toLongOrNull()?.toString() ?: return 0
        return if (userPrefs.contains(id)) {
            userPrefs.edit()
                .remove(id)
                .commit()
            1
        }
        else {
            0
        }
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        values ?: return 0
        return when(uriMatcher.match(uri)) {
            TYPE_USERS_ID -> updateUser(uri, values)
            else -> 0
        }
    }

    private fun updateUser(uri : Uri, values: ContentValues) : Int {
        val id = uri.lastPathSegment?.toLongOrNull()?.toString() ?: return 0
        return if (userPrefs.contains(id)) {
            saveUser(values)
            1
        }
        else {
            0
        }
    }

    companion object {
        private const val AUTHORITIES = "${BuildConfig.APPLICATION_ID}.provider"
        private const val PATH_USERS = "users"
        private const val PATH_COURSES = "courses"

        private const val TYPE_USERS = 1
        private const val TYPE_COURSES = 2
        private const val TYPE_USERS_ID = 3
        private const val TYPE_COURSES_ID = 4

        private const val COLUMN_USER_ID = "id"
        private const val COLUMN_USER_NAME = "name"
        private const val COLUMN_USER_AGE = "age"
    }
}