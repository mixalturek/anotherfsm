package net.sourceforge.anotherfsm.qfsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import net.sourceforge.anotherfsm.AnotherFsm;
import net.sourceforge.anotherfsm.logger.StdStreamLoggerFactory;

import org.junit.BeforeClass;
import org.junit.Test;

public class QfsmParserTest {
	/** Delta for double comparison. */
	private static final double DELTA = 1e-15;

	/** Directory where data files are stored. */
	private static final String DATA_DIR = "src_tests/net/sourceforge/anotherfsm/qfsm/";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// TODO: update to no logger
		AnotherFsm.setLoggerFactory(new StdStreamLoggerFactory());
	}

	@Test
	public void testParse() {
		try {
			QfsmProject project = QfsmParser.parse(DATA_DIR + "test.fsm");

			assertEquals("0.52", project.getVersion());
			assertEquals("Qfsm", project.getAuthor());

			QfsmMachine machine = project.getMachine();
			assertEquals(0, machine.getNumMooreOutputs());
			assertEquals(false, machine.isDrawTransitionFontItalic());
			assertEquals(true, machine.isDrawDisplayInitialTransition());
			assertEquals(8, machine.getDrawStateFontSize());
			assertEquals("Helvetica", machine.getDrawTransitionFont());
			assertEquals(false, machine.isDrawStateFontItalic());
			assertEquals("STATE MACHINE AUTHOR", machine.getAuthor());
			assertEquals("STATE MACHINE DESCRIPTION", machine.getDescription());
			assertEquals("STATE MACHINE VERSION", machine.getVersion());
			assertEquals("STATE MACHINE", machine.getName());
			assertEquals(QfsmMachine.ArrowType.FILLED,
					machine.getDrawArrowType());
			assertEquals(2, machine.getNumEncodingBits());
			assertEquals(50, machine.getDrawStateFontWeight());
			assertEquals("Helvetica", machine.getDrawStateFont());
			assertEquals(0, machine.getNumInputs());
			assertEquals(8, machine.getDrawTransitionFontSize());
			assertEquals(50, machine.getDrawTransitionFontWeight());
			assertEquals(QfsmMachine.MachineType.FREE_TEXT, machine.getType());
			assertEquals(0, machine.getNumOutputs());
			assertEquals(0, machine.getStartStateId());
			assertEquals("FIRST STATE", machine.getStartState().getName());

			assertEquals("", machine.getOutputNames());
			assertEquals("", machine.getInputNames());
			assertEquals("", machine.getOutputNamesMoore());

			QfsmInitialTransition initialTransition = machine
					.getInitialTransition();
			assertEquals(63, initialTransition.getDrawPosX(), DELTA);
			assertEquals(223, initialTransition.getDrawPosY(), DELTA);
			assertEquals(123, initialTransition.getDrawEndPosX(), DELTA);
			assertEquals(223, initialTransition.getDrawEndPosY(), DELTA);

			assertEquals(3, machine.getStates().size());
			assertSame(machine.getState(0), machine.getState("FIRST STATE"));
			assertSame(machine.getState(1), machine.getState("SECOND STATE"));
			assertSame(machine.getState(2), machine.getState("FINAL STATE"));

			QfsmState state = machine.getState(0);
			assertEquals("FIRST STATE", state.getName());
			assertEquals(0, state.getDrawColor());
			assertEquals(40, state.getDrawRadius());
			assertEquals("FIRST STATE DESCRIPTION", state.getDescription());
			assertEquals(false, state.isFinalState());
			assertEquals("", state.getMooreOutputs());
			assertEquals(223, state.getDrawPosY(), DELTA);
			assertEquals(0, state.getStateId());
			assertEquals(163, state.getDrawPosX(), DELTA);
			assertEquals(1, state.getDrawLineWidth());

			state = machine.getState(1);
			assertEquals("SECOND STATE", state.getName());
			assertEquals(0, state.getDrawColor());
			assertEquals(40, state.getDrawRadius());
			assertEquals("SECOND STATE DESCRIPTION", state.getDescription());
			assertEquals(false, state.isFinalState());
			assertEquals("", state.getMooreOutputs());
			assertEquals(222, state.getDrawPosY(), DELTA);
			assertEquals(1, state.getStateId());
			assertEquals(434, state.getDrawPosX(), DELTA);
			assertEquals(1, state.getDrawLineWidth());

			state = machine.getState(2);
			assertEquals("FINAL STATE", state.getName());
			assertEquals(0, state.getDrawColor());
			assertEquals(40, state.getDrawRadius());
			assertEquals("FINAL STATE DESCRIPTION", state.getDescription());
			assertEquals(true, state.isFinalState());
			assertEquals("", state.getMooreOutputs());
			assertEquals(74, state.getDrawPosY(), DELTA);
			assertEquals(2, state.getStateId());
			assertEquals(306, state.getDrawPosX(), DELTA);
			assertEquals(1, state.getDrawLineWidth());

			assertEquals(6, machine.getTransitions().size());

			QfsmTransition transition = machine.getTransition(0,
					"FIRST TO SECOND", 1);
			assertEquals(266.6664851168897, transition.getDrawBezier1PosX(),
					DELTA);
			assertEquals(222.2841331762911, transition.getDrawBezier2PosY(),
					DELTA);
			assertEquals(222.5682663525822, transition.getDrawBezier1PosY(),
					DELTA);
			assertEquals("FIRST TO SECOND DESCRIPTION",
					transition.getDescription());
			assertEquals(true, transition.isDrawStraight());
			assertEquals(QfsmTransition.TransitionType.FREE_TEXT,
					transition.getType());
			assertEquals(222.8523995288733, transition.getDrawPosY(), DELTA);
			assertEquals(394.0, transition.getDrawEndPosX(), DELTA);
			assertEquals(202.9997276753345, transition.getDrawPosX(), DELTA);
			assertEquals(222.0, transition.getDrawEndPosY(), DELTA);
			assertEquals(330.3332425584449, transition.getDrawBezier2PosX(),
					DELTA);
			assertEquals(0, transition.getSourceStateId());
			assertEquals(1, transition.getDestinationStateId());
			assertEquals("FIRST STATE", transition.getSourceState().getName());
			assertEquals("SECOND STATE", transition.getDestinationState()
					.getName());
			assertEquals(false, transition.isInputDefault());
			assertEquals(false, transition.isInputAny());
			assertEquals(false, transition.isInputInverted());
			assertEquals("FIRST TO SECOND", transition.getInputEvent());
			assertEquals("FIRST TO SECOND OUTPUT", transition.getOutputText());

			transition = machine.getTransition(0, "FIRST TO FIRST", 0);
			assertEquals(233.5977738820847, transition.getDrawBezier1PosX(),
					DELTA);
			assertEquals(322.5284192033147, transition.getDrawBezier2PosY(),
					DELTA);
			assertEquals(320.0358404039149, transition.getDrawBezier1PosY(),
					DELTA);
			assertEquals("FIRST TO FIRST DESCRIPTION",
					transition.getDescription());
			assertEquals(true, transition.isDrawStraight());
			assertEquals(QfsmTransition.TransitionType.FREE_TEXT,
					transition.getType());
			assertEquals(260.3338242481224, transition.getDrawPosY(), DELTA);
			assertEquals(150.0020375070273, transition.getDrawEndPosX(), DELTA);
			assertEquals(177.3591631723548, transition.getDrawPosX(), DELTA);
			assertEquals(260.8292607782821, transition.getDrawEndPosY(), DELTA);
			assertEquals(95.96199756191075, transition.getDrawBezier2PosX(),
					DELTA);
			assertEquals(0, transition.getSourceStateId());
			assertEquals(0, transition.getDestinationStateId());
			assertEquals("FIRST STATE", transition.getSourceState().getName());
			assertEquals("FIRST STATE", transition.getDestinationState()
					.getName());
			assertEquals(false, transition.isInputDefault());
			assertEquals(false, transition.isInputAny());
			assertEquals(false, transition.isInputInverted());
			assertEquals("FIRST TO FIRST", transition.getInputEvent());
			assertEquals("FIRST TO FIRST OUTPUT", transition.getOutputText());

			transition = machine.getTransition(1, "SECOND TO SECOND", 1);
			assertEquals(503.3824573913882, transition.getDrawBezier1PosX(),
					DELTA);
			assertEquals(320.6848628340658, transition.getDrawBezier2PosY(),
					DELTA);
			assertEquals(319.908501195413, transition.getDrawBezier1PosY(),
					DELTA);
			assertEquals("SECOND TO SECOND DESCRIPTION",
					transition.getDescription());
			assertEquals(true, transition.isDrawStraight());
			assertEquals(QfsmTransition.TransitionType.FREE_TEXT,
					transition.getType());
			assertEquals(259.5099504289482, transition.getDrawPosY(), DELTA);
			assertEquals(420.5313979159949, transition.getDrawEndPosX(), DELTA);
			assertEquals(447.8925742329437, transition.getDrawPosX(), DELTA);
			assertEquals(259.6642636713202, transition.getDrawEndPosY(), DELTA);
			assertEquals(365.7263019353601, transition.getDrawBezier2PosX(),
					DELTA);
			assertEquals(1, transition.getSourceStateId());
			assertEquals(1, transition.getDestinationStateId());
			assertEquals("SECOND STATE", transition.getSourceState().getName());
			assertEquals("SECOND STATE", transition.getDestinationState()
					.getName());
			assertEquals(false, transition.isInputDefault());
			assertEquals(false, transition.isInputAny());
			assertEquals(false, transition.isInputInverted());
			assertEquals("SECOND TO SECOND", transition.getInputEvent());
			assertEquals("SECOND TO SECOND OUTPUT", transition.getOutputText());

			transition = machine.getTransition(1, "SECOND TO FIRST", 0);
			assertEquals(332.6684796661258, transition.getDrawBezier1PosX(),
					DELTA);
			assertEquals(243.5966546955807, transition.getDrawBezier2PosY(),
					DELTA);
			assertEquals(243.6134791740603, transition.getDrawBezier1PosY(),
					DELTA);
			assertEquals("SECOND TO FIRST DESCRIPTION",
					transition.getDescription());
			assertEquals(true, transition.isDrawStraight());
			assertEquals(QfsmTransition.TransitionType.FREE_TEXT,
					transition.getType());
			assertEquals(243.63030365254, transition.getDrawPosY(), DELTA);
			assertEquals(197.2997170285018, transition.getDrawEndPosX(), DELTA);
			assertEquals(400.3528609849379, transition.getDrawPosX(), DELTA);
			assertEquals(243.5798302171011, transition.getDrawEndPosY(), DELTA);
			assertEquals(264.9840983473138, transition.getDrawBezier2PosX(),
					DELTA);
			assertEquals(1, transition.getSourceStateId());
			assertEquals(0, transition.getDestinationStateId());
			assertEquals("SECOND STATE", transition.getSourceState().getName());
			assertEquals("FIRST STATE", transition.getDestinationState()
					.getName());
			assertEquals(false, transition.isInputDefault());
			assertEquals(false, transition.isInputAny());
			assertEquals(false, transition.isInputInverted());
			assertEquals("SECOND TO FIRST", transition.getInputEvent());
			assertEquals("SECOND TO FIRST OUTPUT", transition.getOutputText());

			transition = machine.getTransition(1, "SECOND TO FINAL", 2);
			assertEquals(384.3346330791148, transition.getDrawBezier1PosX(),
					DELTA);
			assertEquals(130.1759481374532, transition.getDrawBezier2PosY(),
					DELTA);
			assertEquals(160.8491877135374, transition.getDrawBezier1PosY(),
					DELTA);
			assertEquals("SECOND TO FINAL DESCRIPTION",
					transition.getDescription());
			assertEquals(true, transition.isDrawStraight());
			assertEquals(QfsmTransition.TransitionType.FREE_TEXT,
					transition.getType());
			assertEquals(191.5224272896216, transition.getDrawPosY(), DELTA);
			assertEquals(336.8157728449876, transition.getDrawEndPosX(), DELTA);
			assertEquals(408.0940631961784, transition.getDrawPosX(), DELTA);
			assertEquals(99.50270856136903, transition.getDrawEndPosY(), DELTA);
			assertEquals(360.5752029620512, transition.getDrawBezier2PosX(),
					DELTA);
			assertEquals(1, transition.getSourceStateId());
			assertEquals(2, transition.getDestinationStateId());
			assertEquals("SECOND STATE", transition.getSourceState().getName());
			assertEquals("FINAL STATE", transition.getDestinationState()
					.getName());
			assertEquals(false, transition.isInputDefault());
			assertEquals(false, transition.isInputAny());
			assertEquals(false, transition.isInputInverted());
			assertEquals("SECOND TO FINAL", transition.getInputEvent());
			assertEquals("SECOND TO FINAL OUTPUT", transition.getOutputText());

			transition = machine.getTransition(2, "FINAL TO FIRST", 0);
			assertEquals(246.3141783096459, transition.getDrawBezier1PosX(),
					DELTA);
			assertEquals(161.4943922715183, transition.getDrawBezier2PosY(),
					DELTA);
			assertEquals(130.3590486376485, transition.getDrawBezier1PosY(),
					DELTA);
			assertEquals("FINAL TO FIRST DESCRIPTION",
					transition.getDescription());
			assertEquals(true, transition.isDrawStraight());
			assertEquals(QfsmTransition.TransitionType.FREE_TEXT,
					transition.getType());
			assertEquals(99.22370500377863, transition.getDrawPosY(), DELTA);
			assertEquals(189.0316549382387, transition.getDrawEndPosX(), DELTA);
			assertEquals(274.9554399953494, transition.getDrawPosX(), DELTA);
			assertEquals(192.6297359053881, transition.getDrawEndPosY(), DELTA);
			assertEquals(217.6729166239423, transition.getDrawBezier2PosX(),
					DELTA);
			assertEquals(2, transition.getSourceStateId());
			assertEquals(0, transition.getDestinationStateId());
			assertEquals("FINAL STATE", transition.getSourceState().getName());
			assertEquals("FIRST STATE", transition.getDestinationState()
					.getName());
			assertEquals(false, transition.isInputDefault());
			assertEquals(false, transition.isInputAny());
			assertEquals(false, transition.isInputInverted());
			assertEquals("FINAL TO FIRST", transition.getInputEvent());
			assertEquals("FINAL TO FIRST OUTPUT", transition.getOutputText());
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}
	}

	@Test
	public void testParseLocale() {
		try {
			// Check the file is loadable (no exception)
			QfsmParser.parse(DATA_DIR + "lang_cs_cz.fsm");
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}
	}

	@Test
	public void testParseEmptyFreeText() {
		try {
			QfsmProject project = QfsmParser.parse(DATA_DIR
					+ "empty_freetext.fsm");

			QfsmMachine machine = project.getMachine();

			assertEquals(QfsmMachine.UNDEFINED_ID, machine.getStartStateId());
			assertNull(machine.getStartState());
			assertNull(machine.getInitialTransition());
			assertEquals(0, machine.getStates().size());
			assertEquals(0, machine.getTransitions().size());
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}
	}

	@Test
	public void testParseBrokenTransition() {
		try {
			QfsmParser.parse(DATA_DIR + "broken_transition.fsm");
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals("Expected one subelement: transition.to, count 0",
					e.getMessage());
		}
	}

	@Test
	public void testParseBinary() {
		try {
			QfsmParser.parse(DATA_DIR + "binary.fsm");
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}
	}

	@Test
	public void testParseAscii() {
		try {
			QfsmParser.parse(DATA_DIR + "ascii.fsm");
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}
	}

	@Test
	public void testParseQfsm053() {
		try {
			QfsmProject project = QfsmParser.parse(DATA_DIR + "qfsm_0_53.fsm");

			assertEquals("0.53", project.getVersion());
			assertEquals("Qfsm", project.getAuthor());
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}
	}
}
