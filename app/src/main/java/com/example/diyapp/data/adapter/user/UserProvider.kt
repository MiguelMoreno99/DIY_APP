package com.example.diyapp.data.adapter.user

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserProvider @Inject constructor() {
    var userData: List<User> = emptyList()
}