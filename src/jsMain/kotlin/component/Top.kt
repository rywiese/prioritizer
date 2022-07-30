package component

import api.TreeApi
import client.TreeClient
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import model.Depth1Tree
import model.Tree
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.useEffectOnce
import react.useState

external interface TopProps : Props {
    var treeApi: TreeApi
    var treeClient: TreeClient
}

val mainScope = MainScope()

val Top = FC { props: TopProps ->
    var statefulTree: Tree? by useState(null)
    var statefulChildren: Set<Tree> by useState(emptySet())
    useEffectOnce {
        mainScope.launch {
            props.treeClient
                .getRootDepth1()
                ?.also { root: Depth1Tree ->
                    statefulTree = root
                    statefulChildren = root.children
                }
        }
    }
    statefulTree?.let {
        Tree {
            tree = it
            children = statefulChildren
            onClickChild = { childId: String ->
                mainScope.launch {
                    props.treeClient
                        .getTreeDepth1(
                            treeId = childId
                        )
                        ?.also { childId: Depth1Tree ->
                            statefulTree = childId
                            statefulChildren = childId.children
                        }
                }
            }
            onClickParent = { parentId: String ->
                mainScope.launch {
                    props.treeClient
                        .getTreeDepth1(
                            treeId = parentId
                        )
                        ?.also { parent: Depth1Tree ->
                            statefulTree = parent
                            statefulChildren = parent.children
                        }
                }
            }
        }
    } ?: div { +"oops" }
}
