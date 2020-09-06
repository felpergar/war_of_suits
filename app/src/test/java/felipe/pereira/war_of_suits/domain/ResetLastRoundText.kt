package felipe.pereira.war_of_suits.domain

import androidx.core.widget.TextViewCompat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import felipe.pereira.war_of_suits.domain.usecase.Game
import felipe.pereira.war_of_suits.domain.usecase.ResetLastRound
import org.junit.Before
import org.junit.Test

class ResetLastRoundText {

    private lateinit var useCase: ResetLastRound
    private lateinit var game: Game

    @Before
    fun `set up`() {
        game = mock()
        useCase = ResetLastRound(game, mock(), mock())
    }

    @Test
    fun `should execute resetLastRound in game when useCase is executed`() {

        useCase.buildUseCaseSingle(Unit).test()

        verify(game).resetLastRound()
    }
}