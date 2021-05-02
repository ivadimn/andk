package info.fandroid.chat.ui.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import info.fandroid.chat.R
import info.fandroid.chat.cache.AccountCacheImpl
import info.fandroid.chat.cache.SharedPrefsManager
import info.fandroid.chat.data.account.AccountRepositoryImpl
import info.fandroid.chat.domain.account.AccountRepository
import info.fandroid.chat.domain.account.Register
import info.fandroid.chat.remote.account.AccountRemoteImpl
import info.fandroid.chat.remote.core.NetworkHandler
import info.fandroid.chat.remote.core.Request
import info.fandroid.chat.remote.service.ServiceFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPrefs = this.getSharedPreferences(this.packageName, Context.MODE_PRIVATE)

        val accountCache = AccountCacheImpl(SharedPrefsManager(sharedPrefs))
        val accountRemote = AccountRemoteImpl(Request(NetworkHandler(this)), ServiceFactory.makeService(true))

        val accountRepository: AccountRepository = AccountRepositoryImpl(accountRemote, accountCache)

        accountCache.saveToken("12345")

        val register = Register(accountRepository)
        register(Register.Params("abcd@m.com", "abcd", "123")) {
            it.either({
                Toast.makeText(this, it.javaClass.simpleName, Toast.LENGTH_SHORT).show()
            }, {
                Toast.makeText(this, "Аккаунт создан", Toast.LENGTH_SHORT).show()
            })
        }
    }
}
