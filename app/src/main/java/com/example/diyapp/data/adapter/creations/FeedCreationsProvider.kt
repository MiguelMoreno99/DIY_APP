package com.example.diyapp.data.adapter.creations

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedCreationsProvider @Inject constructor() {
    var feedCreationsList: List<FeedCreations> = emptyList()
}