package com.example.diyapp.data.adapter.favorites

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedFavoritesProvider @Inject constructor() {
    var feedFavoritesList: List<FeedFavorites> = emptyList()
}