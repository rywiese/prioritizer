package component

import model.Item
import react.FC
import react.Props

external interface QueueProps : Props {
    var items: List<Item>
}

val Queue = FC<QueueProps> { props: QueueProps ->
    props.items.map { itemProp: Item ->
        Item {
            item = itemProp
        }
    }
}
