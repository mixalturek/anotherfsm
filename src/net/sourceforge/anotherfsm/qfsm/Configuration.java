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

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Configuration of code generation.
 * 
 * @author Michal Turek
 */
class Configuration {
	/** The configuration version. */
	private String version;

	/** The output directory. */
	private String outputDirectory;

	/** The base state machine class. */
	private String baseClass;

	/** The Java package. */
	private String javaPackage;

	/** The suffix of the file name. */
	private String fsmFileSuffix;

	/** The imports for state machine class. */
	private String fsmImports;

	/** Description of transitions contains Java code. */
	private boolean transitionDescriptionContainsCode;

	/** Generate type preprocessor. */
	private boolean typePreprocessor;

	/** Generate equals preprocessor. */
	private boolean equalsPreprocessor;

	/** Generate global state listener. */
	private boolean globalStateListener;

	/** Generate global transition listener. */
	private boolean globalTransitionListener;

	/** Generate state listener for each state. */
	private boolean stateListener;

	/** Generate transition listener for each transition. */
	private boolean transitionListener;

	/** The suffix of the file name. */
	private String processorFileSuffix;

	/** The imports for processor class. */
	private String processorImports;

	/** File header. */
	private String fileHeader;

	/**
	 * Forbid creating objects.
	 */
	private Configuration() {
		// Do nothing
	}

	public String getVersion() {
		return version;
	}

	public String getOutputDirectory() {
		return outputDirectory;
	}

	public String getBaseClass() {
		return baseClass;
	}

	public String getJavaPackage() {
		return javaPackage;
	}

	public String getFsmImports() {
		return fsmImports;
	}

	public boolean isTypePreprocessor() {
		return typePreprocessor;
	}

	public boolean isEqualsPreprocessor() {
		return equalsPreprocessor;
	}

	public boolean isGlobalStateListener() {
		return globalStateListener;
	}

	public boolean isGlobalTransitionListener() {
		return globalTransitionListener;
	}

	public boolean isStateListener() {
		return stateListener;
	}

	public boolean isTransitionListener() {
		return transitionListener;
	}

	public String getProcessorImports() {
		return processorImports;
	}

	public String getFileHeader() {
		return fileHeader;
	}

	public boolean isTransitionDescriptionContainsCode() {
		return transitionDescriptionContainsCode;
	}

	public String getFsmFileSuffix() {
		return fsmFileSuffix;
	}

	public String getProcessorFileSuffix() {
		return processorFileSuffix;
	}

	/**
	 * Parse a configuration file.
	 * 
	 * @param path
	 *            the file system path
	 * @return the object representation of the configuration data file
	 * @throws QfsmException
	 *             if parsing fails for any reason
	 * 
	 * @see #parse(File)
	 */
	public static Configuration parse(String path) throws QfsmException {
		XmlUtils.ensureNotNull(path, "path");
		return parse(new File(path));
	}

	/**
	 * Parse a configuration file.
	 * 
	 * @param file
	 *            the input file
	 * @return the object representation of the configuration data file
	 * @throws QfsmException
	 *             if parsing fails for any reason
	 */
	public static Configuration parse(File file) throws QfsmException {
		XmlUtils.ensureNotNull(file, "file");

		try {
			Document document = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(file);
			document.getDocumentElement().normalize();

			return parseConfiguration(document.getDocumentElement());
		} catch (ParserConfigurationException | SAXException | DOMException
				| IOException e) {
			throw new QfsmException("Parsing failed: " + file + ", "
					+ e.getMessage(), e);
		}
	}

	/**
	 * Parse the XML content
	 * 
	 * @param configurationEl
	 *            the top level element
	 * @return the configuration object
	 * @throws QfsmException
	 *             if something fails
	 */
	private static Configuration parseConfiguration(Element configurationEl)
			throws QfsmException {
		XmlUtils.checkElementName(configurationEl, "AnotherFsmCodeGenerator");

		Configuration configuration = new Configuration();

		configuration.version = XmlUtils.getText(XmlUtils.getOneElement(
				configurationEl, "Version"));

		configuration.outputDirectory = XmlUtils.getText(XmlUtils
				.getOneElement(configurationEl, "OutputDirectory"));

		configuration.baseClass = XmlUtils.getText(XmlUtils.getOneElement(
				configurationEl, "BaseClass"));

		configuration.javaPackage = XmlUtils.getText(XmlUtils.getOneElement(
				configurationEl, "JavaPackage"));

		configuration.transitionDescriptionContainsCode = XmlUtils
				.toBoolean(XmlUtils.getText(XmlUtils.getOneElement(
						configurationEl, "TransitionDescriptionContainsCode")));

		Element fsmClassEl = XmlUtils.getOneElement(configurationEl,
				"StateMachineClass");

		configuration.fsmFileSuffix = XmlUtils.getOptionalText(XmlUtils
				.getOneElement(fsmClassEl, "FileSuffix"));

		configuration.fsmImports = XmlUtils.getText(XmlUtils.getOneElement(
				fsmClassEl, "Imports"));

		Element processorClassEl = XmlUtils.getOneElement(configurationEl,
				"ProcessorClass");

		configuration.typePreprocessor = XmlUtils.toBoolean(XmlUtils
				.getText(XmlUtils.getOneElement(processorClassEl,
						"TypePreprocessor")));

		configuration.equalsPreprocessor = XmlUtils.toBoolean(XmlUtils
				.getText(XmlUtils.getOneElement(processorClassEl,
						"EqualsPreprocessor")));

		configuration.globalStateListener = XmlUtils.toBoolean(XmlUtils
				.getText(XmlUtils.getOneElement(processorClassEl,
						"GlobalStateListener")));

		configuration.globalTransitionListener = XmlUtils.toBoolean(XmlUtils
				.getText(XmlUtils.getOneElement(processorClassEl,
						"GlobalTransitionListener")));

		configuration.stateListener = XmlUtils.toBoolean(XmlUtils
				.getText(XmlUtils.getOneElement(processorClassEl,
						"StateListener")));

		configuration.transitionListener = XmlUtils.toBoolean(XmlUtils
				.getText(XmlUtils.getOneElement(processorClassEl,
						"TransitionListener")));

		configuration.processorFileSuffix = XmlUtils.getOptionalText(XmlUtils
				.getOneElement(processorClassEl, "FileSuffix"));

		configuration.processorImports = XmlUtils.getText(XmlUtils
				.getOneElement(processorClassEl, "Imports"));

		configuration.fileHeader = XmlUtils.getText(XmlUtils.getOneElement(
				configurationEl, "FileHeader"));

		return configuration;
	}
}
