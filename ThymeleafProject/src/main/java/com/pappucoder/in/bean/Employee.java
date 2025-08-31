package com.pappucoder.in.bean;


public class Employee {
    private String employeeId;
    private String name;
    private String password;

    public Employee(String employeeId, String name, String password) {
        this.employeeId = employeeId;
        this.name = name;
        this.password = password;
    }

    public String getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public String getPassword() { return password; }
}
