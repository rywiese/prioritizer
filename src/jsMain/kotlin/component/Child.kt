package component

import model.Item
import react.FC
import react.Props
import react.dom.html.ReactHTML.div

external interface ChildProps : Props {
    var name: String
    var firstItemOrNull: Item?
    var onClickChild: () -> Unit
    var onClickItem: () -> Unit
}

val Child = FC { props: ChildProps ->
    div {
        +props.name
        onClick = { props.onClickChild() }
    }
    props.firstItemOrNull?.let { firstItem: Item ->
        Item {
            item = firstItem
            onClick = props.onClickItem
        }
    } ?: div { +"Empty!" }
}
