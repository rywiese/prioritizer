package component

import api.TreeApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import model.DeepTree
import model.Tree
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.useEffectOnce
import react.useState

external interface TopProps : Props {
    var api: TreeApi
}

val mainScope = MainScope()

val Top = FC { props: TopProps ->
    var statefulTree: Tree? by useState(null)
    var statefulChildren: Set<Tree> by useState(emptySet())
    useEffectOnce {
        mainScope.launch {
            val root: Tree = props.api.getRoot(
                treeDepth = 1,
                queueLength = -1
            )!!
            require(root is DeepTree)
            statefulTree = root
            statefulChildren = root.children
        }
    }
    statefulTree?.let {
        Tree {
            tree = it
            children = statefulChildren
            onClickChild = { childId: String ->
                mainScope.launch {
                    val child: Tree = props.api.getTree(
                        treeId = childId,
                        treeDepth = 1,
                        queueLength = -1
                    )!!
                    require(child is DeepTree)
                    statefulTree = child
                    statefulChildren = child.children
                }
            }
            onClickParent = { parentId: String ->
                mainScope.launch {
                    val parent: Tree = props.api.getTree(
                        treeId = parentId,
                        treeDepth = 1,
                        queueLength = -1
                    )!!
                    require(parent is DeepTree)
                    statefulTree = parent
                    statefulChildren = parent.children
                }
            }
        }
    } ?: div { +"oops" }
}
