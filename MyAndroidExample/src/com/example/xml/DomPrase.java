package com.example.xml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomPrase {

	public List<PersonInfo> getPersons(InputStream inStream) throws Throwable {
		List<PersonInfo> person_list = new ArrayList<PersonInfo>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document documnet = builder.parse(inStream);
		Element root = documnet.getDocumentElement();
		NodeList personNodes = root.getElementsByTagName("person");
		for (int i = 0; i < personNodes.getLength(); i++) {
			PersonInfo person = new PersonInfo();
			Element personElement = (Element) personNodes.item(i);
			person.setId(new Integer(personElement.getAttribute("id")));
			NodeList personChilds = personElement.getChildNodes();
			for (int y = 0; y < personChilds.getLength(); y++) {
				if (personChilds.item(y).getNodeType() == Node.ELEMENT_NODE) {// �жϵ�ǰ�ڵ��Ƿ���Ԫ�����ͽڵ�
					Element childElement = (Element) personChilds.item(y);
					if ("name".equals(childElement.getNodeName())) {
						person.setName(childElement.getFirstChild()
								.getNodeValue());
					} else if ("age".equals(childElement.getNodeName())) {
						person.setAge(new String(childElement.getFirstChild()
								.getNodeValue()));
					}
				}
			}
			person_list.add(person);
		}
		return person_list;
	}
}
