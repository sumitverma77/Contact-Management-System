package com.example.contact_management_system.builder;

import com.example.contact_management_system.request.AddRequest;
import com.example.contact_management_system.entity.EmployeeContactDetails;

public class DTOConverter {
    public static EmployeeContactDetails convertAddRequestToEntity(AddRequest addRequest) {
        EmployeeContactDetails newContact = new EmployeeContactDetails();
        newContact.setName(addRequest.getName());
        newContact.setPhone(addRequest.getPhone());
        return newContact;
    }

}
