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

	public static void ensureNotEmpty(String string, String objectName)
			throws QfsmException {
		ensureNotNull(string, objectName);

		if (string.isEmpty()) {
			throw new QfsmException("String must not be empty: " + objectName);
		}
	}

	public static void checkElementName(Element element, String expected)
			throws QfsmException {
		ensureNotNull(element, "element");
		ensureNotNull(expected, "expected");

		if (!expected.equals(element.getNodeName())) {
			throw new QfsmException("Unexpected element: " + expected + ", "
					+ element.getNodeName());
		}
	}

	public static Element toElement(Node node) throws QfsmException {
		if (node.getNodeType() != Node.ELEMENT_NODE) {
			throw new QfsmException("Node is not element: " + node);
		}

		return (Element) node;
	}

	public static Element getOneElement(Element element, String name)
			throws QfsmException {
		NodeList list = element.getElementsByTagName(name);
		if (list.getLength() != 1) {
			throw new QfsmException("Unexpected count of machine nodes: "
					+ list.getLength());
		}

		return toElement(list.item(0));
	}

	public static List<Element> getElements(Element element, String name)
			throws QfsmException {
		List<Element> elements = new LinkedList<Element>();

		NodeList list = element.getElementsByTagName(name);

		for (int i = 0; i < list.getLength(); ++i)
			elements.add(toElement(list.item(i)));

		return elements;
	}

	public static String getText(Element element) throws QfsmException {
		ensureNotNull(element, "element");

		String value = element.getTextContent();
		ensureNotEmpty(value, "value");

		return value;
	}

	public static String getOptionalText(Element element) throws QfsmException {
		ensureNotNull(element, "element");

		return element.getTextContent();
	}

	public static String getAtribute(Element element, String name)
			throws QfsmException {
		ensureNotNull(element, "element");
		ensureNotNull(name, "name");

		if (!element.hasAttribute(name)) {
			throw new QfsmException("Attribute is not defined: " + name);
		}

		String value = element.getAttribute(name);
		// ensureNotEmpty(value, "value");

		return value;
	}

	/**
	 * Get a value of optional attribute.
	 * 
	 * @param element
	 *            the source element
	 * @param name
	 *            the attribute name
	 * @return the value if the attribute is defined or empty string
	 * @throws QfsmException
	 *             if something fails
	 */
	public static String getOptionalAtribute(Element element, String name)
			throws QfsmException {
		ensureNotNull(element, "element");
		ensureNotNull(name, "name");

		return element.getAttribute(name);
	}

	public static int toInt(String string) throws QfsmException {
		try {
			return Integer.parseInt(string);
		} catch (NumberFormatException e) {
			throw new QfsmException("Integer parsing failed: " + string);
		}
	}

	public static double toDouble(String string) throws QfsmException {
		try {
			return Double.parseDouble(string);
		} catch (NumberFormatException e) {
			throw new QfsmException("Integer parsing failed: " + string);
		}
	}

	public static boolean toBoolean(String string) throws QfsmException {
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
