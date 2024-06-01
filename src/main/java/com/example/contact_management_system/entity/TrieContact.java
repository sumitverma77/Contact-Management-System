    package com.example.contact_management_system.entity;

    import com.example.contact_management_system.response.TrieAddResponse;
    import lombok.Getter;
    import lombok.Setter;

    import java.util.HashMap;
    public class TrieContact {
        @Getter
       private HashMap<Character ,TrieContact>name = new HashMap<>();
        @Getter
       private HashMap<Character , TrieContact> phone = new HashMap<>();
       public void setInPhoneMap(char key)
       {
           phone.putIfAbsent(key  , new TrieContact());
       }
       public void setInNameMap(char key)
       {
          name.putIfAbsent(key ,new TrieContact());
       }
       public TrieContact getNameNext(char key)
       {
           return name.get(key);
       }
        public TrieContact getPhoneNext(char key)
        {
            return phone.get(key);
        }

      @Getter
      @Setter
      private   String phoneInEnd;
       @Getter
       @Setter
       private  String nameInEnd;
        public boolean isNameEnd=false;
        public boolean isPhoneEnd=false;
    }
