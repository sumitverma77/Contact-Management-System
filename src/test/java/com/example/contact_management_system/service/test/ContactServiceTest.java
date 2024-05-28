package com.example.contact_management_system.service.test;
import com.example.contact_management_system.builder.DTOConverter;
import com.example.contact_management_system.constant.messageConstant;
import com.example.contact_management_system.request.AddRequest;
import com.example.contact_management_system.entity.EmployeeContactDetails;
import com.example.contact_management_system.repo.ContactRepo;
import com.example.contact_management_system.request.DeleteRequest;
import com.example.contact_management_system.service.ContactService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
//@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactServiceTest {
    @Autowired
    ContactService contactService;
    @MockBean
    private ContactRepo contactRepo;
    @MockBean
    private EmployeeContactDetails employeeContactDetails;
    @Test
  public void addServiceWhenContactAlreadyPresent()
    {
        EmployeeContactDetails employeeContactDetails = new EmployeeContactDetails();
        employeeContactDetails.setPhone("843984989428");
        employeeContactDetails.setName("raj");
        employeeContactDetails.setId(Long.valueOf("2"));

        AddRequest addRequest= new AddRequest();
        addRequest.setName("raj");
        addRequest.setPhone("843984989428");
        when(contactRepo.findByPhone(addRequest.getPhone())).thenReturn(employeeContactDetails);
        assertEquals(messageConstant.alreadyPresent ,contactService.addService(addRequest).getMsg());
    }
    @Test
    public void addServiceWhenContactNotPresent()
    {
        AddRequest addRequest= new AddRequest();
        addRequest.setName("raj");
        addRequest.setPhone("843984989428");
        when(contactRepo.findByPhone(addRequest.getPhone())).thenReturn(null);
        EmployeeContactDetails employeeContactDetails = new EmployeeContactDetails();
        employeeContactDetails.setPhone("843984989428");
        employeeContactDetails.setName("raj");
        employeeContactDetails.setId(Long.valueOf("2"));
        when(contactRepo.save(DTOConverter.convertAddRequestToEntity(addRequest))).thenReturn(employeeContactDetails);
        assertEquals(messageConstant.savedSeccessfully ,contactService.addService(addRequest).getMsg());
    }
    @Test
    public  void deleteServiceWhenContactPresent()
    {
        DeleteRequest deleteRequest=new DeleteRequest();
        deleteRequest.setId(2L);
        EmployeeContactDetails employeeContactDetails= new EmployeeContactDetails();
        employeeContactDetails.setId(2L);
        employeeContactDetails.setName("Sumit");
        employeeContactDetails.setPhone("8808630994");
        when(contactRepo.findById(deleteRequest.getId())).thenReturn(Optional.of(employeeContactDetails));
        assertEquals(messageConstant.deltedsuccessfully,contactService.deleteService(deleteRequest).getMsg());
    }
    @Test
    public  void deleteServiceWhenContactNotPresent()
    {
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.setId(2L);
        when(contactRepo.findById(deleteRequest.getId())).thenReturn(Optional.empty());
        assertEquals(messageConstant.contactNotPresent,contactService.deleteService(deleteRequest).getMsg());
    }


    }
