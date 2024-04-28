package com.example.contact_management_system.entity;

public class TrieNode {
  TrieNode[] name = new TrieNode[26];
  TrieNode[] phone = new TrieNode[10];
  public boolean endOfName=false;
  public boolean endOfPhone=false;
  public String nameAtEnd;
  public String phoneAtEnd;

  public boolean containsPhoneKey(char key)
  {
    return phone[key-'0']!=null;
  }
  public boolean containsNameKey(char key)
  {
    return name[key-'a']!=null;
  }
  public void putName(char nameKey, TrieNode node )
  {
    name[nameKey-'a']=node;
  }
  public void putPhone(char phoneKey , TrieNode node)
  {
    phone[phoneKey-'0']=node;
  }
  public TrieNode getNextPhoneLink(char key)
  {
    return phone[key-'0'];
  }
  public TrieNode getNextNameLink(char key)
  {
    return name[key-'a'];
  }
  boolean isPresent(char key)
  {
    return endOfPhone;
  }
}
