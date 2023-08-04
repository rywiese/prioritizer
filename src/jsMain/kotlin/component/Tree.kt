package component

import http.CreateItemRequest
import model.Category
import model.Item
import model.Tree
import mui.material.Container
import mui.material.Stack
import mui.material.StackDirection
import mui.system.responsive
import mui.system.sx
import organisms.Subcategory
import react.FC
import react.Props
import web.cssom.AlignItems
import web.cssom.px

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
        Stack {
            sx {
                alignItems = AlignItems.center
                padding = 36.px
            }
            useFlexGap = true
            spacing = responsive(10)
            props.children.map { child: Tree ->
                Subcategory {
                    category = child.category
                    queue = child.queue
                    onClick = { props.onClickChild(child) }
                    deleteCategory = props.deleteCategory
                }
            }
        }
    }
}
