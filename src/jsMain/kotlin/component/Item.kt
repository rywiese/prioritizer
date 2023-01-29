package component

import model.Item
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p

external interface ItemProps : Props {
    var item: Item
}

val Item = FC<ItemProps> { props: ItemProps ->
    div {
        p {
            +"Name: ${props.item.name}"
        }
        p {
            +"Price: $${props.item.price}"
        }
        p {
            +"Link: ${props.item.link}"
        }
    }
}
