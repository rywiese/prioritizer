package component

import emotion.react.css
import model.Category
import model.Tree
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import web.cssom.Display
import web.cssom.FlexDirection
import web.cssom.JustifyContent
import web.cssom.number
import web.cssom.pct
import web.cssom.px

external interface ChildrenProps : Props {
    var children: Set<Tree>
    var onClickChild: (child: Tree) -> Unit
    var createSubcategory: (subCategoryName: String) -> Unit
    var deleteCategory: (Category) -> Unit
}

val Children = FC { props: ChildrenProps ->
    div {
        css {
            display = Display.flex
            flexDirection = FlexDirection.column
            justifyContent = JustifyContent.center
            height = 100.pct
        }
        props.children.map { child: Tree ->
            div {
                css {
                    flexGrow = number(1.0)
                    padding = 25.px
                }
                Child {
                    category = child.category
                    firstItemOrNull = child.queue.firstOrNull()
                    onClick = { props.onClickChild(child) }
                    deleteCategory = props.deleteCategory
                }
            }
        }
        div {
            css {
                flexGrow = number(1.0)
                padding = 25.px
            }
            CreateCategoryButton {
                createCategory = props.createSubcategory
            }
        }
    }
}
