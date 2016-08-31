package com.huice.cases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class base {
	
	@BeforeSuite
	public void beforeSuite(){
		System.out.println("befreSuite");
	}
	
	@AfterSuite
	public void afterSuite(){
		System.out.println("afterSuite");
	}
	
	@BeforeTest
	public void beforeTest(){
		System.out.println("beforeTest");
	}
	
	@AfterTest
	public void afterTest(){
		System.out.println("afterTest");
	}
	
	@BeforeGroups
	public void beforeGruops(){
		System.out.println("beforeGruops");
	}
	
	@AfterGroups
	public void afterGruops(){
		System.out.println("afterGruops");
	}
	
	@BeforeClass
	public void beforeClass(){
		System.out.println("beforeClass");
	}
	
	@AfterClass
	public void afterClass(){
		System.out.println("afterClass");
	}
	
	@BeforeMethod
	public void beforeMethod(){
		System.out.println("beforeMethod");
	}
	
	@AfterMethod
	public void afierMethod(){
		System.out.println("afterMethod");
	}

}
