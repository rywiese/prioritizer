import client.HttpPrioritizerClient
import component.Top
import react.create
import react.dom.client.createRoot
import web.dom.document

fun main() {
    createRoot(document.getElementById("root")!!)
        .render(
            Top.create {
                api = HttpPrioritizerClient
            }
        )
}
