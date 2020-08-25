package felipe.pereira.war_of_suits.common

import org.junit.Assert

infix fun <E> E.shouldBe(expected: E) = Assert.assertEquals(expected, this@shouldBe)