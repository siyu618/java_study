package com.study.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by tianyuzhi on 16/5/16.
 */
public class CacheTest {

    public static void main(String[] args) {
        // create a cache for employees based on their employee id
        LoadingCache<String, Employee> employeeLoadingCache =
                CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(30, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Employee>() {
                    @Override
                    public Employee load(String empId) throws Exception {
                        // make the expensive call
                        return getFromDatabase(empId);
                    }
                });

        try {
            // get data form db call
            System.out.println("Invocation #1");
            System.out.println(employeeLoadingCache.get("100"));
            System.out.println(employeeLoadingCache.get("103"));
            System.out.println(employeeLoadingCache.get("110"));

            // get record from cache
            System.out.println("Invocation #1");
            System.out.println(employeeLoadingCache.get("100"));
            System.out.println(employeeLoadingCache.get("103"));
            System.out.println(employeeLoadingCache.get("110"));

        } catch (ExecutionException e) {

        }
    }

    private static Employee getFromDatabase(String emplId) {
        Employee e1 = new Employee("Mahesh", "Finance", "100");
        Employee e2 = new Employee("Rohan", "ID", "103");
        Employee e3 = new Employee("Sohan", "Admin", "110");
        Map<String, Employee> db = new HashMap<>();
        db.put("100", e1);
        db.put("103", e2);
        db.put("110", e3);
        System.out.println("Database hit for " + emplId);
        return db.get(emplId);
    }

}
@Getter
@Setter
class Employee {
    String name;
    String dept;
    String emplID;
    public Employee(String name, String dept, String emplId) {
        this.name = name;
        this.dept = dept;
        this.emplID = emplId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                ", emplID='" + emplID + '\'' +
                '}';
    }
}

