package felipe.pereira.war_of_suits.data

class GameCrashedException : Exception() {
    
    override val message: String?
    get() = "It was not possible reset the round"
}