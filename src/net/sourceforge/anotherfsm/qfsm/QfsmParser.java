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
	 * be successfully parsed.
	 * 
	 * <h3>Limitations and differences from Qfsm</h3>
	 * 
	 * <ul>
	 * <li>{@link QfsmMachine#startStateId} attribute is optional in Qfsm, a
	 * warning will be logged if not defined.</li>
	 * <li>{@link QfsmMachine#initialTransition} element is optional in Qfsm, a
	 * warning will be logged if not defined.</li>
	 * <li>Several text parameters that contain multiple values (list) are not
	 * parsed to the smallest pieces.</li>
	 * </ul>
	 * 
	 * Note Qfsm supports various state machine types, but not all of them were
	 * tested. Please report a bug if loading of your file fails and you believe
	 * the issue is in this library (patch appreciated).
	 * 
	 * @param file
	 *            the input file
	 * @throws QfsmException
	 *             if parsing fails for any reason
	 * 
	 * @see Qfsm source code, Project.cpp, Project::getDomDocument()
	 */
	// TODO: different FSM types
	// TODO: FSM with broken/unlinked transition
	// TODO: LANG=cs_CZ
	// TODO: version 0.53
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
		machine.version = XmlUtils.getAtribute(machineEl, "version");
		machine.author = XmlUtils.getAtribute(machineEl, "author");
		machine.description = XmlUtils.getAtribute(machineEl, "description");

		machine.type = QfsmMachine.MachineType.convert(XmlUtils.toInt(XmlUtils
				.getAtribute(machineEl, "type")));

		machine.numMooreOutputs = XmlUtils.toInt(XmlUtils.getAtribute(
				machineEl, "nummooreout"));

		machine.numEncodingBits = XmlUtils.toInt(XmlUtils.getAtribute(
				machineEl, "numbits"));

		machine.numInputs = XmlUtils.toInt(XmlUtils.getAtribute(machineEl,
				"numin"));

		machine.numOutputs = XmlUtils.toInt(XmlUtils.getAtribute(machineEl,
				"numout"));

		String initialState = XmlUtils.getOptionalAtribute(machineEl,
				"initialstate");

		if (initialState.isEmpty()) {
			logger.warn("Optional attribute is not defined: initialstate");
			machine.startStateId = QfsmMachine.UNDEFINED_ID;
		} else {
			machine.startStateId = XmlUtils.toInt(initialState);
		}

		machine.stateFont = XmlUtils.getAtribute(machineEl, "statefont");
		machine.stateFontSize = XmlUtils.toInt(XmlUtils.getAtribute(machineEl,
				"statefontsize"));
		machine.stateFontWeight = XmlUtils.toInt(XmlUtils.getAtribute(
				machineEl, "statefontweight"));
		machine.stateFontItalic = XmlUtils.toBoolean(XmlUtils.getAtribute(
				machineEl, "statefontitalic"));

		machine.transitionFont = XmlUtils.getAtribute(machineEl, "transfont");
		machine.transitionFontSize = XmlUtils.toInt(XmlUtils.getAtribute(
				machineEl, "transfontsize"));
		machine.transitionFontWeight = XmlUtils.toInt(XmlUtils.getAtribute(
				machineEl, "transfontweight"));
		machine.transitionFontItalic = XmlUtils.toInt(XmlUtils.getAtribute(
				machineEl, "transfontitalic"));

		machine.arrowType = QfsmMachine.ArrowType.convert(XmlUtils
				.toInt(XmlUtils.getAtribute(machineEl, "arrowtype")));

		machine.drawInitialTransition = XmlUtils.toBoolean(XmlUtils
				.getAtribute(machineEl, "draw_it"));

		machine.inputNames = XmlUtils.getOptionalText(XmlUtils.getOneElement(
				machineEl, "inputnames"));
		machine.outputNames = XmlUtils.getOptionalText(XmlUtils.getOneElement(
				machineEl, "outputnames"));
		machine.outputNamesMoore = XmlUtils.getOptionalText(XmlUtils
				.getOneElement(machineEl, "outputnames_moore"));

		Element initialTransitionEl = XmlUtils.getOneOptionalElement(machineEl,
				"itransition");

		if (initialTransitionEl == null) {
			logger.warn("Optional element is not defined: itransition");
			machine.initialTransition = new QfsmInitialTransition();
		} else {
			machine.initialTransition = parseInitialTransition(initialTransitionEl);
		}

		machine.states = parseStates(XmlUtils.getElements(machineEl, "state"));
		machine.transitions = parseTransitions(XmlUtils.getElements(machineEl,
				"transition"));

		return machine;
	}

	private static List<QfsmState> parseStates(List<Element> elements)
			throws QfsmException {
		List<QfsmState> states = new LinkedList<QfsmState>();

		for (Element element : elements) {
			QfsmState state = new QfsmState();

			state.name = XmlUtils.getText(element);
			state.description = XmlUtils.getAtribute(element, "description");
			state.stateId = XmlUtils.toInt(XmlUtils.getAtribute(element, "code"));
			state.mooreOutputs = XmlUtils.getAtribute(element, "moore_outputs");

			state.xpos = XmlUtils.toInt(XmlUtils.getAtribute(element, "xpos"));
			state.ypos = XmlUtils.toInt(XmlUtils.getAtribute(element, "ypos"));
			state.radius = XmlUtils.toInt(XmlUtils.getAtribute(element,
					"radius"));
			state.color = XmlUtils.toInt(XmlUtils.getAtribute(element,
					"pencolor"));
			state.lineWidth = XmlUtils.toInt(XmlUtils.getAtribute(element,
					"linewidth"));

			state.finalState = XmlUtils.toBoolean(XmlUtils.getAtribute(element,
					"finalstate"));

			states.add(state);
		}

		return states;
	}

	private static List<QfsmTransition> parseTransitions(List<Element> elements)
			throws QfsmException {
		List<QfsmTransition> transitions = new LinkedList<QfsmTransition>();

		for (Element element : elements) {
			QfsmTransition transition = new QfsmTransition();

			transition.type = QfsmTransition.TransitionType.convert(XmlUtils
					.toInt(XmlUtils.getAtribute(element, "type")));

			transition.xpos = XmlUtils.toDouble(XmlUtils.getAtribute(element,
					"xpos"));
			transition.ypos = XmlUtils.toDouble(XmlUtils.getAtribute(element,
					"ypos"));
			transition.endx = XmlUtils.toDouble(XmlUtils.getAtribute(element,
					"endx"));
			transition.endy = XmlUtils.toDouble(XmlUtils.getAtribute(element,
					"endy"));

			transition.c1x = XmlUtils.toDouble(XmlUtils.getAtribute(element,
					"c1x"));
			transition.c1y = XmlUtils.toDouble(XmlUtils.getAtribute(element,
					"c1y"));
			transition.c2x = XmlUtils.toDouble(XmlUtils.getAtribute(element,
					"c2x"));
			transition.c2y = XmlUtils.toDouble(XmlUtils.getAtribute(element,
					"c2y"));

			transition.straight = XmlUtils.toBoolean(XmlUtils.getAtribute(
					element, "straight"));

			transition.description = XmlUtils.getAtribute(element,
					"description");
			transition.startStateId = XmlUtils.toInt(XmlUtils.getText(XmlUtils
					.getOneElement(element, "from")));
			transition.destinationStateId = XmlUtils.toInt(XmlUtils
					.getText(XmlUtils.getOneElement(element, "to")));

			Element inputs = XmlUtils.getOneElement(element, "inputs");
			transition.inputInvert = XmlUtils.toBoolean(XmlUtils.getAtribute(
					inputs, "invert"));
			transition.inputAny = XmlUtils.toBoolean(XmlUtils.getAtribute(
					inputs, "any"));
			transition.inputDefault = XmlUtils.toBoolean(XmlUtils.getAtribute(
					inputs, "default"));
			transition.inputText = XmlUtils.getText(inputs);

			transition.outputsText = XmlUtils.getText(XmlUtils.getOneElement(
					element, "outputs"));

			transitions.add(transition);
		}

		return transitions;
	}

	private static QfsmInitialTransition parseInitialTransition(Element itrEl)
			throws QfsmException {
		QfsmInitialTransition itr = new QfsmInitialTransition();

		itr.xpos = XmlUtils.toDouble(XmlUtils.getAtribute(itrEl, "xpos"));
		itr.ypos = XmlUtils.toDouble(XmlUtils.getAtribute(itrEl, "ypos"));
		itr.endx = XmlUtils.toDouble(XmlUtils.getAtribute(itrEl, "endx"));
		itr.endy = XmlUtils.toDouble(XmlUtils.getAtribute(itrEl, "endy"));

		return itr;
	}
}
