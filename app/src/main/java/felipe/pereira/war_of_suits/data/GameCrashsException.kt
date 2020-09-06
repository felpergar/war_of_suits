package felipe.pereira.war_of_suits.data

class GameCrashsException : Exception() {
    
    override val message: String?
    get() = "It was not possible reset the round"
}