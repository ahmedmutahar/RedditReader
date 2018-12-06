package com.apps.bit.redditreader.model

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root
data class Feed(
        @field: ElementList(inline = true)
        var entry: List<Entry>? = null
)