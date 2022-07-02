package component

import model.Item
import model.Node
import react.FC
import react.Props
import react.dom.html.ReactHTML.div

external interface ChildProps : Props {
    var childId: String
    var name: String
    var firstItemOrNull: Item?
}

val Child = FC<ChildProps> { props: ChildProps ->
    div {
        +props.name
    }
    props.firstItemOrNull?.let { firstItem: Item ->
        Item {
            item = firstItem
        }
    } ?: div { +"Empty!" }
}
