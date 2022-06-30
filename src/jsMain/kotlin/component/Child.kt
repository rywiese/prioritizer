package component

import model.Item
import model.Node
import react.FC
import react.Props
import react.dom.html.ReactHTML.div

external interface ChildProps : Props {
    var childId: String
    var node: Node
    var firstItemOrNull: Item?
}

val Child = FC<ChildProps> { props: ChildProps ->
    Node {
        node = props.node
    }
    props.firstItemOrNull?.let { firstItem: Item ->
        Item {
            item = firstItem
        }
    } ?: div { +"Empty!" }
}
