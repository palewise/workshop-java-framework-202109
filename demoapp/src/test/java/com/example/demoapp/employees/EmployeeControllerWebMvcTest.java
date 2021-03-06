package com.example.demoapp.employees;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerWebMvcTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void success_getEmployeeById() throws Exception, EmployeeNotFoundException {
        int id=100;

        // Arrange
        EmployeeResponse mockResponse = new EmployeeResponse();
        mockResponse.setId(100);
        mockResponse.setName("Napat");
        when(employeeService.getById(100)).thenReturn(mockResponse);

        // Act
        MvcResult result = mvc.perform(get("/employees/"+id)).andExpect(status().isOk()).andReturn();

        // Response body
        byte[] json=result.getResponse().getContentAsByteArray();
        ObjectMapper mapper=new ObjectMapper();
        EmployeeResponse response =mapper.readValue(json,EmployeeResponse.class);

        // Assert
        assertEquals(id,response.getId());
        assertEquals("Napat",response.getName());
    }
}