package testing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import testing.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {


}
