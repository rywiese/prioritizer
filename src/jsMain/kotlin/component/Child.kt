package component

import model.Category
import model.Item
import mui.icons.material.SubdirectoryArrowRight
import mui.material.Button
import mui.material.Container
import mui.material.IconButton
import mui.material.Typography
import react.FC
import react.Props
import react.dom.html.ReactHTML.div

external interface ChildProps : Props {
    var category: Category
    var firstItemOrNull: Item?
    var onClick: () -> Unit
    var deleteCategory: (Category) -> Unit
}

val Child = FC { props: ChildProps ->
    Container {
        Typography {
            +props.category.name
        }
        props.firstItemOrNull?.let { firstItem: Item ->
            Item {
                item = firstItem
            }
        } ?: div { +"Empty!" }
        Button {
            +"Delete"
            onClick = {
                props.deleteCategory(props.category)
            }
        }
        IconButton {
            SubdirectoryArrowRight {
                onClick = { props.onClick() }
            }
        }
    }
}
