package com.hangyi.eyunda.util;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * This class includes base functions of the dom4j
 * 
 * @author Malik Xu
 */
public class Dom4jUtil {
	private Document mydom;

	public Document getMydom() {
		return this.mydom;
	}

	public void setMydom(Document mydom) {
		this.mydom = mydom;
	}

	/**
	 * read a XML file, return a document object
	 * 
	 * @param fileName
	 * @return a Document object
	 */
	public Document readXmlFile(String fileName) {
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new FileInputStream(fileName));
			mydom = document;
			return document;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * analyse a string with XML format, return a document object
	 * 
	 * @param xmlStr
	 * @return a Document object
	 */
	public Document readXmlString(String xmlStr) {
		try {
			String text = xmlStr;
			Document document = DocumentHelper.parseText(text);

			mydom = document;
			return document;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * create a document object automatically
	 * 
	 * @return a Document object
	 */
	public Document createDocument() {
		try {
			Document document = DocumentHelper.createDocument();

			mydom = document;

			return document;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * clear the document object automatically
	 * 
	 * @return void
	 */
	public void clearContent() {
		try {
			mydom.clearContent();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * create root node of document
	 * 
	 * @param rootNodeName
	 * @param rootNodeText
	 * @return a Element object
	 */
	public Element addRootNode(String rootNodeName, String rootNodeText) {
		try {
			Element rootNode = mydom.addElement(rootNodeName);
			rootNode.setText(rootNodeText);

			return rootNode;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * add a child node under parent node
	 * 
	 * @param node
	 *            parent node
	 * @param childNodeName
	 *            child node name
	 * @param childNodeText
	 *            child node text
	 * @return a Element object as child node
	 */
	public Element addChildNode(Element node, String childNodeName, String childNodeText) {
		try {
			Element childNode = node.addElement(childNodeName);
			childNode.setText(childNodeText);

			return childNode;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * set a node text
	 * 
	 * @param node
	 *            given node
	 * @param nodeText
	 *            text value of given node
	 * @return true if success,false if failure
	 */
	public boolean setNodeText(Element node, String nodeText) {
		try {
			node.setText(nodeText);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * delete root node of the document
	 * 
	 * @return true if success,false if failure
	 */
	public boolean removeRootNode() {
		try {
			Element rootElement = mydom.getRootElement();

			return mydom.remove(rootElement);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * delete a child node under a parent node
	 * 
	 * @param parentElement
	 *            parent node
	 * @param childElement
	 *            child node
	 * @return true if success,false if failure
	 */
	public boolean removeChildNode(Element parentElement, Element childElement) {
		try {
			return parentElement.remove(childElement);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * get root node of the document
	 * 
	 * @return the root node
	 */
	public Element getRootNode() {
		try {
			Element rootNode = mydom.getRootElement();

			return rootNode;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * get a child node under a parent node
	 * 
	 * @param parentElement
	 *            a parent node
	 * @param childNodeName
	 *            the name of a child node
	 * @return a child node
	 */
	public Element getChildNode(Element parentElement, String childNodeName) {
		try {
			Element node = parentElement.element(childNodeName);

			return node;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * get the name string of a node
	 * 
	 * @param node
	 *            a given node
	 * @return the name string
	 */
	public String getNodeName(Element node) {
		try {
			String name = node.getName();

			return name;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * get the text value of a node
	 * 
	 * @param node
	 *            a given node
	 * @return the text value string
	 */
	public String getNodeText(Element node) {
		try {
			String text = node.getText();

			return text;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * get all children of a given parent node
	 * 
	 * @param node
	 *            a given node as parent
	 * @return a list of chilren under a parent node
	 */
	@SuppressWarnings("rawtypes")
	public List<Element> getAllChildrenNode(Element node) {
		try {
			List<Element> children = new ArrayList<Element>();
			if (node != null) {
				for (Iterator it = node.elementIterator(); it.hasNext();) {
					Element e = (Element) it.next();
					children.add(e);
				}
			}
			return children;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * get all children named as given string of a given parent node
	 * 
	 * @param node
	 *            a given node as parent
	 * @return a list of chilren under a parent node
	 */
	@SuppressWarnings("unchecked")
	public List<Element> getChildrenNode(Element node, String childNodeName) {
		try {
			List<Element> children = node.elements(childNodeName);
			return children;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * get a Attribute object named as given string of a given node
	 * 
	 * @param node
	 *            a given node
	 * @param attributeName
	 * @return a Attribute object
	 */
	public Attribute getAttribute(Element node, String attributeName) {
		try {
			Attribute attribute = node.attribute(attributeName);

			return attribute;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * get the name of a Attribute object
	 * 
	 * @param attribute
	 *            a given Attribute object
	 * @return the name string
	 */
	public String getAttributeName(Attribute attribute) {
		try {
			String attributeName = attribute.getName();

			return attributeName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * get the text value of a Attribute object
	 * 
	 * @param attribute
	 *            a given Attribute object
	 * @return the name string
	 */
	public String getAttributeText(Attribute attribute) {
		try {
			String attributeText = attribute.getText();

			return attributeText;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * get all Attribute of a node
	 * 
	 * @param node
	 *            a given node
	 * @return list of Attribute
	 */
	@SuppressWarnings("rawtypes")
	public List<Attribute> getAttributeList(Element node) {
		try {
			List<Attribute> attributeList = new ArrayList<Attribute>();
			if (node != null) {
				for (Iterator it = node.attributeIterator(); it.hasNext();) {
					Attribute attribute = (Attribute) it.next();
					attributeList.add(attribute);
				}
			}
			return attributeList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * add a attribute of a node
	 * 
	 * @param node
	 *            a given node
	 * @param attributeName
	 *            attribute name
	 * @param attributeText
	 *            attribute text
	 * @return true if success,false if failure
	 */
	public boolean addAttribute(Element node, String attributeName, String attributeText) {
		try {
			node.addAttribute(attributeName, attributeText);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * set the text of a attribute
	 * 
	 * @param attribute
	 *            a given attribute
	 * @param attributeText
	 *            the text value of a given attribute
	 * @return true if success,false if failure
	 */
	public boolean setAttributeText(Attribute attribute, String attributeText) {
		try {
			attribute.setText(attributeText);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * remove a attribute of a node
	 * 
	 * @param node
	 *            a given node
	 * @param attributeName
	 *            attribute name
	 * @return true if success,false if failure
	 */
	public boolean removeAttribute(Element node, String attributeName) {
		try {
			Attribute attribute = node.attribute(attributeName);

			return node.remove(attribute);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * write a xml file with the document object
	 * 
	 * @param fileName
	 *            given file path and name
	 * @return true if success,false if failure
	 */
	public boolean writeXmlFile(String fileName) {
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			XMLWriter writer = new XMLWriter(new FileWriter(fileName));
			writer.write(mydom);
			writer.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * write a xml string with the document object
	 * 
	 * @return the xml string
	 */
	public String getXmlString() {
		try {
			String xmlString = mydom.asXML();
			return xmlString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * write a xml string with sub-tree under a given node
	 * 
	 * @param node
	 *            a given node
	 * @return the xml string
	 */
	public String getXmlString(Element node) {
		try {
			String xmlString = node.asXML();
			return xmlString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * find and return the nodes under given node path
	 * 
	 * @param nodePath
	 *            given node path
	 * @return list of find nodes
	 */
	@SuppressWarnings("unchecked")
	public List<Element> selectNodes(String nodePath) {
		try {
			List<Element> l = mydom.selectNodes(nodePath);
			return l;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		try {
			Dom4jUtil domservice = new Dom4jUtil();
			domservice.readXmlFile("D:/happyness/temp/test.xml");
			domservice.writeXmlFile("D:/happyness/temp/test2.xml");

			String s = domservice.getXmlString();
			System.out.println(s);

			Element root = domservice.getRootNode();
			// System.out.println(metadata.getNodeText(root));

			List<Element> children = domservice.getAllChildrenNode(root);
			Iterator<Element> it = children.iterator();
			while (it.hasNext()) {
				Element e = it.next();
				System.out.println(e.getName() + "=" + e.getText());
			}
			
			/*List<Element> resultNodeList = metadata.selectNodes("/DRAWBACKAPI");
			Element resultNode = resultNodeList.get(0);
			System.out.println(resultNode.getName() + "=" + resultNode.getText());*/
			
			String s2 = domservice.getXmlString(root);
			System.out.println(s2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
