package component

import emotion.react.css
import http.CreateItemRequest
import model.Item
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import web.cssom.Display
import web.cssom.FlexDirection
import web.cssom.JustifyContent
import web.cssom.number
import web.cssom.pct
import web.cssom.px

external interface QueueProps : Props {
    var items: List<Item>
    var createItem: (CreateItemRequest) -> Unit
    var deleteItem: (Item) -> Unit
}

val Queue = FC { props: QueueProps ->
    div {
        css {
            display = Display.flex
            flexDirection = FlexDirection.column
            justifyContent = JustifyContent.center
            height = 100.pct
        }
        props.items.takeIf(List<*>::isNotEmpty)?.map { itemProp: Item ->
            div {
                css {
                    flexGrow = number(1.0)
                    padding = 25.px
                }
                Item {
                    item = itemProp
                }
                button {
                    +"Delete"
                    onClick = {
                        props.deleteItem(itemProp)
                    }
                }
            }
        } ?: div { +"Nothing here yet!" }
        div {
            css {
                flexGrow = number(1.0)
                padding = 25.px
            }
            CreateItemButton {
                createItem = props.createItem
            }
        }
    }
}
