package component

import model.Item
import react.FC
import react.Props
import react.dom.html.ReactHTML.div

external interface ItemProps : Props {
    var item: Item
}

val Item = FC<ItemProps> { props: ItemProps ->
    div {
        +props.item.toString()
    }
}
