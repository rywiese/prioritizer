package component

import http.CreateItemRequest
import kotlinx.browser.window
import mui.material.Button
import react.FC
import react.Props

external interface CreateItemButtonProps : Props {
    var createItem: (CreateItemRequest) -> Unit
}

val CreateItemButton = FC { props: CreateItemButtonProps ->
    Button {
        +"Create new item"
        onClick = {
            window.prompt("Enter item name")?.let { name: String ->
                window.prompt("Enter item price")
                    ?.toDouble()
                    ?.let { price: Double ->
                        window.prompt("Enter item link")?.let { link: String ->
                            props.createItem(
                                CreateItemRequest(
                                    name = name,
                                    price,
                                    link = link
                                )
                            )
                        }
                    }
            }
        }
    }
}
