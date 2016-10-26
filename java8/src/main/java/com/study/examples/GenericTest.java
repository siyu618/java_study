package com.study.examples;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by tianyuzhi on 16/10/25.
 */
public class GenericTest {
    @Getter @Setter
    public static class Pair<T> {
        private T first;
        private T second;

        public Pair(T first, T second) {
            this.first = first;
            this.second = second;
        }
    }

    @Getter
    public static class Employee {
        private String name;
        private double salary;
        public Employee(String name, double s) {
            this.name = name;
            this.salary = s;
        }
    }

    public static class Manager extends Employee {
        public Manager(String name, double s) {
            super(name, s);
        }

    }

    public static class President extends Employee {
        public President(String name, double s) {
            super(name, s);
        }
    }

    public static void printEmployeeBoddies(Pair<Employee> pair) {
        System.out.println(pair.getFirst().getName() + ":" + pair.getSecond().getName());
    }

    public static void main(String[] args) {
        Employee zhangsan = new Employee("zhangsan", 1000);
        Employee lisi = new Employee("lisi", 10000);
        Manager m1 = new Manager("jack", 1000);
        Manager m2 = new Manager("tom", 100);

        Pair<Employee> employeePair = new Pair<>(zhangsan, lisi);
        printEmployeeBoddies(employeePair);

        Pair<Manager> managerPair = new Pair<>(m1, m2);
       // printEmployeeBoddies(managerPair); // will fail

        Pair<? extends Employee> pair = managerPair;
        //pair.setFirst(new Employee("test", 1)); // this will fail
        Pair<? extends Employee> pair1 = employeePair;// this also is right
    }

}
