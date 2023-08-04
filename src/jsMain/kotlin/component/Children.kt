package component

import model.Category
import model.Tree
import mui.material.Divider
import mui.material.DividerVariant
import mui.material.Stack
import mui.system.responsive
import mui.system.sx
import react.FC
import react.Props
import web.cssom.AlignItems

external interface ChildrenProps : Props {
    var children: Set<Tree>
    var onClickChild: (child: Tree) -> Unit
    var createSubcategory: (subCategoryName: String) -> Unit
    var deleteCategory: (Category) -> Unit
}

val Children = FC { props: ChildrenProps ->
    Stack {
        sx {
            alignItems = AlignItems.center
        }
        useFlexGap = true
        spacing = responsive(10)
        props.children.map { child: Tree ->
            Child {
                category = child.category
                firstItemOrNull = child.queue.firstOrNull()
                onClick = { props.onClickChild(child) }
                deleteCategory = props.deleteCategory
            }
        }
    }
}
