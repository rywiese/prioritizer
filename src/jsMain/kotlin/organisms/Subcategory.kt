package organisms

import model.Category
import model.Item
import molecules.CompactItemCard
import mui.icons.material.Delete
import mui.icons.material.Edit
import mui.icons.material.SubdirectoryArrowRight
import mui.material.Card
import mui.material.CardContent
import mui.material.Container
import mui.material.Divider
import mui.material.IconButton
import mui.material.Paper
import mui.material.PaperVariant
import mui.material.Stack
import mui.material.StackDirection
import mui.material.Toolbar
import mui.material.Typography
import mui.material.styles.TypographyVariant
import mui.system.responsive
import mui.system.sx
import react.FC
import react.Props
import web.cssom.Auto
import web.cssom.Display
import web.cssom.FlexDirection
import web.cssom.pct
import web.cssom.px
import kotlin.math.max

external interface ChildProps : Props {
    var category: Category
    var queue: List<Item>
    var onClick: () -> Unit
    var deleteCategory: (Category) -> Unit
}

val Subcategory = FC { props: ChildProps ->
    Card {
        sx {
            width = 100.pct
        }
        raised = true
        CardContent {
            Toolbar {
                sx {
                    display = Display.flex
                    flexDirection = FlexDirection.row
                }
                Typography {
                    variant = TypographyVariant.h5
                    +props.category.name
                }
                IconButton {
                    Edit {
                        onClick = {
                            // TODO
                        }
                    }
                }
                IconButton {
                    sx {
                        marginLeft = Auto.auto
                    }
                    Delete {
                        onClick = {
                            props.deleteCategory(props.category)
                        }
                    }
                }
            }
            Divider {

            }
            Stack {
                sx {
                    padding = 12.px
                }
                direction = responsive(StackDirection.row)
                spacing = responsive(24.px)
                props.queue.mapIndexed { index: Int, thisItem: Item ->
                    CompactItemCard {
                        item = thisItem
                        elevation = max(24 - 8 * index, 0)
                    }
                }
            }
            Divider {

            }
            Toolbar {
                IconButton {
                    sx {
                        marginLeft = Auto.auto
                        marginRight = Auto.auto
                    }
                    SubdirectoryArrowRight {
                        onClick = { props.onClick() }
                    }
                }
            }
        }
    }
}
