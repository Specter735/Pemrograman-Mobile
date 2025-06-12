package com.example.zennfit.model

import com.google.gson.annotations.SerializedName
import android.text.Html
import android.os.Build

data class ExerciseInfo(
    @SerializedName("id") val id: Int,
    @SerializedName("uuid") val uuid: String,
    @SerializedName("created") val created: String?,
    @SerializedName("last_update") val lastUpdate: String?,
    @SerializedName("last_update_global") val lastUpdateGlobal: String?,
    @SerializedName("category") val category: Category?,
    @SerializedName("muscles") val muscles: List<Muscle>?,
    @SerializedName("muscles_secondary") val musclesSecondary: List<Muscle>?,
    @SerializedName("equipment") val equipment: List<EquipmentDetail>?,
    @SerializedName("license") val license: License?,
    @SerializedName("license_author") val licenseAuthor: String?,
    @SerializedName("images") val images: List<ExerciseImage>?,
    @SerializedName("translations") val translations: List<ExerciseTranslation>?,
    @SerializedName("variations") val variations: Any?,
    @SerializedName("videos") val videos: List<ExerciseVideo>?,
    @SerializedName("author_history") val authorHistory: List<String>?,
    @SerializedName("total_authors_history") val totalAuthorsHistory: List<String>?
)

data class Category(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

data class Muscle(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("name_en") val nameEn: String?,
    @SerializedName("is_front") val isFront: Boolean?,
    @SerializedName("image_url_main") val imageUrlMain: String?,
    @SerializedName("image_url_secondary") val imageUrlSecondary: String?
)

data class EquipmentDetail(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

data class License(
    @SerializedName("id") val id: Int,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("short_name") val shortName: String,
    @SerializedName("url") val url: String
)

data class ExerciseImage(
    @SerializedName("id") val id: Int,
    @SerializedName("exercise_base") val exerciseBaseId: Int?,
    @SerializedName("image") val imageUrl: String,
    @SerializedName("is_main") val isMain: Boolean,
    @SerializedName("uuid") val uuid: String
)

data class ExerciseVideo(
    @SerializedName("id") val id: Int,
    @SerializedName("exercise_base") val exerciseBaseId: Int?,
    @SerializedName("video") val videoUrl: String,
    @SerializedName("is_main") val isMain: Boolean,
    @SerializedName("uuid") val uuid: String
)

data class ExerciseTranslation(
    @SerializedName("id") val id: Int,
    @SerializedName("uuid") val uuid: String,
    @SerializedName("name") val name: String,
    @SerializedName("exercise") val exerciseId: Int?,
    @SerializedName("description") val description: String,
    @SerializedName("created") val created: String?,
    @SerializedName("language") val languageId: Int,
    @SerializedName("aliases") val aliases: List<Any>?,
    @SerializedName("notes") val notes: List<Any>?,
    @SerializedName("license") val license: Int?,
    @SerializedName("license_title") val licenseTitle: String?,
    @SerializedName("license_object_url") val licenseObjectUrl: String?,
    @SerializedName("license_author") val licenseAuthor: String?,
    @SerializedName("license_author_url") val licenseAuthorUrl: String?,
    @SerializedName("license_derivative_source_url") val licenseDerivativeSourceUrl: String?,
    @SerializedName("author_history") val authorHistory: List<String>?
)


fun ExerciseInfo.getLocalizedName(languageId: Int = 2): String {
    val localizedTranslation = translations?.firstOrNull { it.languageId == languageId }

    return localizedTranslation?.name ?: translations?.firstOrNull()?.name ?: "Nama tidak tersedia"
}

fun ExerciseInfo.getLocalizedDescription(languageId: Int = 2): String {
    val localizedTranslation = translations?.firstOrNull { it.languageId == languageId }

    val htmlDescription = localizedTranslation?.description
        ?: translations?.firstOrNull()?.description

    return if (!htmlDescription.isNullOrEmpty()) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(htmlDescription, Html.FROM_HTML_MODE_COMPACT).toString()
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(htmlDescription).toString()
        }
    } else {
        "Deskripsi tidak tersedia."
    }
}

fun ExerciseInfo.getMainImageUrl(): String? {
    return images?.firstOrNull { it.isMain }?.imageUrl ?: images?.firstOrNull()?.imageUrl
}

fun ExerciseInfo.getMainVideoUrl(): String? {
    return videos?.firstOrNull { it.isMain }?.videoUrl ?: videos?.firstOrNull()?.videoUrl
}