package model

import protocol.Identifiable
import protocol.Named

interface Tree : Identifiable, Named {

    val queue: List<Item>

    val childIds: Set<String>

}
