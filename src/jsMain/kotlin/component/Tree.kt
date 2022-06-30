package component

import csstype.Display
import csstype.FlexDirection
import csstype.FlexGrow
import model.DeepTree
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.div

external interface TreeProps : Props {
    var tree: DeepTree
}

val Tree = FC<TreeProps> { props: TreeProps ->
    div {
        css {
            display = Display.flex
            flexDirection = FlexDirection.row
        }
        div {
            css {
                flexGrow = FlexGrow(1.0)
            }
            Node {
                node = props.tree.node
            }
        }
        div {
            css {
                flexGrow = FlexGrow(4.0)
            }
            Queue {
                items = props.tree.queue
            }
        }
        div {
            css {
                flexGrow = FlexGrow(1.0)
            }
            Children {
                children = props.tree.children
            }
        }
    }
}
