package component

import api.TreeApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import model.Tree
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.useEffectOnce
import react.useState

external interface TopProps : Props {
    var treeApi: TreeApi
}

val mainScope = MainScope()

val Top = FC { props: TopProps ->
    var statefulTreeOrNull: Tree? by useState(null)
    useEffectOnce {
        mainScope.launch {
            props.treeApi
                .getRoot(
                    treeDepth = 1,
                    queueLength = 100
                )
                ?.also { root: Tree ->
                    statefulTreeOrNull = root
                }
        }
    }
    statefulTreeOrNull?.let { statefulTree: Tree ->
        Tree {
            tree = statefulTree
            onClickChild = { childId: String ->
                mainScope.launch {
                    props.treeApi
                        .getTree(
                            treeId = childId,
                            treeDepth = 1,
                            queueLength = 100,
                        )
                        ?.also { child: Tree ->
                            statefulTreeOrNull = child
                        }
                }
            }
            onClickParent = {
                mainScope.launch {
                    props.treeApi
                        .getParent(
                            treeId = statefulTree.id,
                            treeDepth = 1,
                            queueLength = 100,
                        )
                        ?.also { parent: Tree ->
                            statefulTreeOrNull = parent
                        }
                }
            }
        }
    } ?: div { +"oops" }
}
