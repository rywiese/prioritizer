package component

import model.Node
import react.FC
import react.Props

external interface NodeProps : Props {
    var node: Node
}

val Node = FC<NodeProps> { props: NodeProps ->
    +props.node.toString()
}
