package component

import model.Category
import model.Tree
import mui.material.Stack
import mui.system.responsive
import mui.system.sx
import organisms.Subcategory
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
            Subcategory {
                category = child.category
                queue = child.queue
                onClick = { props.onClickChild(child) }
                deleteCategory = props.deleteCategory
            }
        }
    }
}
