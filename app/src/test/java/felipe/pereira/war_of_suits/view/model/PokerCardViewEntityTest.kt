package felipe.pereira.war_of_suits.view.model

import felipe.pereira.war_of_suits.common.shouldBe
import felipe.pereira.war_of_suits.domain.model.PokerCard
import felipe.pereira.war_of_suits.view.game.enums.CardValue
import felipe.pereira.war_of_suits.view.game.enums.Suit
import felipe.pereira.war_of_suits.view.game.model.transformToUi
import org.junit.Test

class PokerCardViewEntityTest {

    @Test
    fun `should return correct view object when transformToUi`() {
        val card = PokerCard(CardValue.TEN, Suit.CLUBS)

        val result = card.transformToUi()

        result.number shouldBe card.number
        result.suit shouldBe card.suit
    }
}