/*
 *  Copyright 2013 Michal Turek, AnotherFSM
 *
 *      http://anotherfsm.sourceforge.net/
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package net.sourceforge.anotherfsm.qfsm;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Helper methods for XML/DOM parsing.
 * 
 * @author Michal Turek
 */
class XmlUtils {
	/**
	 * Forbid creating objects.
	 */
	private XmlUtils() {
		// Do nothing
	}

	/**
	 * Verify the passed object is not null.
	 * 
	 * @param object
	 *            the object
	 * @param objectName
	 *            the object name
	 * @throws QfsmException
	 *             if the object is null
	 */
	public static void ensureNotNull(Object object, String objectName)
			throws QfsmException {
		if (object == null) {
			throw new QfsmException("Object must not be null: " + objectName);
		}
	}

	/**
	 * Verify the string is not empty.
	 * 
	 * @param string
	 *            the string
	 * @param objectName
	 *            the string name
	 * @throws QfsmException
	 *             if the string is empty
	 */
	public static void ensureNotEmpty(String string, String objectName)
			throws QfsmException {
		ensureNotNull(string, objectName);

		if (string.isEmpty()) {
			throw new QfsmException("String must not be empty: " + objectName);
		}
	}

	/**
	 * Check the element has the expected name.
	 * 
	 * @param element
	 *            the element
	 * @param expected
	 *            the expected name
	 * @throws QfsmException
	 *             if the element name differs from the expected one
	 */
	public static void checkElementName(Element element, String expected)
			throws QfsmException {
		ensureNotNull(element, "element");
		ensureNotNull(expected, "expected");

		if (!expected.equals(element.getNodeName())) {
			throw new QfsmException("Unexpected element: " + expected + ", "
					+ element.getNodeName());
		}
	}

	/**
	 * Convert a node to an element.
	 * 
	 * @param node
	 *            the node
	 * @return the element
	 * @throws QfsmException
	 *             if the node is not element
	 */
	public static Element toElement(Node node) throws QfsmException {
		ensureNotNull(node, "node");

		if (node.getNodeType() != Node.ELEMENT_NODE) {
			throw new QfsmException("Node is not element: " + node);
		}

		return (Element) node;
	}

	/**
	 * Get one subelement with a concrete name.
	 * 
	 * @param element
	 *            the parent element
	 * @param name
	 *            the subelement name
	 * @return the subelement
	 * @throws QfsmException
	 *             if the element is not defined or if more than one element of
	 *             this name is defined
	 */
	public static Element getOneElement(Element element, String name)
			throws QfsmException {
		ensureNotNull(element, "element");
		ensureNotNull(name, "name");

		NodeList list = element.getElementsByTagName(name);

		switch (list.getLength()) {
		case 1:
			return toElement(list.item(0));

		default:
			throw new QfsmException("Unexpected count of subelements: " + name
					+ ", " + list.getLength());
		}
	}

	/**
	 * Get one optional subelement with a concrete name.
	 * 
	 * @param element
	 *            the parent element
	 * @param name
	 *            the subelement name
	 * @return the subelement or null if the subelement is not defined
	 * @throws QfsmException
	 *             if more than one element of this name is defined
	 */
	public static Element getOneOptionalElement(Element element, String name)
			throws QfsmException {
		ensureNotNull(element, "element");
		ensureNotNull(name, "name");

		NodeList list = element.getElementsByTagName(name);

		switch (list.getLength()) {
		case 0:
			return null;

		case 1:
			return toElement(list.item(0));

		default:
			throw new QfsmException("Unexpected count of subelements: " + name
					+ ", " + list.getLength());
		}
	}

	/**
	 * Get all subelements with a concrete name.
	 * 
	 * @param element
	 *            the parent element
	 * @param name
	 *            the subelements name
	 * @return the list of subelements
	 * @throws QfsmException
	 *             if something fails
	 */
	public static List<Element> getElements(Element element, String name)
			throws QfsmException {
		ensureNotNull(element, "element");
		ensureNotNull(name, "name");

		List<Element> elements = new LinkedList<Element>();

		NodeList list = element.getElementsByTagName(name);

		for (int i = 0; i < list.getLength(); ++i)
			elements.add(toElement(list.item(i)));

		return elements;
	}

	/**
	 * Get text located inside an element.
	 * 
	 * @param element
	 *            the parent element
	 * @return the text
	 * @throws QfsmException
	 *             if the text is empty
	 */
	public static String getText(Element element) throws QfsmException {
		ensureNotNull(element, "element");

		String value = element.getTextContent();
		ensureNotEmpty(value, "value");

		return value;
	}

	/**
	 * Get optional text located inside an element.
	 * 
	 * @param element
	 *            the parent element
	 * @return the text or empty string
	 * @throws QfsmException
	 *             if something fails
	 */
	public static String getOptionalText(Element element) throws QfsmException {
		ensureNotNull(element, "element");

		return element.getTextContent();
	}

	/**
	 * Get a value of an attribute.
	 * 
	 * @param element
	 *            the parent element
	 * @param name
	 *            the attribute name
	 * @return the attribute value
	 * @throws QfsmException
	 *             if the attribute is not defined
	 */
	public static String getAtribute(Element element, String name)
			throws QfsmException {
		ensureNotNull(element, "element");
		ensureNotNull(name, "name");

		if (!element.hasAttribute(name)) {
			throw new QfsmException("Attribute is not defined: " + name);
		}

		return element.getAttribute(name);
	}

	/**
	 * Get a value of an optional attribute.
	 * 
	 * @param element
	 *            the parent element
	 * @param name
	 *            the attribute name
	 * @return the attribute value or empty string if the attribute is not
	 *         defined
	 * @throws QfsmException
	 *             if something fails
	 */
	public static String getOptionalAtribute(Element element, String name)
			throws QfsmException {
		ensureNotNull(element, "element");
		ensureNotNull(name, "name");

		return element.getAttribute(name);
	}

	/**
	 * Parse string to an integer value.
	 * 
	 * @param string
	 *            the string
	 * @return the integer
	 * @throws QfsmException
	 *             if the parsing fails
	 */
	public static int toInt(String string) throws QfsmException {
		ensureNotNull(string, "string");

		try {
			return Integer.parseInt(string);
		} catch (NumberFormatException e) {
			throw new QfsmException("Integer parsing failed: " + string, e);
		}
	}

	/**
	 * Parse string to a double value.
	 * 
	 * @param string
	 *            the string
	 * @return the double value
	 * @throws QfsmException
	 *             if the parsing fails
	 */
	public static double toDouble(String string) throws QfsmException {
		ensureNotNull(string, "string");

		try {
			return Double.parseDouble(string);
		} catch (NumberFormatException e) {
			throw new QfsmException("Double parsing failed: " + string, e);
		}
	}

	/**
	 * Parse string to a boolean value. The allowed keywords are
	 * {@code 0, false, no, 1, true, yes} independent to case.
	 * 
	 * @param string
	 *            the string
	 * @return the boolean value
	 * @throws QfsmException
	 *             if the parsing fails
	 */
	public static boolean toBoolean(String string) throws QfsmException {
		ensureNotNull(string, "string");

		switch (string.toLowerCase()) {
		case "0":
		case "false":
		case "no":
			return false;

		case "1":
		case "true":
		case "yes":
			return true;

		default:
			throw new QfsmException("Boolean parsing failed: " + string);
		}
	}
}
