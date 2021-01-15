import org.junit.jupiter.api.Test;
import java.util.EmptyStackException;
import static org.junit.jupiter.api.Assertions.*;

class ShuntingYardTest {
    //---infixToPostfix test cases---

    @Test
    public void exp1Shunt() {
        String expected = "4 -u 5 + ";
        String actual =
                ShuntingYard.infixToPostfix("-4+5".split("(?<=[-+*^√/()])|(?=[-+*^√/()])"));
        assertEquals(expected, actual);
    }

    @Test
    public void exp2Shunt() {
        String expected = "36 3 2 ^ / 5 1 - sin * ";
        String actual =
                ShuntingYard.infixToPostfix("36/3^2*sin(5-1)".split("(?<=[-+*^√/()])|(?=[-+*^√/()])"));
        assertEquals(expected, actual);
    }

    @Test
    public void exp3Shunt() {
        String expected = "15 5 - 5 - 5 - ";
        String actual =
                ShuntingYard.infixToPostfix("15-5-5-5".split("(?<=[-+*^√/()])|(?=[-+*^√/()])"));
        assertEquals(expected, actual);
    }

    @Test
    public void exp4Shunt() {
        String expected = "3 tan cos sin ";
        String actual =
                ShuntingYard.infixToPostfix("sin(cos(tan(3)))".split("(?<=[-+*^√/()])|(?=[-+*^√/()])"));
        assertEquals(expected, actual);
    }

    @Test
    public void exp5Shunt() {
        assertThrows(IndexOutOfBoundsException.class, () ->
                ShuntingYard.infixToPostfix("5*5+3√".split("(?<=[-+*^√/()])|(?=[-+*^√/()])")));
    }

    /**
     * postfixCalculate (ignores trailing whitespace in an expression)
     */
    @Test
    public void calc1() {
        String expected = "1.0";
        String actual = ShuntingYard.postfixCalculate("4 -u 5 +");
        assertEquals(expected, actual);
    }

    @Test
    public void calc2() {
        String expected = "0.2790258949765012";
        String actual = ShuntingYard.postfixCalculate("36 3 2 ^ / 5 1 - sin *");
        assertEquals(expected, actual);
    }

    @Test
    public void calc3() {
        String expected = "0.0";
        String actual = ShuntingYard.postfixCalculate("15 5 - 5 - 5 -");
        assertEquals(expected, actual);
    }

    @Test
    public void calc4() {
        String expected = "0.017452399137206503";
        String actual = ShuntingYard.postfixCalculate("3 tan cos sin");
        assertEquals(expected, actual);
    }

    /**
     * Divide by zero error.
     */
    @Test
    public void calc5() {
        assertThrows(ArithmeticException.class, () -> ShuntingYard.postfixCalculate("3 0 /"));
    }

    /**
     * Bad syntax error.
     */
    @Test
    public void cal6() {
        assertThrows(EmptyStackException.class, () -> ShuntingYard.postfixCalculate("+ 6 - -"));
    }

}