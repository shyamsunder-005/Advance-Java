// Employee class follows SRP because it is only responsible for storing employee data.
class Employee {
    String name;
    double salary;
    Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }
}
// PaySlipPrinter class is responsible for printing an employee's payslip so it follows SRP.
class PaySlipPrinter {
    public void printPaySlip(Employee employee) {
        System.out.println("Employee: " + employee.name + ", Salary: $" + employee.salary);
    }
}
// This ensures that different storages can be implemented without modifying existing code (OCP).
interface EmployeeStorage {
    void store(Employee employee); 
}
class Database implements EmployeeStorage {
    public void store(Employee employee) { 
        System.out.println("Saving employee to Database...");
    }
}
// This class follows ocp
class FileStorage implements EmployeeStorage {
    public void store(Employee employee) {
        System.out.println("Saving employee to File...");
    }
}
public class Employees {
    public static void main(String[] args) {
        Employee employee = new Employee("Shyam", 100000);
        EmployeeStorage Databasestorage = new Database(); 
        Databasestorage .store(employee); 
        PaySlipPrinter printer = new PaySlipPrinter();
        printer.printPaySlip(employee);
		EmployeeStorage filestorage = new FileStorage(); 
        filestorage.store(employee); 
    }
}
