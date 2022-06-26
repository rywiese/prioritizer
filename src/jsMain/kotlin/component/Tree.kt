package component

import model.Tree
import react.FC
import react.Props

external interface TreeProps : Props {
    var tree: Tree
}

val Tree = FC<TreeProps> { props: TreeProps ->
    +props.tree.id
    Node {
        node = props.tree.node
    }
    Queue {
        items = props.tree.queue
    }
    props.tree.childIds.map { childId: String ->
        +childId
    }
}
