package com.huice.listener;

import java.util.Iterator;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

public class ExecutionListener implements ITestListener{
	
	@Override
	public void onFinish(ITestContext context){
		
		Iterator<ITestResult> listOfFailedTests = context.getFailedTests().getAllResults().iterator();
		
		while(listOfFailedTests.hasNext()){
			ITestResult failedTest = listOfFailedTests.next();
			ITestNGMethod method = failedTest.getMethod();
			
			if(context.getFailedTests().getResults(method).size()>1){
				listOfFailedTests.remove();
			}
		}
		
		System.out.println(context.getPassedTests().size());
		System.out.println(context.getSkippedTests().size());
		System.out.println(context.getFailedTests().size());
	}
	
	@Override
	public void onStart(ITestContext context){
		
		System.out.println("测试开始执行，为所有测试用例添加失败重跑机制");
		for(ITestNGMethod method: context.getAllTestMethods()){
			method.setRetryAnalyzer(new TestRetryAnalyzer());
			System.out.println(method.getMethodName()+" ->set retry");
		}
	}
	
	
	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0){
		
		
	}
	
	@Override
	public void onTestFailure(ITestResult result){
		
	}
	
	@Override
	public void onTestSkipped(ITestResult arg0){
		
	}
	
	@Override
	public void onTestStart(ITestResult arg0){
		System.out.println("TestStart!");
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
