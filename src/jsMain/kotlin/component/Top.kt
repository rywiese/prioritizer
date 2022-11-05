package component

import api.PrioritizerApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import model.Category
import model.Tree
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.useEffectOnce
import react.useState

external interface TopProps : Props {
    var api: PrioritizerApi
}

val mainScope = MainScope()

val Top = FC { props: TopProps ->
    var statefulGrandparent: Category? by useState(null)
    var statefulParent: Category? by useState(null)
    var statefulTree: Tree? by useState(null)
    var statefulChildren: Set<Tree> by useState(emptySet())
    useEffectOnce {
        mainScope.launch {
            props.api
                .getRoot()
                ?.also { root: Tree ->
                    statefulTree = root
                    statefulChildren = root.children
                }
        }
    }
    statefulTree?.let { currentTree ->
        Tree {
            tree = currentTree
            parent = statefulParent
            children = statefulChildren
            onClickChild = { childId: String ->
                mainScope.launch {
                    props.api
                        .getTree(categoryId = childId)
                        ?.also { child: Tree ->
                            statefulGrandparent = statefulParent
                            statefulParent = currentTree.category
                            statefulTree = child
                            statefulChildren = child.children
                        }
                }
            }
            onClickParent = { parentId: String ->
                mainScope.launch {
                    props.api
                        .getTree(categoryId = parentId)
                        ?.also { parent: Tree ->
                            statefulChildren = parent.children
                            statefulTree = parent
                            statefulParent = statefulGrandparent
                            statefulGrandparent = null
                        }
                }
            }
        }
    } ?: div { +"Not loaded... yet?" }
}
