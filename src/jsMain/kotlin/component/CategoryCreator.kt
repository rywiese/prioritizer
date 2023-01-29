package component

import kotlinx.browser.window
import react.FC
import react.Props
import react.dom.html.ReactHTML.button

external interface CreateCategoryButtonProps : Props {
    var createCategory: (categoryName: String) -> Unit
}

val CreateCategoryButton = FC { props: CreateCategoryButtonProps ->
    button {
        +"Create new category"
        onClick = {
            window.prompt("Enter category name")?.let { name: String ->
                props.createCategory(name)
            }
        }
    }
}
