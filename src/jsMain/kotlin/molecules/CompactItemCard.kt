package molecules

import model.Item
import mui.material.Avatar
import mui.material.Button
import mui.material.Card
import mui.material.CardContent
import mui.material.Stack
import mui.material.StackDirection
import mui.material.Typography
import mui.system.responsive
import mui.system.sx
import react.FC
import react.Props
import web.cssom.Position
import web.cssom.TextAlign
import web.cssom.px

external interface CompactItemCardProps : Props {
    var item: Item
    var elevation: Number
}

val CompactItemCard = FC { props: CompactItemCardProps ->
    Card {
        sx {
            position = Position.relative
            textAlign = TextAlign.left
        }
        elevation = props.elevation
        CardContent {
            Stack {
                direction = responsive(StackDirection.row)
                Avatar {
                    sx {
                        height = 72.px
                        width = 72.px
                    }
                }
                Stack {
                    sx {
                        paddingLeft = 12.px
                    }
                    direction = responsive(StackDirection.column)
                    Typography {
                        sx {
                            paddingLeft = 8.px
                            paddingRight = 8.px
                        }
                        +props.item.name
                    }
                    Typography {
                        sx {
                            paddingLeft = 8.px
                            paddingRight = 8.px
                        }
                        +"$${props.item.price}"
                    }
                    Button {
                        +"Promote"
                    }
                }
            }
        }
    }
}
