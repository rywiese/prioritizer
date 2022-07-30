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
            props.treeClient.getRoot { root: Depth1Tree ->
                statefulTree = root
                statefulChildren = root.children.sortedBy { it.name }.toSet()
            }
        }
    }
    statefulTree?.let {
        Tree {
            tree = it
            children = statefulChildren
            onClickParent = { parentId: String ->
                mainScope.launch {
                    props.treeClient.getTree(parentId) { parent: Depth1Tree ->
                        statefulTree = parent
                        statefulChildren = parent.children.sortedBy { it.name }.toSet()
                    }
                }
            }
            onClickChild = { child: Tree ->
                mainScope.launch {
                    props.treeClient.getTree(child.id) { childId: Depth1Tree ->
                        statefulTree = childId
                        statefulChildren = childId.children.sortedBy { it.name }.toSet()
                    }
                }
            }
            onClickChildItem = { child: Tree ->
                mainScope.launch {
                    props.treeClient.promote(
                        treeId = child.parentId!!,
                        childId = child.id
                    ) { tree: Depth1Tree ->
                        statefulTree = tree
                        statefulChildren = tree.children.sortedBy { it.name }.toSet()
                    }
                }
            }
        }
    } ?: div { +"oops" }
}
