package domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class NumericListTest {

    @SuppressWarnings("rawtypes")
    private NumericList list;

    @Test
    public void initFailTest() {
        // setup

        // invocation & assertion
        assertThrows(IllegalStateException.class, () -> new LimitedNumericList<BigDecimal>(-1));
    }

    @Test
    public void getAverageOnEmptyListTest() {
        // setup
        list = new LimitedNumericList<BigDecimal>(5);

        // invocation & assertion
        assertThrows(IllegalStateException.class, () -> list.getAverage());
    }

    @Test
    public void getElementOutOfBoundTest() {
        // setup
        list = new LimitedNumericList<BigDecimal>(5);

        // invocation & assertion
        assertThrows(IndexOutOfBoundsException.class, () -> list.getNthElement(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.getNthElement(10));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void addUnderCapacityTest() {
        // setup
        list = new LimitedNumericList<BigDecimal>(5);

        // invocation
        list.add(BigDecimal.valueOf(9));
        list.add(BigDecimal.valueOf(8));

        // assertion
        assertTrue("Regular add fail", list.getAllElements().size() == 2);
        assertTrue("Regular add fail", list.getAllElements().contains(BigDecimal.valueOf(9)));
        assertTrue("Regular add fail", list.getAllElements().contains(BigDecimal.valueOf(8)));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void addExceedCapacityTest() {
        // setup
        list = new LimitedNumericList<BigDecimal>(5);

        // invocation
        list.add(BigDecimal.valueOf(9));
        list.add(BigDecimal.valueOf(5));
        list.add(BigDecimal.valueOf(3));
        list.add(BigDecimal.valueOf(4));
        list.add(BigDecimal.valueOf(2));
        list.add(BigDecimal.valueOf(1));

        // assertion
        assertTrue("Max size of the list reached", list.getAllElements().size() == 5);
        assertTrue("Early inserted elements are erased when reaching capacity",
                !list.getAllElements().contains(BigDecimal.valueOf(9)));
        assertTrue("Early inserted elements are erased by newly inserted one when reaching capacity",
                list.getAllElements().contains(BigDecimal.valueOf(1)));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void addNullFailTest() {
        // setup
        list = new LimitedNumericList<BigDecimal>(5);

        // invocation
        list.add(null);

        // assertion
        assertTrue("Null value cannot be inserted to the list",
                list.getAllElements().isEmpty());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void addInfiniteFailTest() {
        // setup
        list = new LimitedNumericList<Double>(5);

        // invocation
        list.add(Double.POSITIVE_INFINITY);

        // assertion
        assertTrue("Inf value cannot be inserted to the list",
                list.getAllElements().isEmpty());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void addNaNFailTest() {
        // setup
        list = new LimitedNumericList<Double>(5);

        // invocation
        list.add(Double.NaN);

        // assertion
        assertTrue("NaN cannot be inserted to the list",
                list.getAllElements().isEmpty());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getAverageRegularTest() {
        // setup
        list = new LimitedNumericList<BigDecimal>(5);
        list.add(BigDecimal.valueOf(9));
        list.add(BigDecimal.valueOf(8));
        list.add(BigDecimal.valueOf(7));
        list.add(BigDecimal.valueOf(6));

        // invocation
        BigDecimal result = list.getAverage(); // use default precision

        // assertion
        assertEquals("Calculated Average not correct", BigDecimal.valueOf(7.5), result);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getAverageWithMaxValueTest() {
        // setup
        list = new LimitedNumericList<Double>(5);
        list.add(BigDecimal.valueOf(Double.MAX_VALUE));
        list.add(BigDecimal.valueOf(8));
        list.add(BigDecimal.valueOf(7));
        list.add(BigDecimal.valueOf(6));

        // invocation
        BigDecimal result = list.getAverageWithPrecision(MathContext.DECIMAL32);

        // assertion
        assertEquals("Calculated Average not correct",
                BigDecimal.valueOf(4.494233E+307), result);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getNthInsertedElementTest() {
        // setup
        list = new LimitedNumericList<BigDecimal>(5);
        list.add(BigDecimal.valueOf(9));
        list.add(BigDecimal.valueOf(5));
        list.add(BigDecimal.valueOf(3));
        list.add(BigDecimal.valueOf(4));
        list.add(BigDecimal.valueOf(2));
        list.add(BigDecimal.valueOf(1));

        // invocation
        Number result = list.getNthElement(2);

        // assertion
        assertEquals("Calculated Average not correct",
                BigDecimal.valueOf(3), result);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getAllNInsertedElementTest() {
        // setup
        list = new LimitedNumericList<BigDecimal>();
        list.add(BigDecimal.valueOf(9));
        list.add(BigDecimal.valueOf(5));
        list.add(BigDecimal.valueOf(3));
        list.add(BigDecimal.valueOf(4));
        list.add(BigDecimal.valueOf(2));
        list.add(BigDecimal.valueOf(1));

        // invocation
        List<BigDecimal> result = list.getAllElements();

        // assertion
        assertTrue("Max size of the list reached", list.getAllElements().size() == 5);
        assertTrue("Get all elements tests fail", !list.getAllElements().contains(BigDecimal.valueOf(9)));
        assertTrue("Get all elements tests fail", list.getAllElements().contains(BigDecimal.valueOf(1)));
        assertTrue("Get all elements tests fail", list.getAllElements().contains(BigDecimal.valueOf(2)));
        assertTrue("Get all elements tests fail", list.getAllElements().contains(BigDecimal.valueOf(3)));
        assertTrue("Get all elements tests fail", list.getAllElements().contains(BigDecimal.valueOf(4)));
        assertTrue("Get all elements tests fail", list.getAllElements().contains(BigDecimal.valueOf(5)));
    }

}
