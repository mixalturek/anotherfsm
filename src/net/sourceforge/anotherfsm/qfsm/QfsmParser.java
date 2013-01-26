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
	 * See Qfsm source code, Project.cpp, Project::getDomDocument().
	 * 
	 * @param file
	 *            the input file
	 * @throws QfsmException
	 *             if parsing fails for any reason
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

		project.setAuthor(XmlUtils.getAtribute(projectEl, "author"));

		switch (project.getAuthor()) {
		case "Qfsm":
			break; // ok

		default:
			logger.warn("Unexpected project author, an issue may occur: "
					+ project.getAuthor());
			logger.info("Only files from 'Qfsm' were tested");
			break;
		}

		project.setVersion(XmlUtils.getAtribute(projectEl, "version"));

		switch (project.getVersion()) {
		case "0.52":
		case "0.53":
			break; // ok

		default:
			logger.warn("Unexpected project version, an issue may occur: "
					+ project.getVersion());
			logger.info("Only files from Qfsm '0.52' and '0.53' were tested");
			break;
		}

		project.setMachine(parseMachine(XmlUtils.getOneElement(projectEl,
				"machine")));

		return project;
	}

	private static QfsmMachine parseMachine(Element machineEl)
			throws QfsmException {
		QfsmMachine machine = new QfsmMachine();

		machine.setName(XmlUtils.getAtribute(machineEl, "name"));
		machine.setVersion(XmlUtils.getAtribute(machineEl, "version"));
		machine.setAuthor(XmlUtils.getAtribute(machineEl, "author"));
		machine.setDescription(XmlUtils.getAtribute(machineEl, "description"));

		machine.setType(QfsmMachine.MachineType.convert(XmlUtils.toInt(XmlUtils
				.getAtribute(machineEl, "type"))));

		machine.setNumMooreOutputs(XmlUtils.toInt(XmlUtils.getAtribute(
				machineEl, "nummooreout")));

		machine.setNumEncodingBits(XmlUtils.toInt(XmlUtils.getAtribute(
				machineEl, "numbits")));

		machine.setNumInputs(XmlUtils.toInt(XmlUtils.getAtribute(machineEl,
				"numin")));

		machine.setNumOutputs(XmlUtils.toInt(XmlUtils.getAtribute(machineEl,
				"numout")));

		String initialState = XmlUtils.getOptionalAtribute(machineEl,
				"initialstate");

		if (initialState.isEmpty()) {
			logger.warn("Optional attribute is not defined: initialstate");
			machine.setStartStateId(QfsmMachine.UNDEFINED_ID);
		} else {
			machine.setStartStateId(XmlUtils.toInt(initialState));
		}

		machine.setDrawStateFont(XmlUtils.getAtribute(machineEl, "statefont"));
		machine.setDrawStateFontSize(XmlUtils.toInt(XmlUtils.getAtribute(
				machineEl, "statefontsize")));
		machine.setDrawStateFontWeight(XmlUtils.toInt(XmlUtils.getAtribute(
				machineEl, "statefontweight")));
		machine.setDrawStateFontItalic(XmlUtils.toBoolean(XmlUtils.getAtribute(
				machineEl, "statefontitalic")));

		machine.setDrawTransitionFont(XmlUtils.getAtribute(machineEl,
				"transfont"));
		machine.setDrawTransitionFontSize(XmlUtils.toInt(XmlUtils.getAtribute(
				machineEl, "transfontsize")));
		machine.setDrawTransitionFontWeight(XmlUtils.toInt(XmlUtils
				.getAtribute(machineEl, "transfontweight")));
		machine.setDrawTransitionFontItalic(XmlUtils.toBoolean(XmlUtils
				.getAtribute(machineEl, "transfontitalic")));

		machine.setDrawArrowType(QfsmMachine.ArrowType.convert(XmlUtils
				.toInt(XmlUtils.getAtribute(machineEl, "arrowtype"))));

		machine.setDrawDisplayInitialTransition(XmlUtils.toBoolean(XmlUtils
				.getAtribute(machineEl, "draw_it")));

		machine.setInputNames(XmlUtils.getOptionalText(XmlUtils.getOneElement(
				machineEl, "inputnames")));
		machine.setOutputNames(XmlUtils.getOptionalText(XmlUtils.getOneElement(
				machineEl, "outputnames")));
		machine.setOutputNamesMoore(XmlUtils.getOptionalText(XmlUtils
				.getOneElement(machineEl, "outputnames_moore")));

		Element initialTransitionEl = XmlUtils.getOneOptionalElement(machineEl,
				"itransition");

		if (initialTransitionEl == null) {
			logger.warn("Optional element is not defined: itransition");
			machine.setInitialTransition(new QfsmInitialTransition());
		} else {
			machine.setInitialTransition(parseInitialTransition(initialTransitionEl));
		}

		machine.setStates(parseStates(XmlUtils.getElements(machineEl, "state")));
		machine.setTransitions(parseTransitions(XmlUtils.getElements(machineEl,
				"transition")));

		machine.evaluateStates();
		return machine;
	}

	private static List<QfsmState> parseStates(List<Element> elements)
			throws QfsmException {
		List<QfsmState> states = new LinkedList<QfsmState>();

		for (Element element : elements) {
			QfsmState state = new QfsmState();

			state.setName(XmlUtils.getText(element));
			state.setDescription(XmlUtils.getAtribute(element, "description"));
			state.setStateId(XmlUtils.toInt(XmlUtils.getAtribute(element,
					"code")));
			state.setMooreOutputs(XmlUtils
					.getAtribute(element, "moore_outputs"));

			state.setDrawPosX(XmlUtils.toInt(XmlUtils.getAtribute(element,
					"xpos")));
			state.setDrawPosY(XmlUtils.toInt(XmlUtils.getAtribute(element,
					"ypos")));
			state.setDrawRadius(XmlUtils.toInt(XmlUtils.getAtribute(element,
					"radius")));
			state.setDrawColor(XmlUtils.toInt(XmlUtils.getAtribute(element,
					"pencolor")));
			state.setDrawLineWidth(XmlUtils.toInt(XmlUtils.getAtribute(element,
					"linewidth")));

			state.setFinalState(XmlUtils.toBoolean(XmlUtils.getAtribute(
					element, "finalstate")));

			states.add(state);
		}

		return states;
	}

	private static List<QfsmTransition> parseTransitions(List<Element> elements)
			throws QfsmException {
		List<QfsmTransition> transitions = new LinkedList<QfsmTransition>();

		for (Element element : elements) {
			QfsmTransition transition = new QfsmTransition();

			transition.setType(QfsmTransition.TransitionType.convert(XmlUtils
					.toInt(XmlUtils.getAtribute(element, "type"))));

			transition.setDrawPosX(XmlUtils.toDouble(XmlUtils.getAtribute(
					element, "xpos")));
			transition.setDrawPosY(XmlUtils.toDouble(XmlUtils.getAtribute(
					element, "ypos")));
			transition.setDrawEndPosX(XmlUtils.toDouble(XmlUtils.getAtribute(
					element, "endx")));
			transition.setDrawEndPosY(XmlUtils.toDouble(XmlUtils.getAtribute(
					element, "endy")));

			transition.setDrawBezier1PosX(XmlUtils.toDouble(XmlUtils
					.getAtribute(element, "c1x")));
			transition.setDrawBezier1PosY(XmlUtils.toDouble(XmlUtils
					.getAtribute(element, "c1y")));
			transition.setDrawBezier2PosX(XmlUtils.toDouble(XmlUtils
					.getAtribute(element, "c2x")));
			transition.setDrawBezier2PosY(XmlUtils.toDouble(XmlUtils
					.getAtribute(element, "c2y")));

			transition.setDrawStraight(XmlUtils.toBoolean(XmlUtils.getAtribute(
					element, "straight")));

			transition.setDescription(XmlUtils.getAtribute(element,
					"description"));
			transition.setSourceStateId(XmlUtils.toInt(XmlUtils
					.getText(XmlUtils.getOneElement(element, "from"))));
			transition.setDestinationStateId(XmlUtils.toInt(XmlUtils
					.getText(XmlUtils.getOneElement(element, "to"))));

			Element inputs = XmlUtils.getOneElement(element, "inputs");
			transition.setInputInverted(XmlUtils.toBoolean(XmlUtils
					.getAtribute(inputs, "invert")));
			transition.setInputAny(XmlUtils.toBoolean(XmlUtils.getAtribute(
					inputs, "any")));
			transition.setInputDefault(XmlUtils.toBoolean(XmlUtils.getAtribute(
					inputs, "default")));
			transition.setInputEvent(XmlUtils.getText(inputs));

			transition.setOutputText(XmlUtils.getText(XmlUtils.getOneElement(
					element, "outputs")));

			transitions.add(transition);
		}

		return transitions;
	}

	private static QfsmInitialTransition parseInitialTransition(Element itrEl)
			throws QfsmException {
		QfsmInitialTransition itr = new QfsmInitialTransition();

		itr.setDrawPosX(XmlUtils.toDouble(XmlUtils.getAtribute(itrEl, "xpos")));
		itr.setDrawPosY(XmlUtils.toDouble(XmlUtils.getAtribute(itrEl, "ypos")));
		itr.setDrawEndPosX(XmlUtils.toDouble(XmlUtils
				.getAtribute(itrEl, "endx")));
		itr.setDrawEndPosY(XmlUtils.toDouble(XmlUtils
				.getAtribute(itrEl, "endy")));

		return itr;
	}
}
