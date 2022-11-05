import client.MockPrioritizerApi
import component.Top
import kotlinx.browser.document
import react.create
import react.dom.render

fun main() {
    val container = document.createElement("div")
    document.body!!.appendChild(container)

    val treeComponent = Top.create {
        api = MockPrioritizerApi
    }
    render(treeComponent, container)
}
