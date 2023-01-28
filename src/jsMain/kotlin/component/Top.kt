package component

import api.PrioritizerApi
import http.CreateItemRequest
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import model.Category
import model.Item
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
    var statefulCategory: Category? by useState(null)
    var statefulQueue: List<Item> by useState(mutableListOf())
    var statefulChildren: Set<Tree> by useState(emptySet())
    useEffectOnce {
        mainScope.launch {
            props.api
                .getRoot(maxDepth = 1)
                ?.also { root: Tree ->
                    statefulCategory = root.category
                    statefulQueue = root.queue.toMutableList()
                    statefulChildren = root.children
                }
        }
    }
    statefulCategory?.let { currentCategory: Category ->
        Tree {
            category = currentCategory
            queue = statefulQueue
            parent = statefulParent
            children = statefulChildren
            onClickChild = { childId: String ->
                mainScope.launch {
                    props.api
                        .getTree(
                            categoryId = childId,
                            maxDepth = 1
                        )
                        ?.also { child: Tree ->
                            statefulGrandparent = statefulParent
                            statefulParent = category
                            statefulCategory = child.category
                            statefulQueue = child.queue.toMutableList()
                            statefulChildren = child.children
                        }
                }
            }
            onClickParent = { parentId: String ->
                mainScope.launch {
                    props.api
                        .getTree(
                            categoryId = parentId,
                            maxDepth = 1
                        )
                        ?.also { parent: Tree ->
                            statefulCategory = parent.category
                            statefulQueue = parent.queue.toMutableList()
                            statefulChildren = parent.children
                            statefulParent = statefulGrandparent
                            statefulGrandparent = null
                        }
                }
            }
            createNewItem = { categoryId: String, createItemRequest: CreateItemRequest ->
                mainScope.launch {
                    props.api
                        .createItem(categoryId, createItemRequest)
                        ?.also { item: Item ->
                            statefulQueue = statefulQueue + item
                        }
                }
            }
        }
    } ?: div { +"Not loaded... yet?" }
}
