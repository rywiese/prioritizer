import component.Tree
import kotlinx.browser.document
import react.create
import react.dom.render

fun main() {
    val container = document.createElement("div")
    document.body!!.appendChild(container)

    val treeComponent = Tree.create {
        tree = budget
    }
    render(treeComponent, container)
}
