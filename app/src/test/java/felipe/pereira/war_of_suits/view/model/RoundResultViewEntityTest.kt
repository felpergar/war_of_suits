package felipe.pereira.war_of_suits.view.model

import felipe.pereira.war_of_suits.common.shouldBe
import felipe.pereira.war_of_suits.domain.model.PokerCard
import felipe.pereira.war_of_suits.domain.model.RoundResult
import felipe.pereira.war_of_suits.view.game.enums.CardValue
import felipe.pereira.war_of_suits.view.game.enums.Result
import felipe.pereira.war_of_suits.view.game.enums.Suit
import felipe.pereira.war_of_suits.view.game.model.transformToUi
import org.junit.Test

class RoundResultViewEntityTest {

    @Test
    fun `should return correct view object when transformToUi`() {
        val magnetoCard = PokerCard(CardValue.NINE, Suit.CLUBS)
        val professorCard = PokerCard(CardValue.J, Suit.DIAMONDS)
        val round = RoundResult(Result.MAGNETO, magnetoCard, professorCard)

        val result = round.transformToUi()

        result.winner shouldBe round.winner
        result.magnetoCard.number shouldBe round.magnetoCard.number
        result.magnetoCard.suit  shouldBe round.magnetoCard.suit
        result.professorCard.number shouldBe round.professorCard.number
        result.professorCard.suit shouldBe round.professorCard.suit
    }
}