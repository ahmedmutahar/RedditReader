package com.apps.bit.redditreader.model

import com.apps.bit.redditreader.util.Converter
import io.objectbox.annotation.Id
import io.objectbox.converter.PropertyConverter
import org.simpleframework.xml.Attribute

data class Category(
        @Id
        var id: Long = 0,

        @field: Attribute
        var term: String? = null
) {
    class CategoryConverter : PropertyConverter<Category, ByteArray> {
        override fun convertToDatabaseValue(entityProperty: Category) = Converter.serialize(entityProperty)

        override fun convertToEntityProperty(databaseValue: ByteArray) = Converter.deserialize<Category>(databaseValue)
    }
}