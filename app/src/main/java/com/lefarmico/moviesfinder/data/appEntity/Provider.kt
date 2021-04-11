package com.lefarmico.moviesfinder.data.appEntity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "providers")
data class Provider(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "provider_type")val providerType: ProviderType? = null,
    @ColumnInfo(name = "name")val name: String,
    @ColumnInfo(name = "provider_id")val providerId: Int,
    @ColumnInfo(name = "logoPath")val logoPath: String,
    @ColumnInfo(name = "display_priority")val displayPriority: Int,
    @ColumnInfo(name = "movie_details_id") val movieDetailsId: Int = 0
) {
    enum class ProviderType {
        FLATRATE, BUY, RENT
    }
}
