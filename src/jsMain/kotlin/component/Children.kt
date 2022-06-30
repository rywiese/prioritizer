package component

import csstype.*
import model.Tree
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.div

external interface ChildrenProps : Props {
    var children: Set<Tree>
}

val Children = FC<ChildrenProps> { props: ChildrenProps ->
    div {
        css {
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
