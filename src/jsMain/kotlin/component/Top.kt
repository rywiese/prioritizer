package component

import model.DeepTree
import react.FC
import react.Props
import react.useState

external interface TopProps : Props {
    var initialTree: DeepTree
}

val Top = FC { props: TopProps ->
    var statefulTree: DeepTree by useState(props.initialTree)
    Tree {
        tree = statefulTree
        onClickChild = { childId: String ->
            tree.children
                .find { child: DeepTree ->
                    child.id == childId
                }
                ?.let { child: DeepTree ->
                    statefulTree = child
                }
        }
        onClickParent = { parentId: String ->
            statefulTree = props.initialTree.subTree(parentId)!!
        }
    }
}
