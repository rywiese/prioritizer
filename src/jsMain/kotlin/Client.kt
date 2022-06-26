import component.Tree
import component.Welcome
import kotlinx.browser.document
import react.create
import react.dom.render

fun main() {
    val container = document.createElement("div")
    document.body!!.appendChild(container)

    val treeComponent = Tree.create {
        tree = tree4
    }
    render(treeComponent, container)
}
