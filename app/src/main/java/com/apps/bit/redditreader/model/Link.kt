package com.apps.bit.redditreader.model

import com.apps.bit.redditreader.util.Converter
import io.objectbox.annotation.Id
import io.objectbox.converter.PropertyConverter
import org.simpleframework.xml.Attribute

data class Link(
        @Id
        var id: Long = 0,

        @field: Attribute
        var href: String? = null
) {
    class LinkConverter : PropertyConverter<Link, ByteArray> {
        override fun convertToDatabaseValue(entityProperty: Link) = Converter.serialize(entityProperty)

        override fun convertToEntityProperty(databaseValue: ByteArray) = Converter.deserialize<Link>(databaseValue)
    }
}