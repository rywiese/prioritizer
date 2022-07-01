package component

import csstype.Display
import csstype.FlexDirection
import csstype.FlexGrow
import csstype.pct
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
            height = 100.pct
            display = Display.flex
            flexDirection = FlexDirection.column
        }
        props.children.map { child: Tree ->
            div {
                css {
                    flexGrow = FlexGrow(1.0)
                }
                Child {
                    childId = child.id
                    node = child.node
                    firstItemOrNull = child.queue.firstOrNull()
                }
            }
        }
    }
}
