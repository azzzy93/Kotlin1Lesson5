package kg.geektech.kotlin1lesson5.core.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val loading = MutableLiveData<Boolean>()

}