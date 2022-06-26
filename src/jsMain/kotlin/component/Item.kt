package component

import model.Item
import react.FC
import react.Props

external interface ItemProps : Props {
    var item: Item
}

val Item = FC<ItemProps> { props: ItemProps ->
    +props.item.toString()
}
