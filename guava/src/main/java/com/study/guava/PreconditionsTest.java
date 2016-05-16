package com.study.guava;

import com.google.common.base.Preconditions;

/**
 * Created by tianyuzhi on 16/5/12.
 */
public class PreconditionsTest {
    public static void main(String[] args) {
        PreconditionsTest preconditionsTest = new PreconditionsTest();
        try {
            System.out.println(preconditionsTest.sqrt(-2.0));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(preconditionsTest.sum(null, 2));
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(preconditionsTest.getVal(6));
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

    }

    public double sqrt(double input) {
        Preconditions.checkArgument(input > 0.0,
                "Illegal Argument passe; Negative value %s.", input);
        return Math.sqrt(input);
    }

    public int sum(Integer a, Integer b) {
        a = Preconditions.checkNotNull(a, "Illegal Argument passed: First Argument is null");
        b = Preconditions.checkNotNull(a, "Illegal Argument passed: Second Argument is null");
        return a + b;
    }
    public int getVal(int input) {
        int[] data = {1,2,3,4,5};
        Preconditions.checkElementIndex(input, data.length, "Illegal Argument passed: Invalid index.");
        return 0;
    }
}
