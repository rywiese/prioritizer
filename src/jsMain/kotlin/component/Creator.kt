package component

import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p

external interface CreatorProps : Props {
    var label: String
    var onClick: () -> Unit
}

val Creator = FC { props: CreatorProps ->
    div {
        p {
            +"Create new ${props.label} +"
        }
        onClick = { props.onClick() }
    }
}
