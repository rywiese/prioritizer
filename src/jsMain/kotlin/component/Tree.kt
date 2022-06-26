package component

import model.Tree
import react.FC
import react.Props
import react.dom.html.ReactHTML.div

external interface TreeProps : Props {
    var tree: Tree
}

val Tree = FC<TreeProps> { props: TreeProps ->
    div {
        +props.tree.id
    }
    Node {
        node = props.tree.node
    }
    Queue {
        items = props.tree.queue
    }
    props.tree.childIds.map { childIdProp: String ->
        Child {
            childId = childIdProp
        }
    }
}
