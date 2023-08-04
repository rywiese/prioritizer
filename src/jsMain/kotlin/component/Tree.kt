package component

import http.CreateItemRequest
import model.Category
import model.Item
import model.Tree
import mui.material.Container
import mui.material.Stack
import mui.material.StackDirection
import mui.system.responsive
import react.FC
import react.Props

external interface TreeProps : Props {
    var parent: Category?
    var category: Category
    var queue: List<Item>
    var children: Set<Tree>
    var onClickChild: (Tree) -> Unit
    var onClickParent: (parentId: String) -> Unit
    var createItem: (categoryId: String, CreateItemRequest) -> Unit
    var deleteItem: (Item) -> Unit
    var createSubcategory: (parentId: String, categoryName: String) -> Unit
    var deleteCategory: (Category) -> Unit
}

val Tree = FC { props: TreeProps ->
    Stack {
        direction = responsive(StackDirection.row)
        Container {
            props.parent?.let { parent: Category ->
                Parent {
                    name = parent.name
                    onClick = {
                        props.onClickParent(parent.id)
                    }
                }
            } ?: Parent {
                name = "Root"
                disabled = true
                onClick = { }
            }
        }
        Container {
            Queue {
                items = props.queue
                createItem = { createItemRequest: CreateItemRequest ->
                    props.createItem(
                        props.category.id,
                        createItemRequest
                    )
                }
                deleteItem = props.deleteItem
            }
        }
        Container {
            Children {
                children = props.children
                onClickChild = props.onClickChild
                createSubcategory = { subCategoryName: String ->
                    props.createSubcategory(
                        props.category.id,
                        subCategoryName
                    )
                }
                deleteCategory = props.deleteCategory
            }
        }
    }
}
