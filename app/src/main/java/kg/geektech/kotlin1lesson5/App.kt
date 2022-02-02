package kg.geektech.kotlin1lesson5

import android.app.Application
import kg.geektech.kotlin1lesson5.data.remote.ApiService
import kg.geektech.kotlin1lesson5.core.network.RetrofitApiClient
import kg.geektech.kotlin1lesson5.repository.Repository

class App : Application() {

    companion object {
        val repository: Repository by lazy {
            Repository()
        }

        val apiService: ApiService by lazy {
            RetrofitApiClient.create()
        }
    }
}