package com.study.guava;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.Map;
import java.util.Set;

/**
 * Created by tianyuzhi on 16/5/16.
 */
public class TableTest {
    public static void main(String[] args) {
        //Table<R,C,V> == Map<R,Map<C,V>>
      /*
      *  Company: IBM, Microsoft, TCS
      *  IBM 		-> {101:Mahesh, 102:Ramesh, 103:Suresh}
      *  Microsoft 	-> {101:Sohan, 102:Mohan, 103:Rohan }
      *  TCS 		-> {101:Ram, 102: Shyam, 103: Sunil }
      *
      * */
        Table<String, String, String> employeeTable = HashBasedTable.create();
        employeeTable.put("IBM", "101", "Mahesh");
        employeeTable.put("IBM", "102", "Ramesh");
        employeeTable.put("IBM", "103", "Suresh");

        employeeTable.put("MS", "101", "Sohan");
        employeeTable.put("MS", "102", "Mohan");
        employeeTable.put("MS", "103", "Rohan");

        employeeTable.put("TCS", "101", "Ram");
        employeeTable.put("TCS", "102", "Shyam");
        employeeTable.put("TCS", "103", "Sunil");

        Map<String, String> imbEmployees = employeeTable.row("IBM");
        System.out.println("List of IMB Employees");
        for (Map.Entry<String, String> entry : imbEmployees.entrySet()) {
            System.out.println("Emp ID:" + entry.getKey() + ", name:" + entry.getValue());
        }

        // get all the unique keys of the table
        Set<String> employers = employeeTable.rowKeySet();
        System.out.println("Employers:");
        for (String employer : employers) {
            System.out.print(employer + " ");
        }
        System.out.println();

        // get a Map corresponding to 102
        Map<String, String > employerMap = employeeTable.column("102");
        for (Map.Entry<String, String> entry : employerMap.entrySet()) {
            System.out.println("Employer:" + entry.getKey() + ", Name:" + entry.getValue());
        }

    }
}
