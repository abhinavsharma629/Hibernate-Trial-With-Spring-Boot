package com.manager;

import com.manager.models.Employee;
import com.manager.utils.SessionUtil;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Scanner;

public class EmployeeManager {

    private static Session session = SessionUtil.getSession();;
    private static Configuration configuration = new Configuration();
    private static SessionFactory factory;
    private static Transaction tx = session.beginTransaction();;

    private static <T> List<T> loadAllEmployeeData(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        List<T> allEmployees = session.createQuery(criteria).getResultList();
        return allEmployees;
    }

    private static void askChoice(){
        System.out.print("\n1. Create Employee\n" +
                "2. Update all Employees\n" +
                "3. Update Employee by Id\n" +
                "4. Delete Employee - Warning: this cannot be recovered\n" +
                "5. Get all Employees\n" +
                "6. Get Employee by Id\n" +
                "7. Exit\n" +
                "Enter your choice (1-7): ");
    }

    private static void createOrUpdateEmployee(Employee employee, Scanner sc){
        System.out.print("Enter Employee First Name: ");
        String fName = sc.next();
        System.out.print("\nEnter Employee Last Name: ");
        String lName = sc.next();
        System.out.print("\nEnter Employee Age: ");
        int age = sc.nextInt();
        System.out.print("\nEnter Employee Designation: ");
        String designation = sc.next();
        System.out.print("\nEnter Employee DOB: ");
        String dob = sc.next();
        System.out.print("\nEnter Employee Salary: ");
        long salary = sc.nextLong();

        if(fName.length() != 0){
            employee.setfName(fName);
        }
        if(lName.length() != 0) {
            employee.setlName(lName);
        }
        if(String.valueOf(age).length() > 0 && String.valueOf(age).length() <= 2) {
            employee.setAge(age);
        }
        if(designation.length() != 0) {
            employee.setDesignation(designation);
        }
        if (dob.length() != 0) {
            employee.setDob(dob);
        }
        if(String.valueOf(salary).length() > 0) {
            employee.setSalary(salary);
        }
        session.save(employee);
    }

    public static void main(String args[]) {
        configuration.configure("hibernate.cfg.xml");
        factory = configuration.buildSessionFactory();

        Scanner sc = new Scanner(System.in);

        while (true) {
            askChoice();
            int choice = sc.nextInt();
            System.out.println();

            if (choice == 1) {
                System.out.println("Please Enter Employee Details:- ");
                Employee employee = new Employee();
                createOrUpdateEmployee(employee, sc);
                tx.commit();
                System.out.println("\nEmployee has been successfully created");
            } else if (choice == 2) {
                System.out.println("Enter Ids of Employee to Update Details:- ");
                while(true) {
                    System.out.print("\nEnter Employee Id: ");
                    int empId = sc.nextInt();
                    try{
                        Employee employee = session.load(Employee.class, empId);
                        System.out.println();
                        createOrUpdateEmployee(employee, sc);
                        tx.commit();
                        System.out.println("\nSuccessfully Updated Employee Details");
                    } catch(NullPointerException e) {
                        System.out.println("\nNo Such Employee Found");
                    }

                    System.out.print("\nPress Y to continue and N to break: ");
                    char ch = sc.next().charAt(0);
                    if(ch == 'N' || ch == 'n'){
                        break;
                    }
                }
            } else if (choice == 3) {
                System.out.print("Enter Id of Employee to Update Details: ");
                int empId = sc.nextInt();
                try {
                    Employee employee = session.load(Employee.class, empId);
                    createOrUpdateEmployee(employee, sc);
                    tx.commit();
                    System.out.println("\nSuccessfully Updated Employee Details");
                }
                catch(NullPointerException e){
                    System.out.println("\nNo Such Employee Found");
                }
            } else if (choice == 4) {
                System.out.print("Enter Id of employee to Deleting: ");
                int empId = sc.nextInt();
                try {
                    Employee employee = session.get(Employee.class, empId);
                    if(employee != null) {
                        System.out.print("\nAre You Sure You Want To Delete Employee ? You Won't be able to " +
                                "revert your changes later. Press Y / N: ");
                        char ch = sc.next().charAt(0);
                        if (ch == 'Y' || ch == 'y') {
                            session.delete(employee);
                            tx.commit();
                            System.out.println("\nSuccessfully Deleted Employee");
                        } else {
                            System.out.println("\nProcess Terminated by user");
                        }
                    }
                    else{
                        System.out.println("No Such Employee Found");
                    }
                }
                catch(NullPointerException e){
                    System.out.println("No Such Employee Found");
                }
            } else if (choice == 5) {
                try{
                    System.out.println(loadAllEmployeeData(Employee.class, session));
                }
                catch(NullPointerException e){
                    System.out.println("Table Not Yet Registered");
                }

            } else if (choice == 6) {
                System.out.print("Enter id of employee to Fetch Details: ");
                int empId = sc.nextInt();
                try {
                    Employee employee = session.load(Employee.class, empId);
                    System.out.println("\n" + employee.toString());
                }
                catch (NullPointerException e){
                    System.out.println("No Such Employee Found");
                }
            } else if (choice == 7)
                System.exit(0);
            else {
                System.out.println("\nEnter a Correct Choice Please");
            }
            System.out.println("\n------------------------------------");
        }
    }
}
