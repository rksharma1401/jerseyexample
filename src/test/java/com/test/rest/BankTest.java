/**
 * 
 */
package com.test.rest;

import static org.junit.Assert.*;

import org.junit.Test;

import mockit.Mock;
import mockit.MockUp;

/********** DBManager.java **************/
 class DBManager {
  
 public static String getConnectionString(){
  return "ORIGINAL";
 }
}
 
/********** Bank.java **************/
 class Bank {
  
 public String makeConnection(){
  //some connection related code
  //goes here
   
  // call to static method
  String conStr = DBManager.getConnectionString();
   
  // If the connection String 
  // is anything other than
  // ORIGINAL return FAIL 
  if(conStr.equals("ORIGINAL"))
   return "SUCCESS";
  else
   return "FAIL";
 }
}
 
/********** BankTest.java **************/
public class BankTest {
 
 @Test
 public void testMakeConnectionWithMockUp(){
  new MockUp<DBManager>(){
    
   // Redefine the method here
   // But With No static modifier
   @Mock
   public String getConnectionString(){
    return "DUPLICATE";
   }
 
  };
   
  Bank bank =  new Bank();
  String status = bank.makeConnection();
   
   
  assertEquals("Status is FAIL","FAIL",status);
 }
}