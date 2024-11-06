import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aventurape_androidmobile.domains.adventurer.models.Adventure
import com.example.aventurape_androidmobile.domains.adventurer.models.Comment
import com.example.aventurape_androidmobile.utils.RetrofitClient
import com.example.aventurape_androidmobile.utils.models.PublicationByOrderResponse
import com.example.aventurape_androidmobile.utils.models.PublicationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.util.Log

class StatisticsViewModel: ViewModel() {


    private val _userAdventures = MutableLiveData<List<Adventure>>()
    val userAdventures: LiveData<List<Adventure>> get() = _userAdventures

    private val _topAdventures = MutableLiveData<List<Adventure>>()
    val topAdventures: LiveData<List<Adventure>> get() = _topAdventures

    var comments: Map<Long, List<Comment>> by mutableStateOf(mapOf())
    var isLoadingTopAdventures by mutableStateOf(false)

    fun getCommentsForAdventure(adventureId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.placeholder.getComments(adventureId)
            withContext(Dispatchers.Main) {
                if (response.body() != null) {
                    comments = comments + (adventureId to response.body()!!)
                }
            }
        }
    }

    fun countCommentsForAdventure(adventureId: Long): Int {
        return comments[adventureId]?.size ?: 0
    }

    fun getUserAdventures(entrepreneurId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.placeholder.getPublications(entrepreneurId)
            withContext(Dispatchers.Main) {
                if (response.body() != null) {
                    val publicationResponses = response.body() as List<PublicationResponse>
                    val adventures = publicationResponses.map { publicationResponse ->
                        Adventure(
                            Id = publicationResponse.Id,
                            entrepreneurId = publicationResponse.entrepreneurId,
                            nameActivity = publicationResponse.nameActivity,
                            description = publicationResponse.description,
                            timeDuration = publicationResponse.timeDuration.toInt(),
                            image = publicationResponse.image,
                            cantPeople = publicationResponse.cantPeople,
                            cost = publicationResponse.cost.toDouble()
                        )
                    }
                    adventures.forEach { adventure ->
                        getCommentsForAdventure(adventure.Id)
                    }
                    val sortedAdventures = adventures.sortedByDescending { countCommentsForAdventure(it.Id) }
                    _userAdventures.value = sortedAdventures
                }
            }
        }
    }

    fun getTop5AdventuresByComments() {
        isLoadingTopAdventures = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.placeholder.getAllAdventures()
            withContext(Dispatchers.Main) {
                if (response.body() != null) {
                    val adventures = response.body() as ArrayList<Adventure>
                    adventures.forEach { adventure ->
                        getCommentsForAdventure(adventure.Id)
                    }
                    adventures.sortByDescending { countCommentsForAdventure(it.Id) }
                    _topAdventures.value = adventures.take(5)
                }
                isLoadingTopAdventures = false
            }
        }
    }
    private val _topRatedPublications = MutableLiveData<List<PublicationByOrderResponse>>()
    val topRatedPublications: LiveData<List<PublicationByOrderResponse>> get() = _topRatedPublications

    fun getFavoritePublicationsByProfileIdOrderedByRating(entrepreneurId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.placeholder.getFavoritePublicationsByProfileIdOrderedByRating(entrepreneurId)
            withContext(Dispatchers.Main) {
                if (response.body() != null) {
                    Log.d("API Response", response.body().toString())
                    _topRatedPublications.value = response.body()
                } else {
                    Log.d("API Response", "Response body is null")
                }
                if (!response.isSuccessful) {
                    Log.d("API Response", "Request failed with code: ${response.code()}")
                }
            }
        }
    }


}