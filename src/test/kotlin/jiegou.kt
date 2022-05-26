data class Person(var name: String, var age: Int)

class Book(){
    operator fun component1() = 123
    operator fun component2() = 345
}

fun main() {

    val (x, y) = Book()
    println(x)
    println(y)
}
