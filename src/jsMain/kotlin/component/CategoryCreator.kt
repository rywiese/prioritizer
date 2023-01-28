package component

import kotlinx.browser.window
import react.FC
import react.Props
import react.dom.html.ReactHTML

external interface CategoryCreatorProps : Props {
    var createCategory: (categoryName: String) -> Unit
}

val CategoryCreator = FC { props: CategoryCreatorProps ->
    ReactHTML.div {
        ReactHTML.p {
            +"Create new category +"
        }
        onClick = {
            window.prompt("Enter category name")?.let { name: String ->
                props.createCategory(name)
            }
        }
    }
}
