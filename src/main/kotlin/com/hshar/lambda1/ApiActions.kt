package com.hshar.lambda1

import com.github.salomonbrys.kotson.fromJson
import org.litote.kmongo.*
import spark.Request
import spark.Response
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.mongodb.client.MongoCollection
import java.util.*

class ApiActions {

    var collection: MongoCollection<Note> = Database.getNotes()

    fun insertNote(req: Request, res: Response): String {
        val note = Gson().fromJson<Note>(req.body())
        note.createdAt = Date()
        collection.insertOne(note.json) // TODO: Check if insert failed then return objectId!
        res.status(200)
        res.body("Success")
        return res.body()
    }

    fun getNote(req: Request, res: Response): String? {
        val note = collection.findOneById(org.bson.types.ObjectId(req.params("id")))
        return note?.json
    }

    fun getAllNoes(req: Request, res: Response): String? {
        val notes = collection.find()
        return notes?.json
    }

    fun deleteNote(req: Request, res: Response): String? {
        return collection.deleteOneById(org.bson.types.ObjectId(req.params("id"))).deletedCount.toString()
    }

    fun updateNote(req: Request, res: Response): String? {
        val id = req.params("id")
        val body = req.body() ?: "{}"
        try {
            if (id != null) {
                body.replace("\n", "")
                body.replace("\t", "")

                val update = Gson().fromJson<NoteUpdate>(body)
                update.updatedAt = Date()
                val updateJson = "{${MongoOperator.set}: ${update.json}}"
                return collection.updateOneById(org.bson.types.ObjectId(id), updateJson).toString()
            } else {
                res.status(400)
                res.body("Bad Request")
                return res.body()
            }
        } catch (e: JsonSyntaxException) {
            res.status(400)
            res.body("Bad Request")
            return res.body()
        }
    }
}
