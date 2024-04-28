    package com.example.contact_management_system.service;

    import com.example.contact_management_system.constant.MessageConstant;
    import com.example.contact_management_system.request.AddRequest;
    import com.example.contact_management_system.entity.TrieNode;
    import com.example.contact_management_system.response.AddResponse;
    import org.springframework.stereotype.Service;

    @Service
    public class ContactServiceWithTrie {

        private TrieNode root;

        public ContactServiceWithTrie() {
            root = new TrieNode();
        }

        public AddResponse addService(AddRequest addRequest) {
            Boolean contactPresent = true;
          TrieNode  node = root;
            String name = addRequest.getName();
            String phone = addRequest.getPhone();
            for (int i = 0; i < phone.length(); i++) {
                if (!node.containsPhoneKey(phone.charAt(i))) {
                    node.putPhone(phone.charAt(i), new TrieNode());
                    contactPresent=false;
                }
                node = node.getNextPhoneLink(phone.charAt(i));
            }
            if(contactPresent) {
                AddResponse addResponse = new AddResponse();
                addResponse.setMsg(MessageConstant.alreadyPresent);
                return addResponse;
            }
            node.endOfPhone = true;
            node.phoneAtEnd = phone;
            node.nameAtEnd=name;
             node = root;

            for (int i = 0; i < name.length(); i++) {
                if (!node.containsNameKey(name.charAt(i))) //not present
                {
                    node.putName(name.charAt(i), new TrieNode());
                }
                    node = node.getNextNameLink(name.charAt(i));

            }
                node.endOfName = true;
                node.nameAtEnd =  name;
                node.phoneAtEnd=phone;

            AddResponse addResponse = new AddResponse();
            addResponse.setMsg(MessageConstant.savedSeccessfully);
            return addResponse;
        }
    }
