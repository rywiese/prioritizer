package component

import react.FC
import react.Props
import react.dom.html.ReactHTML.div

external interface ChildProps : Props {
    var childId: String
}

val Child = FC<ChildProps> { props: ChildProps ->
    div {
        +props.childId
    }
}
