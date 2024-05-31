package com.example.contact_management_system.service;

import com.example.contact_management_system.builder.DTOConverter;
import com.example.contact_management_system.constant.messageConstant;
import com.example.contact_management_system.entity.TrieContact;
import com.example.contact_management_system.request.*;
import com.example.contact_management_system.response.AddResponse;
import com.example.contact_management_system.response.DeleteResponse;
import com.example.contact_management_system.entity.EmployeeContactDetails;
import com.example.contact_management_system.repo.ContactRepo;
import com.example.contact_management_system.response.TrieAddResponse;
import com.example.contact_management_system.response.TrieSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContactService {
    @Autowired
    private ContactRepo contactRepo;
    private TrieContact root = new TrieContact();
    public AddResponse  addService(AddRequest addRequest)
    {
        AddResponse addResponse = new AddResponse();
        Optional<EmployeeContactDetails> employeeContactDetails= Optional.ofNullable(contactRepo.findByPhone(addRequest.getPhone()));
        if(employeeContactDetails.isPresent())
        {
            addResponse.setId(employeeContactDetails.get().getId());
            addResponse.setMsg(messageConstant.alreadyPresent);
        }
        else {
            EmployeeContactDetails newContact= DTOConverter.convertAddRequestToEntity(addRequest);
           Optional<EmployeeContactDetails> savedDetails= Optional.of(contactRepo.save(newContact));
            addResponse.setId(savedDetails.get().getId());
       addResponse.setMsg(messageConstant.savedSeccessfully);
        }
        return addResponse;
    }
    public DeleteResponse deleteService(DeleteRequest deleteRequest)
    {
        DeleteResponse deleteResponse = new DeleteResponse();
        Optional<EmployeeContactDetails>    employeeContactDetails=contactRepo.findById(deleteRequest.getId());
          if(employeeContactDetails.isPresent()) {
              contactRepo.deleteById(deleteRequest.getId());
              deleteResponse.setMsg(messageConstant.deltedsuccessfully);
          }
          else {
              deleteResponse.setMsg(messageConstant.contactNotPresent);
        }
  return deleteResponse;
    }
public List<EmployeeContactDetails> searchByName(SearchByNameRequest searchByNameRequest)
{
    List<EmployeeContactDetails> employeeContactDetails = contactRepo.findAllByNameStartingWith(searchByNameRequest.getPrefix());
    return employeeContactDetails;
}
public  List<EmployeeContactDetails> searchByPhone(SearchByPhoneRequest searchByPhoneRequest) {
    List<EmployeeContactDetails> employeeContactDetails=contactRepo.findAllByPhoneStartingWith(searchByPhoneRequest.getPrefix());
    return  employeeContactDetails;
}
public List<EmployeeContactDetails> searchByBoth(SearchByBothRequest searchByBothRequest)
{
    List<EmployeeContactDetails> allContactsByPhone= contactRepo.findAllByPhoneStartingWith(searchByBothRequest.getPrefix());
    List<EmployeeContactDetails> allContactsByName=contactRepo.findAllByNameStartingWith(searchByBothRequest.getPrefix());
    Set<EmployeeContactDetails>set = new HashSet<>();
    set.addAll(allContactsByPhone);
    set.addAll(allContactsByName);
    List<EmployeeContactDetails>uniqueContacts= new ArrayList<>(set);
    return uniqueContacts;
}
public TrieAddResponse addInTrie(AddRequest addRequest)
{

    TrieAddResponse trieAddResponse = new TrieAddResponse();
    String phone = addRequest.getPhone();
    String name = addRequest.getName();
    TrieContact curr = root;

    for(int i=0;i<phone.length();i++)
    {
       curr.setInPhoneMap(phone.charAt(i));
       curr=curr.getPhoneNext(phone.charAt(i));
    }
    if(curr.isPhoneEnd==true)
    {
        trieAddResponse.setMsg(messageConstant.alreadyPresent);
        return trieAddResponse;
    }
    curr.isPhoneEnd=true;
    curr.setNameInEnd(name);
    curr.setPhoneInEnd(phone);
    curr=root;
   for(int i=0;i< name.length();i++)
   {
       curr.setInNameMap(name.charAt(i));
       curr=curr.getNameNext(name.charAt(i));
   }
   curr.setPhoneInEnd(phone);
   curr.setNameInEnd(name);
   curr.isNameEnd=true;
   trieAddResponse.setMsg(messageConstant.savedSeccessfully);
   return trieAddResponse;
}

public List<TrieSearchResponse> search(SearchByBothRequest searchByBothRequest)
{
    List<TrieSearchResponse> results = new ArrayList<>();
    String prefix = searchByBothRequest.getPrefix();
    TrieContact curr= root;
    for(int i=0;i<prefix.length();i++)
    {
       TrieContact next=curr.getPhoneNext(prefix.charAt(i));
        curr=next;
        if(next==null)
            break;
    }
    if(curr!=null)
    {
        searchInPhoneMap(curr, results);
    }
    curr=root;
    for(int i=0;i<prefix.length();i++)
    {
        TrieContact next=curr.getNameNext(prefix.charAt(i));
        curr=next;
        if(next==null)
            break;
    }
    if(curr!=null)
    {
        searchInNameMap(curr , results);
    }
    return results;
}
    private void searchInPhoneMap(TrieContact node , List<TrieSearchResponse> result) {
        if (node.isPhoneEnd) {
            TrieSearchResponse trieSearchResponse = new TrieSearchResponse();
            trieSearchResponse.setName(node.getNameInEnd());
            trieSearchResponse.setPhone(node.getPhoneInEnd());
            result.add(trieSearchResponse);
        }
        for (Character key : node.getPhone().keySet()) {
            TrieContact nextNode = node.getPhone().get(key);
            searchInPhoneMap(nextNode, result);
        }
    }
    private void searchInNameMap(TrieContact node , List<TrieSearchResponse> result) {
        if (node.isNameEnd) {
            TrieSearchResponse trieSearchResponse = new TrieSearchResponse();
            trieSearchResponse.setName(node.getNameInEnd());
            trieSearchResponse.setPhone(node.getPhoneInEnd());
            result.add(trieSearchResponse);
        }
        for (Character key : node.getName().keySet()) {
            TrieContact nextNode = node.getName().get(key);
            searchInNameMap(nextNode, result);
        }
    }
}
