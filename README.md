# PaytmLabs SDE Challenge - coding question

## Package description
This package contains an interface, and an implementation of a data structure 
that supports elements insertion, calculation of the moving average of the last N elements added, 
and access to the elements inserted.

Corresponding unit tests are also included with 100% method coverage and 93% line coverage. 

The implementation is built upon the Java 8 built-in classes: List and ArrayList. Therefore, the provided data structure allows access by 
index with O(1) time complexity, data insertion with O(1) time complexity, and moving average calculation with O(n) time complexity 
with O(1) additional space complexity.

The provided generic class supports classes that extends Number.class in Java, such as Integer, Float, Double, BigDecimal, Short, and Long, etc.

To ensure high-precision and prevent issues of overflow/underflow issues(such as Float and Double types), the calculation of the moving average 
are implemented with BigDecimal type. Default used precision is IEEE 754R Decimal128 format(34 digits). User can also provide desired precision as 
a parameter upon average calculation.
