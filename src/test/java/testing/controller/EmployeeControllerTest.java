package testing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import testing.service.EmployeeService;
import testing.entity.Employee;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    //for mocking the repo usage
    private EmployeeService employeeService;

    @Autowired
    //for serialization of obj to json
    private ObjectMapper objectMapper;


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
    @DisplayName("Saving an employee in database")
    void saveEmployee() throws Exception {
        given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class))).willReturn(employee);
        ResultActions response =
                mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        //changing the object into json for serialization
                        .content(objectMapper.writeValueAsString(employee)));
        //check if it meets with the HTTP response as in controller
        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void getAllEmployee() throws Exception {
        given(employeeService.getAllEmployee()).willReturn(Arrays.asList(employee));
        ResultActions response =
                mockMvc.perform(get("/employee"));
        response.andExpect(MockMvcResultMatchers.status().isAccepted());
    }
}