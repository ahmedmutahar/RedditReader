package com.apps.bit.redditreader.model

import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import org.simpleframework.xml.Element
import java.io.Serializable
import java.util.*

@Entity
data class Entry(
        @Id
        var id: Long = 0,

        @field: Element(name = "id")
        var postId: String? = null,

        @field: Element
        @field: Convert(converter = Author.AuthorConverter::class, dbType = ByteArray::class)
        var author: Author? = null,

        @field: Element
        @field: Convert(converter = Category.CategoryConverter::class, dbType = ByteArray::class)
        var category: Category? = null,

        @field: Element
        var title: String? = null,

        @field: Element
        var content: String? = null,

        @field: Element
        var updated: Date? = null,

        @field: Element
        @field: Convert(converter = Link.LinkConverter::class, dbType = ByteArray::class)
        var link: Link? = null
) : Serializable