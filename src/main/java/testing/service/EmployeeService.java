package testing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testing.entity.Employee;
import testing.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Autowired
    private EmployeeRepository employeeRepository;
    public List<Employee> getAllEmployee(){
        return this.employeeRepository.findAll();
    }

    public Employee findById(Integer id){
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        return employeeOptional.orElse(null);

    }

    public Employee saveEmployee(Employee employee){
       Employee employee1 = employeeRepository.save(employee);
        return employee1;
    }

}
