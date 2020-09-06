package felipe.pereira.war_of_suits.data

import felipe.pereira.war_of_suits.common.shouldBe
import org.junit.Test

class DeckProviderTest {

    private val deckProvider: DeckProvider = DeckProvider()

    @Test
    fun `getCardsShuffled should return 28 cards fir ecah player after execute createCards times`() {
        deckProvider.createCards()

        val result = deckProvider.getCardsShuffled()

        result.first.size shouldBe 28
        result.second.size shouldBe 28
    }

    @Test
    fun `getCardsShuffled should return 56 cards after execute createCards two times`() {
        deckProvider.createCards()
        deckProvider.createCards()

        val result = deckProvider.getCardsShuffled()
        (result.first.size + result.second.size) shouldBe 56
    }
}