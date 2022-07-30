package component

import react.FC
import react.Props
import react.dom.html.ReactHTML.div

external interface ParentProps : Props {
    var name: String
    var onClick: () -> Unit
}

val Parent = FC { props: ParentProps ->
    div {
        +props.name
        onClick = { props.onClick() }
    }
}
