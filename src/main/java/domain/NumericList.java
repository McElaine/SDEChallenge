package domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

/**
 * A generic list with a limited capacity N that stores numbers only, can
 * 	1. compute the moving average of all the added elements (max N), return in {code BigDecimal};
 * 	2. add finite numeric elements to the lsit;
 * 	3. can access to specific element or all elements in the list.
 *
 * @param <E> the type of elements in this list which must extend class {@code Number}.
 */
public interface NumericList<E extends Number> {

    /**
     * Add a new {@code BigDecimal} element to the list,
     * remove the least recently added element if list is full,
     * add to the list if not full.
     *
     * @param  value the {@code BigDecimal} being added to the list.
     * @throws NullPointerException if the value being add is null.
     */
    void add(E value);

    /**
     * Returns the average of the last n inserted elements with the provided precision.
     *
     * @return the average value in {@code BigDecimal} of all elements in the list.
     * @throws IllegalStateException if the list is empty.
     */
    BigDecimal getAverageWithPrecision(MathContext precision);

    /**
     * Returns the average of the elements in the list.
     *
     * @return  the average value in  {@code BigDecimal} of all elements in the list.
     * @throws IllegalStateException if the is empty.
     */
    BigDecimal getAverage();

    /**
     * Returns the nth added number in the list.
     *
     * @param n the ordering index of previous insertions.
     * @return the nth added number from the list.
     */
    E getNthElement(int n);

    /**
     * @return a {@code List} containing all elements in the list.
     */
    List<E> getAllElements();

}
