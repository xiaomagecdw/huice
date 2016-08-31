package com.huice.uitl;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.huice.base.Locator;

public class XmlParser {
	
	private String filePath;
	private Document document;
	
	public XmlParser(String filePath){
		
		this.filePath = filePath;
		this.load();
	}
	
	private void load(){
		
		File file = new File(filePath);
		if(file.exists()){
			SAXReader rd = new SAXReader();
			try {
				this.document = rd.read(file);
			} catch (DocumentException e) {
				System.out.println("解析xml异常！");
			}
			
		}else{
			System.out.println("待解析xml文件不存在！");
		}
	}
	
	public Element getElement(String pathString){
		return (Element) document.selectSingleNode(pathString);
	}
	
	public String getElementText(String pathString){
		Element e = (Element) document.selectSingleNode(pathString);
		if(e !=null){
			return e.getTextTrim();
		}
		return null;
	}
	
	@SuppressWarnings(value = "unchecked")
	public List<Element> getElements(String pathString){
		return document.selectNodes(pathString);
	}
	
	public List<String> getElementTextList(String pathString){
		List<String> result = new ArrayList<String>();
		for(Object element : document.selectNodes(pathString)){
			result.add(((Element)element).getTextTrim());
		}
		return result;
	}
	
	public Map<String, String> getChildrenInfo(String elementPath){
		@SuppressWarnings("unchecked")
		List<Element> elements = this.getElement(elementPath).elements();
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		for(Element e: elements){
			map.put(e.getName(), e.getTextTrim());
		}
		return map;
		
	}
	
	public Map<String, String> getChildrenInfoByElement(Element elememt){
		@SuppressWarnings("unchecked")
		List<Element> elements = elememt.elements();
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		for(Element e: elements){
			map.put(e.getName(), e.getTextTrim());
		}
		return map;
	}
	
	public boolean isExist(String elementPath){
		
		boolean flag = false;
		Element element = this.getElement(elementPath);
		if(element != null){
			flag = true;
			
		}
		return flag;
	}

}
