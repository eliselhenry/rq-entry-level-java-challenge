package com.challenge.api.service;

import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeModel;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeService {
    private final Map<UUID, EmployeeModel> employees = new LinkedHashMap<>();

    public EmployeeService() {
        mockEmployees();
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    public Employee getEmployeeByUuid(UUID uuid) {
        EmployeeModel employee = employees.get(uuid);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        }
        return employee;
    }

    public Employee createEmployee(EmployeeModel requestBody) {
        if (requestBody == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is required");
        }

        EmployeeModel employee = new EmployeeModel();
        employee.setUuid(UUID.randomUUID());
        employee.setFirstName(requestBody.getFirstName());
        employee.setLastName(requestBody.getLastName());
        employee.setFullName(buildFullName(requestBody.getFirstName(), requestBody.getLastName()));
        employee.setSalary(requestBody.getSalary());
        employee.setAge(requestBody.getAge());
        employee.setJobTitle(requestBody.getJobTitle());
        employee.setEmail(requestBody.getEmail());
        employee.setContractHireDate(Instant.now());
        employee.setContractTerminationDate(null);

        employees.put(employee.getUuid(), employee);
        return employee;
    }

    private void mockEmployees() {
        addEmployee("Elise", "Henry", 90000, 23, "Associate Software Engineer", "ehenry@reliaquest.com");
        addEmployee("John", "Smith", 110000, 34, "Engineering Manager", "jsmith@reliaquest.com");
        addEmployee("Kathy", "Hamilton", 275000, 47, "VP of Engineering", "khamilton@reliaquest.com");
    }

    private void addEmployee(
            String firstName, String lastName, Integer salary, Integer age, String jobTitle, String email) {
        EmployeeModel employee = new EmployeeModel();
        employee.setUuid(UUID.randomUUID());
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setFullName(buildFullName(firstName, lastName));
        employee.setSalary(salary);
        employee.setAge(age);
        employee.setJobTitle(jobTitle);
        employee.setEmail(email);
        employee.setContractHireDate(Instant.now());
        employee.setContractTerminationDate(null);

        employees.put(employee.getUuid(), employee);
    }

    private String buildFullName(String firstName, String lastName) {
        return firstName + " " + lastName;
    }
}
