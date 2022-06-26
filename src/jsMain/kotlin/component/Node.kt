package component

import model.Node
import react.FC
import react.Props
import react.dom.html.ReactHTML.div

external interface NodeProps : Props {
    var node: Node
}

val Node = FC<NodeProps> { props: NodeProps ->
    div {
        +props.node.toString()
    }
}
