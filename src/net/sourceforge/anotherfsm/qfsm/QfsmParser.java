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
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sourceforge.anotherfsm.AnotherFsm;
import net.sourceforge.anotherfsm.logger.FsmLogger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Parser of <a href="http://qfsm.sourceforge.net/">Qfsm</a> file format.
 * 
 * @author Michal Turek
 */
public class QfsmParser {
	/** The logger. */
	private static final FsmLogger logger = AnotherFsm
			.getLogger(QfsmParser.class);

	/**
	 * Forbid creating objects.
	 */
	private QfsmParser() {
		// Do nothing
	}

	/**
	 * Parse a Qfsm file. Data files from Qfsm of 0.52 and 0.53 versions should
	 * be parsed and loaded without problems.
	 * 
	 * <h3>Differences from Qfsm</h3>
	 * 
	 * <ul>
	 * <li>{@link QfsmMachine#initialstate} is optional in Qfsm, a warning will
	 * be logged in such case</li>
	 * <li></li>
	 * <li></li>
	 * <li>TODO:</li>
	 * </ul>
	 * 
	 * Note Qfsm supports various state machine types, but not all of them were
	 * tested. Please create a new ticket if loading of your file fails and you
	 * believe the issue is in this library.
	 * 
	 * @param file
	 *            the input file
	 * @throws QfsmException
	 *             if parsing fails
	 * 
	 * @see Qfsm source code, Project.cpp, Project::getDomDocument()
	 */
	public static QfsmProject parse(File file) throws QfsmException {
		XmlUtils.ensureNotNull(file, "Input file");

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();

			// Qfsm doesn't provide qfsm.dtd that is refered by XML document,
			// remove FileNotFoundException throwing
			builder.setEntityResolver(new EntityResolver() {
				@Override
				public InputSource resolveEntity(String publicId,
						String systemId) throws SAXException, IOException {
					return new InputSource(new StringReader(""));
				}
			});

			Document document = builder.parse(file);
			document.getDocumentElement().normalize();

			return parseProject(document.getDocumentElement());
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new QfsmException("Parsing failed: " + file, e);
		}
	}

	private static QfsmProject parseProject(Element projectEl)
			throws QfsmException {
		XmlUtils.checkElementName(projectEl, "qfsmproject");

		QfsmProject project = new QfsmProject();

		project.author = XmlUtils.getAtribute(projectEl, "author");

		switch (project.author) {
		case "Qfsm":
			break; // ok

		default:
			logger.warn("Unexpected project author, an issue may occur: "
					+ project.author);
			logger.info("Only files from 'Qfsm' were tested");
			break;
		}

		project.version = XmlUtils.getAtribute(projectEl, "version");

		switch (project.version) {
		case "0.52":
		case "0.53":
			break; // ok

		default:
			logger.warn("Unexpected project version, an issue may occur: "
					+ project.version);
			logger.info("Only files from Qfsm '0.52' and '0.53' were tested");
			break;
		}

		project.machine = parseMachine(XmlUtils.getOneElement(projectEl,
				"machine"));

		return project;
	}

	private static QfsmMachine parseMachine(Element machineEl)
			throws QfsmException {
		QfsmMachine machine = new QfsmMachine();

		machine.name = XmlUtils.getAtribute(machineEl, "name");
		machine.description = XmlUtils.getAtribute(machineEl, "description");
		machine.author = XmlUtils.getAtribute(machineEl, "author");
		machine.version = XmlUtils.getAtribute(machineEl, "version");

		// Optional in Qfsm but mandatory here
		String initialState = XmlUtils.getOptionalAtribute(machineEl,
				"initialstate");

		if (initialState.isEmpty())
			logger.warn("Optional attribute is not defined: initialstate");
		else
			machine.initialstate = XmlUtils.toInt(initialState);

		machine.draw_it = XmlUtils.toInt(XmlUtils.getAtribute(machineEl,
				"draw_it"));

		machine.type = QfsmMachine.MachineType.convert(XmlUtils.toInt(XmlUtils
				.getAtribute(machineEl, "type")));
		machine.arrowtype = XmlUtils.toInt(XmlUtils.getAtribute(machineEl,
				"arrowtype"));

		machine.numin = XmlUtils
				.toInt(XmlUtils.getAtribute(machineEl, "numin"));
		machine.numout = XmlUtils.toInt(XmlUtils.getAtribute(machineEl,
				"numout"));
		machine.numbits = XmlUtils.toInt(XmlUtils.getAtribute(machineEl,
				"numbits"));
		machine.nummooreout = XmlUtils.toInt(XmlUtils.getAtribute(machineEl,
				"nummooreout"));

		machine.transfont = XmlUtils.getAtribute(machineEl, "transfont");
		machine.transfontsize = XmlUtils.toInt(XmlUtils.getAtribute(machineEl,
				"transfontsize"));
		machine.transfontweight = XmlUtils.toInt(XmlUtils.getAtribute(
				machineEl, "transfontweight"));
		machine.transfontitalic = XmlUtils.toInt(XmlUtils.getAtribute(
				machineEl, "transfontitalic"));

		machine.statefont = XmlUtils.getAtribute(machineEl, "statefont");
		machine.statefontsize = XmlUtils.toInt(XmlUtils.getAtribute(machineEl,
				"statefontsize"));
		machine.statefontweight = XmlUtils.toInt(XmlUtils.getAtribute(
				machineEl, "statefontweight"));
		machine.statefontitalic = XmlUtils.toInt(XmlUtils.getAtribute(
				machineEl, "statefontitalic"));

		machine.states = parseStates(XmlUtils.getElements(machineEl, "state"));
		machine.transitions = parseTransitions(XmlUtils.getElements(machineEl,
				"transition"));

		machine.itransition = parseInitialTransition(XmlUtils.getOneElement(
				machineEl, "itransition"));

		machine.inputnames = XmlUtils.getOptionalText(XmlUtils.getOneElement(
				machineEl, "inputnames"));
		machine.outputnames = XmlUtils.getOptionalText(XmlUtils.getOneElement(
				machineEl, "outputnames"));
		machine.outputnames_moore = XmlUtils.getOptionalText(XmlUtils
				.getOneElement(machineEl, "outputnames_moore"));

		return machine;
	}

	private static List<QfsmState> parseStates(List<Element> elements)
			throws QfsmException {
		List<QfsmState> states = new LinkedList<QfsmState>();

		for (Element element : elements) {
			QfsmState state = new QfsmState();

			state.name = XmlUtils.getText(element);
			state.code = XmlUtils.toInt(XmlUtils.getAtribute(element, "code"));
			state.description = XmlUtils.getAtribute(element, "description");
			state.finalstate = XmlUtils.toBoolean(XmlUtils.getAtribute(element,
					"finalstate"));
			state.xpos = XmlUtils.toInt(XmlUtils.getAtribute(element, "xpos"));
			state.ypos = XmlUtils.toInt(XmlUtils.getAtribute(element, "ypos"));
			state.radius = XmlUtils.toInt(XmlUtils.getAtribute(element,
					"radius"));
			state.linewidth = XmlUtils.toInt(XmlUtils.getAtribute(element,
					"linewidth"));
			state.pencolor = XmlUtils.toInt(XmlUtils.getAtribute(element,
					"pencolor"));
			state.moore_outputs = XmlUtils
					.getAtribute(element, "moore_outputs");

			states.add(state);
		}

		return states;
	}

	private static List<QfsmTransition> parseTransitions(List<Element> elements)
			throws QfsmException {
		List<QfsmTransition> transitions = new LinkedList<QfsmTransition>();

		for (Element element : elements) {
			QfsmTransition transition = new QfsmTransition();

			transition.description = XmlUtils.getAtribute(element,
					"description");
			transition.from = XmlUtils.toInt(XmlUtils.getText(XmlUtils
					.getOneElement(element, "from")));
			transition.to = XmlUtils.toInt(XmlUtils.getText(XmlUtils
					.getOneElement(element, "to")));

			Element inputs = XmlUtils.getOneElement(element, "inputs");
			transition.inputsText = XmlUtils.getText(inputs);
			transition.inputsDefault = XmlUtils.toInt(XmlUtils.getAtribute(
					inputs, "default"));
			transition.inputsAny = XmlUtils.toInt(XmlUtils.getAtribute(inputs,
					"any"));
			transition.inputsInvert = XmlUtils.toInt(XmlUtils.getAtribute(
					inputs, "invert"));

			transition.outputsText = XmlUtils.getText(XmlUtils.getOneElement(
					element, "outputs"));

			transition.c1x = XmlUtils.toDouble(XmlUtils.getAtribute(element,
					"c1x"));
			transition.c1y = XmlUtils.toDouble(XmlUtils.getAtribute(element,
					"c1y"));
			transition.c2x = XmlUtils.toDouble(XmlUtils.getAtribute(element,
					"c2x"));
			transition.c2y = XmlUtils.toDouble(XmlUtils.getAtribute(element,
					"c2y"));
			transition.xpos = XmlUtils.toDouble(XmlUtils.getAtribute(element,
					"xpos"));
			transition.ypos = XmlUtils.toDouble(XmlUtils.getAtribute(element,
					"ypos"));
			transition.endx = XmlUtils.toDouble(XmlUtils.getAtribute(element,
					"endx"));
			transition.endy = XmlUtils.toDouble(XmlUtils.getAtribute(element,
					"endy"));
			transition.straight = XmlUtils.toInt(XmlUtils.getAtribute(element,
					"straight"));
			transition.type = XmlUtils.toInt(XmlUtils.getAtribute(element,
					"type"));

			transitions.add(transition);
		}

		return transitions;
	}

	private static QfsmInitialTransition parseInitialTransition(Element itrEl)
			throws QfsmException {
		QfsmInitialTransition itr = new QfsmInitialTransition();

		itr.xpos = XmlUtils.toInt(XmlUtils.getAtribute(itrEl, "xpos"));
		itr.ypos = XmlUtils.toInt(XmlUtils.getAtribute(itrEl, "ypos"));
		itr.endx = XmlUtils.toInt(XmlUtils.getAtribute(itrEl, "endx"));
		itr.endy = XmlUtils.toInt(XmlUtils.getAtribute(itrEl, "endy"));

		return itr;
	}
}
