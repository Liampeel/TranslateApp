package com.example.myapplication.TEST

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Query{
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("owner")
    @Expose
    var owner: String? = null

    @SerializedName("initialText")
    @Expose
    var initialText: String? = null

    @SerializedName("language")
    @Expose
    var language: String? = null

    @SerializedName("translatedText")
    @Expose
    var translatedText: String? = null

    @SerializedName("date_created")
    @Expose
    var dateCreated: String? = null

    companion object {
        private const val serialVersionUID = 3887269712603100567L
    }
}


class responseTest {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("username")
    @Expose
    var username: String? = null

    @SerializedName("queries")
    @Expose
    var queries: List<Query>? = null

    companion object {
        private const val serialVersionUID = 3167875534586939520L
    }
}