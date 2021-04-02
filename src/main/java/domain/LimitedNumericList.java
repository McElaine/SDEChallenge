package domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code NumericList} implementation with limited capacity, supporting classes that extends {@code Number},
 * such as {@code Integer}, {@code Float}, {@code Double}, {@code BigDecimal}, {@code Short}, {@code Long}, etc.
 * <table border>
 * <caption><b>Provided Functions and descriptions
 * </b></caption>
 * <tr><th>Method</th><th>Description</th></tr>
 * <tr><td>Add</td><td> Add a new number.</td></tr>
 * <tr><td>getAverage</td><td> Calculate the average of last n inserted element.</td></tr>
 * <tr><td>getNthElement</td><td> Get the nth inserted element.</td></tr>
 * <tr><td>getAllElements</td><td> Get last n inserted elements.</td></tr>
 * </table>
 * <p>
 * Note: The return average value will be in {@code BigDecimal}, no matter which type is declared upon construction.
 * The purpose is to avoid overflow/underflow issues and ensure high precision.
 *
 * @author Shuonan Dong
 * @see BigDecimal
 * @see ArrayList
 * @see List
 */

public final class LimitedNumericList<E extends Number> implements NumericList<E> {

    private static final int DEFAULT_CAPACITY = 5;
    private static final MathContext PRECISION = MathContext.DECIMAL128;

    /**
     * The maximum number of elements can be stored in the cache.
     * <p>The capacity cannot be changed after initialization.
     */
    private final int capacity;

    /**
     * The {@code List} used to store the elements.
     */
    private ArrayList<E> list;

    /**
     * Creates a {@code NumericCacheImpl} with the default capacity (5).
     */
    public LimitedNumericList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates a {@code NumericCacheImpl} with the specified capacity.
     *
     * @param capacity the capacity for this cache.
     * @throws IllegalArgumentException if the {@code capacity} is less than 0.
     */
    public LimitedNumericList(int capacity) {
        if (capacity < 0)
            throw new IllegalStateException("The capacity of the cache must be greater than 0.");

        this.capacity = capacity;
        this.list = new ArrayList<>();
    }

    /**
     * Add a new {@code BigDecimal} element to the cache list.
     * <p>Remove the least recently added element if cache is full,
     * add to the cache list if not full.
     *
     * @param value the {@code BigDecimal} being added to the cache list.
     * @throws NullPointerException if the value being add is null.
     */
    public void add(E value) {

        // cannot insert null to the list
        if (value != null && isFiniteNumber(value)) {

            // if reach capacity, remove the oldest, append to tail
            if (this.list.size() == this.capacity) {
                this.list.remove(0);
                this.list.add(value);
            }

            // if not, append after the last added element
            else {
                this.list.add(value);
            }
        }
    }

    /**
     * Returns the average of the last n inserted elements with the provided precision.
     *
     * @return the average value in {@code BigDecimal} of all elements in the list.
     * @throws IllegalStateException if the list is empty.
     */
    public BigDecimal getAverageWithPrecision(MathContext precision) {

        // ensure the divider will not be 0
        if (this.list.isEmpty())
            throw new IllegalStateException("Please add numbers before calculating the average.");

        BigDecimal sum = this.list.stream()
                .map(value -> new BigDecimal(value.toString()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sum.divide(BigDecimal.valueOf(this.list.size()), precision);
    }

    /**
     * Returns the average of the elements in the cache list,
     * with the default precision {@code MathContext.DECIMAL128}
     *
     * @return the {@code BigDecimal} average value of all elements in the list.
     * @throws IllegalStateException if the list is empty.
     */
    public BigDecimal getAverage() {
        return getAverageWithPrecision(PRECISION);
    }

    /**
     * Returns the nth added number in the cache.
     *
     * @param n the ordering index of previous insertions.
     * @return the nth added number from the cache.
     * @throws IndexOutOfBoundsException if the provided index is less than zero or larger than the numbers of elements in the list
     */
    public E getNthElement(int n) {
        if (n < 0 || n > this.list.size())
            throw new IndexOutOfBoundsException("Provided index out of bound: " + n);
        return this.list.get(n-1);
    }

    /**
     * Returns all elements in the cache.
     *
     * @return a {@code List} containing all elements in the cache.
     */
    public List<E> getAllElements() {
        return this.list;
    }

    private boolean isFiniteNumber(E number) {
        // Double and Float may have NaN and Inf values.
        if (number instanceof Double) {
            Double num = (Double) number;
            return !num.isInfinite() && !num.isNaN();
        } else if (number instanceof Float) {
            Float num = (Float) number;
            return !num.isInfinite() && !num.isNaN();
        }
        return true;
    }

}
