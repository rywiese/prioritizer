package component

import csstype.Display
import csstype.FlexDirection
import csstype.FlexGrow
import csstype.vh
import model.Category
import model.Tree
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.div

external interface TreeProps : Props {
    var tree: Tree
    var parent: Category?
    var children: Set<Tree>
    var onClickChild: (childId: String) -> Unit
    var onClickParent: (parentId: String) -> Unit
}

val Tree = FC { props: TreeProps ->
    div {
        css {
            display = Display.flex
            flexDirection = FlexDirection.row
            height = 100.vh
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
            +props.tree.category.name
            Queue {
                items = props.tree.queue
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
