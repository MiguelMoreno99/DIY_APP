package com.example.diyapp.data.adapter.explore

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedExploreProvider @Inject constructor() {
    var feedExploreList: List<FeedExplore> = emptyList()
}