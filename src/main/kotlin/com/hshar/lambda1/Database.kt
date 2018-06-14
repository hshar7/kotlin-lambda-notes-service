package com.hshar.lambda1

import com.mongodb.MongoClientURI
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

object Database {
    private lateinit var database: MongoDatabase

    init {
        makeConnection()
    }

    fun makeConnection() {
        try {
            val connectionString = CONNECTION
            val clientUri = MongoClientURI(connectionString)
            val client = KMongo.createClient(clientUri)
            database = client.getDatabase(DEFAULT_DB)
        } catch (ex: Exception) {
            println("Exeption: ${ex.message}")
        }
    }
    fun getNotes() = database.getCollection<Note>("notes")
}
