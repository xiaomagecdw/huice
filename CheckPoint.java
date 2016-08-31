package com.huice.base;


import java.util.List;

import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;

public class CheckPoint extends Assertion {
	
	
	private int flag = 0;
	private String name = "";
	
	public CheckPoint(String name){
		this.name = name;
	}
	 
	
	@SuppressWarnings("rawtypes")
	@Override
	public void  onAssertFailure(IAssert  assertCommand){
		
		System.out.print("断言失败:实际结果"+assertCommand.getActual()+
					                                   "   预期结果"+assertCommand.getExpected()
					                                   );
			flag = flag + 1;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void onAssertSuccess(IAssert  assertCommand){
		
		System.out.println("断言失败:实际结果"+assertCommand.getActual()+
					                                   "   预期结果"+assertCommand.getExpected()
					                                   );
	}
	
	public void equals(boolean actual, boolean expected, String message){
		try {
			assertEquals(actual, expected,message);
		} catch (Error e) {
			
		}
	}
	
	public void assertEquals(String actual, String expected, String message){
		try {
			assertEquals(actual, expected,message);
		} catch (Error e) {
			
		}
	}
	
    public  void equals(int actual, int expected){
		
		try {
			assertEquals(actual, expected);
		} catch (Error e) {
//			System.out.println("断言失败:实际结果"+actual+
//					                                   "   预期结果"+expected);
		}
	}
    
    public void equalsNotNull(Object actual, String expected){
    	try {
			assertNotNull(actual, expected);
		} catch (Error e) {
			
		}
    }
    
    public void notEquals(String actual, String expected, String message){
    	try {
			assertNotEquals(actual, expected, message);
		} catch (Error e) {
			
		}
    }
    
    public void equals(List<String> actuals, String expected, String message){
    	if(actuals.size()!=0){
    		for(String actual:actuals){
                try {
			assertEquals(actual, expected,message);
		       } catch (Error e) {}
           }
       }else System.out.println("检查点函数：实际结果 集合 对象为空");
  }
    
    public void assertNotEquals(List<String> actuals, String expected, String message){
    	if(actuals.size()!=0){
    		for(String actual:actuals){
    			try {
					this.assertNotEquals(actual, expected, message);
				} catch (Error e) {
				}
    		}
    	}else System.out.println("检查点函数：实际结果 集合 对象为空");
    }
    
    public void assertContains(String actual, String expected, String message){
    	if(actual.contains(expected)){
    		equals(true, false, message);
    	}
    }
    
    public void result(String message){
//    	org.testng.Assert.assertEquals(flag, 0);
    	System.out.println("Report:" + message);
    	WebSuite.resultLog.add(name+ ":" +message);
    }
    
    public void initFlag(){
    	flag = 0;
    }
    
    public void isFailed(String message){
    	assertEquals(true,	false, message);
    	WebSuite.resultLog.add(name+ ":"+ message);
    }

//	public void equals(String actual, String expected ){
//		try {
//			assertEquals(actual, expected);
//		} catch(Error e) {
//			
//		}
//	}
//	
//	public void equals(List<String> actuals, String expected){
//		
//		for(String actual: actuals){
//			this.equals(actual, expected);
//		}
//		
//	}
//	
	
	
	
	
	
//	public void result(String message){
//		org.testng.Assert.assertEquals(flag,  0);
//		System.out.println(message);
////		assertEquals(flag,0);
//	}
//	
//	public void initFlag(){
//		flag = 0;
//	}

}
