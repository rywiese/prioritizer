package component

import mui.material.Button
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p

external interface ParentProps : Props {
    var name: String
    var disabled: Boolean?
    var onClick: () -> Unit
}

val Parent = FC { props: ParentProps ->
    Button {
        p {
            +props.name
        }
        disabled = props.disabled
        onClick = { props.onClick() }
    }
}
