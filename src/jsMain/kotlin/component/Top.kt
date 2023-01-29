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
            onClickChild = { child: Tree ->
                mainScope.launch {
                    props.api
                        .getTree(
                            categoryId = child.category.id,
                            maxDepth = 1
                        )
                        ?.also { child: Tree ->
                            statefulGrandparent = statefulParent
                            statefulParent = statefulCategory
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
            createItem = { categoryId: String, createItemRequest: CreateItemRequest ->
                mainScope.launch {
                    props.api
                        .createItem(categoryId, createItemRequest)
                        ?.also { item: Item ->
                            statefulQueue = statefulQueue + item
                        }
                }
            }
            createSubcategory = { parentId: String, name: String ->
                mainScope.launch {
                    props.api
                        .createSubcategory(
                            parentId = parentId,
                            name = name
                        )
                        ?.also { category: Category ->
                            statefulChildren = statefulChildren + object : Tree {
                                override val category: Category = category
                                override val queue: List<Item> = emptyList()
                                override val children: Set<Tree> = emptySet()
                            }
                        }
                }
            }
            deleteCategory = { category: Category ->
                mainScope.launch {
                    props.api
                        .deleteCategory(category.id)
                        ?.also { deletedCategoryId: String ->
                            statefulChildren
                                .find { child: Tree ->
                                    child.category.id == deletedCategoryId
                                }
                                ?.let { childToDelete: Tree ->
                                    statefulChildren = statefulChildren - childToDelete
                                }
                        }
                }
            }
        }
    } ?: div { +"Not loaded... yet?" }
}
