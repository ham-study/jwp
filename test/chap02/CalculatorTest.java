package chap02;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    private Calculator calculator;

    @Before
    public void setup() {
        calculator = new Calculator();
        System.out.println("setup");
    }

    @After
    public void teardown() {
        System.out.println("teardown");
    }

    @Test
    public void add() {
        assertEquals(9, calculator.add(6, 3));
    }

    @Test
    public void subtract() {
        assertEquals(3, calculator.subtract(6, 3));
    }
}