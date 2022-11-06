package model

interface Tree {

    val category: Category

    val queue: List<Item>

    val children: Set<Tree>

}
