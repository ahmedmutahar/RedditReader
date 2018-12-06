package com.apps.bit.redditreader.model

import com.apps.bit.redditreader.util.Converter
import io.objectbox.annotation.Id
import io.objectbox.converter.PropertyConverter
import org.simpleframework.xml.Element

data class Author(
        @Id
        var id: Long = 0,

        @field: Element
        var name: String? = null,

        @field: Element
        var uri: String? = null
) {
    class AuthorConverter : PropertyConverter<Author, ByteArray> {
        override fun convertToDatabaseValue(entityProperty: Author) = Converter.serialize(entityProperty)

        override fun convertToEntityProperty(databaseValue: ByteArray) = Converter.deserialize<Author>(databaseValue)
    }
}