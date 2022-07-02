package component

import csstype.Display
import csstype.FlexDirection
import csstype.FlexGrow
import csstype.JustifyContent
import csstype.pct
import csstype.px
import model.Tree
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.div

external interface ChildrenProps : Props {
    var children: Set<Tree>
}

val Children = FC { props: ChildrenProps ->
    div {
        css {
            display = Display.flex
            flexDirection = FlexDirection.column
            justifyContent = JustifyContent.center
            height = 100.pct
        }
        props.children.map { child: Tree ->
            div {
                css {
                    flexGrow = FlexGrow(1.0)
                    padding = 25.px
                }
                Child {
                    childId = child.id
                    name = child.name
                    firstItemOrNull = child.queue.firstOrNull()
                }
            }
        }
    }
}
