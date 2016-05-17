package com.study.java8;

/**
 * Created by tianyuzhi on 16/5/17.
 */
public class LambdaTest {

    public static void main(String[] args) {
        LambdaTest test = new LambdaTest();
        // with type declaration
        MathOperation addition = (int a, int b) -> a+ b;
        // without declaration
        MathOperation subtraction = (a,b) -> a-b;
        // with return statement along with curly braces
        MathOperation multiplication = (int a, int b) -> {return  a * b;};
        // without return statement and without curly braces
        MathOperation division = (int a, int b) -> a/b;
        System.out.println("10 + 5 = " + test.operate(10,5,addition));
        System.out.println("10 - 5 = " + test.operate(10,5,subtraction));
        System.out.println("10 * 5 = " + test.operate(10,5,multiplication));
        System.out.println("10 / 5 = " + test.operate(10,5,division));

        // without parenthesis
        GreetingService greetingService1 = message -> System.out.println("Hello " + message);
        // with parenthesis
        GreetingService greetingService2 = (message) -> System.out.println("Hello " + message);

        greetingService1.sayMessage("Mahesh");
        greetingService2.sayMessage("Suresh");


        //
        salutation = "adafdfad";
        GreetingService greetingService3 = message -> System.out.println(salutation + message);
        greetingService3.sayMessage("Mahesh");


    }

     static String salutation = "Hello!";


    interface MathOperation {
        int operation(int a, int b);
    }
    interface GreetingService {
        void sayMessage(String message);
    }
    private int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }
}
