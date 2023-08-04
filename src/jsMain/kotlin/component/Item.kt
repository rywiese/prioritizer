package component

import emotion.react.css
import model.Item
import mui.material.Card
import mui.material.CardContent
import mui.material.Typography
import react.FC
import react.Props
import web.cssom.Position
import web.cssom.TextAlign
import web.cssom.px

external interface ItemProps : Props {
    var item: Item
}

val Item = FC { props: ItemProps ->
    Card {
        css {
            position = Position.relative
            textAlign = TextAlign.left
            //width = 250.px
        }
        //variant = PaperVariant.outlined
        CardContent {
            Typography {
                +"Name: ${props.item.name}"
            }
            Typography {
                +"Price: $${props.item.price}"
            }
            Typography {
                +"Link: ${props.item.link}"
            }
        }
    }
}
