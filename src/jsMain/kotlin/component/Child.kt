package component

import model.Item
import react.FC
import react.Props
import react.dom.html.ReactHTML.div

external interface ChildProps : Props {
    var name: String
    var firstItemOrNull: Item?
    var onClick: () -> Unit
}

val Child = FC { props: ChildProps ->
    div {
        +props.name
        onClick = { props.onClick() }
    }
    props.firstItemOrNull?.let { firstItem: Item ->
        Item {
            item = firstItem
        }
    } ?: div { +"Empty!" }
}
