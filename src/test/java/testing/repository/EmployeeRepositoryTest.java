package testing.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import testing.entity.Employee;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//it indicates that the test should not replace the configured data source with an embedded or in-memory database.
class EmployeeRepositoryTest {

    @Mock
    private EmployeeRepository employeeRepository;
    //using mock we can perform the operations as db but not in real(just mocking)

    private Employee employee;


    @BeforeEach
        //during every single test it is executed before a test
    void setUp() {
        employee = Employee.builder()
                .id(111)
                .name("testName")
                .email("test@gmail.com")
                .phoneNum("986717291")
                .status(false)
                .build();
    }

    @Test
    void saveEmployee() {
        //for save we dont need to use when condition of mockito
        employeeRepository.save(employee);
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
        Assertions.assertThat(employeeOptional.isPresent());
    }

    @Test
    void getListofEmployees() {
        employeeRepository.save(employee);
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));
        List<Employee> employees = employeeRepository.findAll();
        Assertions.assertThat(employees.size()).isPositive(); //isGreaterThan(0) too
    }

    @Test
    void updateEmployee() {
        //first saving the employee
        employeeRepository.save(employee);
        //find the employee witht the id
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        employee.setName("update");
        employee.setEmail("update@gmail.com");
        //update the field
        when(employeeRepository.save(employee)).thenReturn(employee);
        //saving the changes as in new obj to check
        Employee employee1 = employeeRepository.save(employee);
        Assertions.assertThat(employee1.getName()).isEqualTo("update");
        Assertions.assertThat(employee1.getEmail()).isEqualTo("update@gmail.com");

    }
    @Test
    void deletById(){
        employeeRepository.save(employee);
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
        Assertions.assertThat(employeeOptional).isEmpty();

    }

    @AfterEach
        //after each test it is executed
    void tearDown() {
        System.out.println("Tearing down");
        employeeRepository.deleteAll();

    }


}