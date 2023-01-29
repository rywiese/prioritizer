package component

import model.Category
import model.Item
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div

external interface ChildProps : Props {
    var category: Category
    var firstItemOrNull: Item?
    var onClick: () -> Unit
    var deleteCategory: (Category) -> Unit
}

val Child = FC { props: ChildProps ->
    div {
        +props.category.name
        onClick = { props.onClick() }
    }
    props.firstItemOrNull?.let { firstItem: Item ->
        Item {
            item = firstItem
        }
    } ?: div { +"Empty!" }
    button {
        +"Delete"
        onClick = {
            props.deleteCategory(props.category)
        }
    }
}
