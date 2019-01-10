package com.eai.wizard.utils;

import java.io.StringWriter;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class Xml {
	
	public String generate(List<String> nodo, List<String> value) {
    	try {
    		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		    DOMImplementation domImplementation = documentBuilder.getDOMImplementation();
	        Document document = domImplementation.createDocument(null, "data", null);
	        document.setXmlVersion("1.0");

	        Element raiz = document.getDocumentElement();
	       
	        for (int i = 0; i < nodo.size(); i++) {
	            Element node = document.createElement(nodo.get(i));
	            Text nodeValue = document.createTextNode(value.get(i));
	          
	            node.appendChild(nodeValue);
	            raiz.appendChild(node);
	        }
	        
	        DOMSource domSource = new DOMSource(document);
	        
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
	        Transformer transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        
	        StringWriter sw = new StringWriter();
	        StreamResult sr = new StreamResult(sw);
	        transformer.transform(domSource, sr);
	        
	        return sw.toString();  
	    } catch (Exception e) {
	    	return null;
	    }
	}
}
