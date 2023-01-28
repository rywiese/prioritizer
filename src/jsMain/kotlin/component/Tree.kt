package component

import csstype.Display
import csstype.FlexDirection
import csstype.FlexGrow
import csstype.TextAlign
import csstype.vh
import http.CreateItemRequest
import model.Category
import model.Item
import model.Tree
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.div

external interface TreeProps : Props {
    var parent: Category?
    var category: Category
    var queue: List<Item>
    var children: Set<Tree>
    var onClickChild: (childId: String) -> Unit
    var onClickParent: (parentId: String) -> Unit
    var createNewItem: (categoryId: String, CreateItemRequest) -> Unit
}

val Tree = FC { props: TreeProps ->
    div {
        css {
            display = Display.flex
            flexDirection = FlexDirection.row
            height = 100.vh
            textAlign = TextAlign.center
        }
        div {
            css {
                flexGrow = FlexGrow(1.0)
            }
            props.parent?.let { parent: Category ->
                Parent {
                    name = parent.name
                    onClick = {
                        props.onClickParent(parent.id)
                    }
                }
            } ?: Parent {
                name = "Root"
                onClick = { }
            }
        }
        div {
            css {
                flexGrow = FlexGrow(4.0)
            }
            +props.category.name
            Queue {
                items = props.queue
                createNewItem = {
                    props.createNewItem(
                        props.category.id,
                        CreateItemRequest(
                            name = "TODO",
                            price = 69.0,
                            link = "figurethis.out/todo"
                        )
                    )
                }
            }
        }
        div {
            css {
                flexGrow = FlexGrow(1.0)
            }
            Children {
                children = props.children
                onClickChild = props.onClickChild
            }
        }
    }
}
