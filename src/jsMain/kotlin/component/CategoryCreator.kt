package component

import kotlinx.browser.window
import mui.material.Button
import react.FC
import react.Props

external interface CreateCategoryButtonProps : Props {
    var createCategory: (categoryName: String) -> Unit
}

// TODO: Make this an icon
val CreateCategoryButton = FC { props: CreateCategoryButtonProps ->
    Button {
        +"Create new category"
        onClick = {
            window.prompt("Enter category name")?.let { name: String ->
                props.createCategory(name)
            }
        }
    }
}
