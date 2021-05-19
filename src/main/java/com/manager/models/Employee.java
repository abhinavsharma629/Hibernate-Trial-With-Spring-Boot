package com.manager.models;

import javax.persistence.*;

@Entity
public class Employee {
    @Id // primary key --> auto generated key
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 2)
    private int age;
    private String fName, lName, designation, dob;
    private long salary;

    public Employee(){
    }

    public Employee(String firstName, String lastName, String designation, String dob, int age, long salary){
        this.fName = firstName;
        this.lName = lastName;
        this.designation = designation;
        this.dob = dob;
        this.age = age;
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee {" +
                "id=" + id +
                ", age=" + age +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", designation='" + designation + '\'' +
                ", dob='" + dob + '\'' +
                ", salary=" + salary +
                " }";
    }
}
