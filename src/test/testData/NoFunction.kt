class Person(var name: String, var age: Int) {
    init {
        name = "Jose"
        age = 32
    }
}

data class Singer(val name: String, val band: String, val instrument: String)

data class Worker(val name: String, val id: String)