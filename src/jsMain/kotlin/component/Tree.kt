package component

import emotion.react.css
import http.CreateItemRequest
import model.Category
import model.Item
import model.Tree
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import web.cssom.Display
import web.cssom.FlexDirection
import web.cssom.TextAlign
import web.cssom.number
import web.cssom.vh

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
    div {
        css {
            display = Display.flex
            flexDirection = FlexDirection.row
            height = 100.vh
            textAlign = TextAlign.center
        }
        div {
            css {
                flexGrow = number(1.0)
            }
            props.parent?.let { parent: Category ->
                Parent {
                    name = parent.name
                    onClick = {
                        props.onClickParent(parent.id)
                    }
                }
            } ?: Parent {
                name = "Root"
                onClick = { }
            }
        }
        div {
            css {
                flexGrow = number(4.0)
            }
            +props.category.name
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
        div {
            css {
                flexGrow = number(1.0)
            }
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
