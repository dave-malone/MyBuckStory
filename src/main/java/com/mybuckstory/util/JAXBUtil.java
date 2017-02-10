package com.mybuckstory.util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;

import org.w3c.dom.Node;

/**
 * Tools for working with the JAXB (XML Binding) library.
 */
public class JAXBUtil{

	
	public static <T> T fromXML(String xml, Class<? extends T> rootElementClass) throws JAXBException{
		Reader reader = new StringReader(xml);
		return (T) parseXML(reader, rootElementClass);
	}
	
	public static String toXml(Object rootElement) throws JAXBException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Writer writer = new BufferedWriter(new OutputStreamWriter(baos));
		generateXML(rootElement, writer);
		return baos.toString();
	}
	
	/**
	 * Parse the XML supplied by the reader into the corresponding tree of Java
	 * objects.
	 * @param <E>
	 * 
	 * @param reader
	 *            Cannot be null. The source of the XML.
	 * @param rootElementClass
	 *            Cannot be null. The type of the root element.
	 * @return the Java object that is the root of the tree, of type
	 *         rootElement.
	 * @throws JAXBException
	 *             if an error occurs parsing the XML.
	 */
	public static Object parseXML(Reader reader, Class<?> rootElementClass) throws JAXBException{

		if(rootElementClass == null)
			throw new IllegalArgumentException("rootElementClass is null");
		if(reader == null)
			throw new IllegalArgumentException("reader is null");

		JAXBContext context = JAXBContext.newInstance(rootElementClass);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		CollectingValidationEventHandler handler = new CollectingValidationEventHandler();
		unmarshaller.setEventHandler(handler);

		Object object = unmarshaller.unmarshal(reader);
		if(!handler.getMessages().isEmpty()){
			String errorMessage = "XML parse errors:";
			for(String message : handler.getMessages()){
				errorMessage += "\n" + message;
			}
			throw new JAXBException(errorMessage);
		}

		return object;
	}

	/**
	 * Generate XML using the supplied root element as the root of the object
	 * tree and write the resulting XML to the specified writer
	 * 
	 * @param rootElement
	 *            Cannot be null.
	 * @param writer
	 *            Cannot be null.
	 * @throws JAXBException
	 */
	public static void generateXML(Object rootElement, Writer writer) throws JAXBException{

		if(rootElement == null)
			throw new IllegalArgumentException("rootElement is null");
		if(writer == null)
			throw new IllegalArgumentException("writer is null");

		JAXBContext context = JAXBContext.newInstance(rootElement.getClass());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(rootElement, writer);
	}

	private static class CollectingValidationEventHandler implements ValidationEventHandler{
		private List<String> messages = new ArrayList<String>();

		public List<String> getMessages(){
			return messages;
		}

		public boolean handleEvent(ValidationEvent event){
			if(event == null)
				throw new IllegalArgumentException("event is null");

			// calculate the severity prefix and return value
			String severity = null;
			boolean continueParsing = false;
			switch(event.getSeverity()){
			case ValidationEvent.WARNING:
				severity = "Warning";
				continueParsing = true; // continue after warnings
				break;
			case ValidationEvent.ERROR:
				severity = "Error";
				continueParsing = true; // terminate after errors
				break;
			case ValidationEvent.FATAL_ERROR:
				severity = "Fatal error";
				continueParsing = false; // terminate after fatal errors
				break;
			default:
				assert false : "Unknown severity.";
			}

			String location = getLocationDescription(event);
			String message = severity + " parsing " + location + " due to " + event.getMessage();
			messages.add(message);

			return continueParsing;
		}

		private String getLocationDescription(ValidationEvent event){
			ValidationEventLocator locator = event.getLocator();
			if(locator == null){
				return "XML with location unavailable";
			}

			StringBuffer msg = new StringBuffer();
			URL url = locator.getURL();
			Object obj = locator.getObject();
			Node node = locator.getNode();
			int line = locator.getLineNumber();

			if(url != null || line != -1){
				msg.append("line " + line);
				if(url != null)
					msg.append(" of " + url);
			}else if(obj != null){
				msg.append(" obj: " + obj.toString());
			}else if(node != null){
				msg.append(" node: " + node.toString());
			}

			return msg.toString();
		}
	}
}

