package testing.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import testing.entity.Employee;
import testing.repository.EmployeeRepository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    // mock instance of EmployeeRepository into the employeeRepository field of the EmployeeService
    private EmployeeService employeeService;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .id(11)
                .name("test")
                .email("test@gmail.com")
                .phoneNum("983838")
                .status(false)
                .build();
    }

    @Test
    @DisplayName("Testing for getting all employees")
    void getAll() {
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));

        List<Employee> employeeList = employeeService.getAllEmployee();

        for (Employee employee : employeeList) {
            System.out.println(employee.getId());
            System.out.println(employee.getName());
            System.out.println(employee.getEmail());
            System.out.println();
        }

        Assertions.assertThat(employeeList.size()).isPositive();
    }

    @Test
    @DisplayName("Testing for finding Employee with given id")
    void findByEmployeeId() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        Employee employeeServiceById = employeeService.findById(1);
        assertEquals(employee, employeeServiceById);

    }

    @Test
    @DisplayName("Testing for updating employee")
    void updateEmployee() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        Employee employeeServiceById = employeeService.findById(1);
        employeeServiceById.setName("updateTest");
        employeeServiceById.setEmail("updateTest@gmail.com");
        when(employeeRepository.save(employeeServiceById)).thenReturn(employee);
        Employee updatedEmployee = employeeRepository.save(employeeServiceById);
        assertEquals(updatedEmployee.getEmail(), "updateTest@gmail.com");
        assertEquals(updatedEmployee.getName(), "updateTest");
    }

    @Test
    @DisplayName("Testing for deleting an employee")
    void deleteById() {
        assertAll(() -> employeeRepository.deleteById(1));


    }

}
