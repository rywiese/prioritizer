package component

import csstype.Display
import csstype.FlexDirection
import csstype.FlexGrow
import csstype.JustifyContent
import csstype.pct
import csstype.px
import http.CreateItemRequest
import model.Item
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.div

external interface QueueProps : Props {
    var items: List<Item>
    var createItem: (CreateItemRequest) -> Unit
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
                    flexGrow = FlexGrow(1.0)
                    padding = 25.px
                }
                Item {
                    item = itemProp
                }
            }
        } ?: div { +"Nothing here yet!" }
        div {
            css {
                flexGrow = FlexGrow(1.0)
                padding = 25.px
            }
            ItemCreator {
                createItem = props.createItem
            }
        }
    }
}
