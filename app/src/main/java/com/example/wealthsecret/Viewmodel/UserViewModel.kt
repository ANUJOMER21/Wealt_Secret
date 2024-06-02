package com.example.wealthsecret.Viewmodel
import kotlinx.coroutines.channels.Channel
import androidx.lifecycle.ViewModel
import com.example.wealthsecret.model.User
import com.google.firebase.database.*
import kotlinx.coroutines.flow.receiveAsFlow
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

sealed class UserDataState {
    data class Success(val user: User) : UserDataState()
    object Failure : UserDataState()
}
class UserViewModel : ViewModel() {

    private val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("users")
    }
    private val userDataStateChannel = Channel<UserDataState>()
    val userDataStateFlow = userDataStateChannel.receiveAsFlow()

    fun addUser(userId: String, user: User) {
        database.child(userId).setValue(user)
            .addOnSuccessListener {
                userDataStateChannel.trySend(UserDataState.Success(user)).isSuccess
            }
            .addOnFailureListener {
                userDataStateChannel.trySend(UserDataState.Failure).isSuccess
            }
    }
    suspend fun checkUserExists(userId: String): Boolean {
        return suspendCoroutine { continuation ->
            database.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    continuation.resume(dataSnapshot.exists())
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    continuation.resume(false)
                }
            })
        }
    }
    fun getUser(userId: String) {
        database.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                if (user != null) {
                    userDataStateChannel.trySend(UserDataState.Success(user)).isSuccess
                } else {
                    userDataStateChannel.trySend(UserDataState.Failure).isSuccess
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                userDataStateChannel.trySend(UserDataState.Failure).isSuccess
            }
        })
    }
}
