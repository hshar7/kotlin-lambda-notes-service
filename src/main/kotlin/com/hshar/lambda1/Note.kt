package com.hshar.lambda1

import java.util.*

data class Note(
        val _id: org.bson.types.ObjectId,
        var title: String,
        var content: String?,
        var createdAt: Date,
        var updatedAt: Date?
)
