package com.operations.crud.controller.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.operations.crud.dto.EmployeeDto;
import com.operations.crud.dto.mapper.EmployeeMapper;
import com.operations.crud.repository.EmployeeRepository;
import com.operations.crud.service.EmployeeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private EmployeeMapper employeeMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserDetailsService userDetailsService;


    @Test
    @WithMockUser(username = "spring")
    public void allEmployees() throws Exception {
        List<EmployeeDto> dtoList = new ArrayList<>();
        EmployeeDto employeeDto = new EmployeeDto(1l, "john", "doe", "male",
                21, 2343, "jo@do.com");
        dtoList.add(employeeDto);

        Mockito.when(employeeService.allFilteredEmployee()).thenReturn(dtoList);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/employees/")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();

        String expected = this.mapToJson(dtoList);
        String actual = result.getResponse().getContentAsString();
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

    }

    @Test
    @WithMockUser(username = "spring")
    public void addEmployee() throws Exception {
        EmployeeDto employeeDto = new EmployeeDto(1l, "john", "doe", "male",
                21, 2343, "jo@do.com");
        String expected = this.mapToJson(employeeDto);

        Mockito.when(employeeService.createEmployee(Mockito.any(EmployeeDto.class))).thenReturn(employeeDto);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/employees/")
                .accept(MediaType.APPLICATION_JSON)
                .content(expected)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }


    @Test
    @WithMockUser(username = "spring")
    public void deleteEmployee() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/employees/0")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "spring")
    public void updateEmployee() throws Exception {
        EmployeeDto employeeDto = new EmployeeDto(1l, "john",
                "doe", "male", 21, 2343, "jo@do.com");
        String expected = this.mapToJson(employeeDto);

        Mockito.when(employeeService.updateEmployee(Mockito.any(EmployeeDto.class))).thenReturn(employeeDto);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("http://localhost:8008/api/employees/0")
                .accept(MediaType.APPLICATION_JSON)
                .content(expected)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(HttpStatus.ACCEPTED.value(), result.getResponse().getStatus());
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}