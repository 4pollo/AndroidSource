package com.example.xml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class SaxPrase {

	public List<PersonInfo> getPersons(InputStream inStream) throws Throwable{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		PersonParser personParser = new PersonParser();
		parser.parse(inStream, personParser);
		inStream.close();
		return personParser.getPersons();
	}

	private final class PersonParser extends DefaultHandler{
		private List<PersonInfo> persons = null;
		private String tag = null;
		private PersonInfo person = null;

		public List<PersonInfo> getPersons() {
			return persons;
		}

		@Override
		public void startDocument() throws SAXException {
			persons = new ArrayList<PersonInfo>();
		}
		
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if("person".equals(localName)){
				person = new PersonInfo();
				person.setId(new Integer(attributes.getValue(0)));
			}
			tag = localName;
		}
		
		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			if(tag!=null){
				String data = new String(ch, start, length);//获取文本节点的数据
				if("name".equals(tag)){
					person.setName(data);
				}else if("age".equals(tag)){
					person.setAge(data);
				}
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if("person".equals(localName)){
				persons.add(person);
				person = null;
			}
			tag = null;
		}
	}
}
