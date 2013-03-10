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

package net.sourceforge.anotherfsm;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Processor of events based on a thread with an input queue.
 * 
 * @author Michal Turek
 */
public class ThreadProcessor extends ProcessorAdapter implements Runnable {
	/** The thread for this processor. */
	private final Thread thread;

	/** The queue for storing of the incoming events before their processing. */
	private final BlockingQueue<Event> queue;

	/** The internal processor that processes the input events. */
	private final Processor processor;

	/** Request to stop the thread occurred. */
	private final AtomicBoolean stopRequested = new AtomicBoolean(false);

	/**
	 * Create the object.
	 * 
	 * @param processor
	 *            the internal processor that process the input events
	 * @param daemon
	 *            the thread is a daemon thread
	 * @param queue
	 *            the queue for storing of the incoming events before their
	 *            processing
	 */
	public ThreadProcessor(Processor processor, boolean daemon,
			BlockingQueue<Event> queue) {
		super(processor.getName());

		Helpers.ensureNotNull(processor, "processor");
		Helpers.ensureNotNull(queue, "queue");

		this.processor = processor;
		this.queue = queue;
		thread = new Thread(this, getThreadName());
		thread.setDaemon(daemon);
	}

	/**
	 * Create the object. The thread will be a non-daemon thread and the queue
	 * will be {@link LinkedBlockingQueue} with unlimited length.
	 * 
	 * @param processor
	 *            the internal processor that process the input events
	 */
	public ThreadProcessor(Processor processor) {
		this(processor, false, new LinkedBlockingQueue<Event>());
	}

	/**
	 * Helper method to get the name of the internally executed thread.
	 * 
	 * @return the thread name
	 */
	String getThreadName() {
		return getClass().getSimpleName() + Helpers.CLASS_INSTANCE_DELIMITER
				+ processor.getName();
	}

	@Override
	public void start() throws FsmException {
		super.start();
		processor.start();
		thread.start();
	}

	@Override
	public void close() {
		stopRequested.set(true);
		thread.interrupt();

		try {
			thread.join();
		} catch (InterruptedException e) {
			logger.warn("Waiting for the thread stop interrupted: " + e);
		}

		processor.close();
		super.close();
	}

	@Override
	public Event process(Event event) throws FsmException {
		Helpers.ensureNotNull(event, "event");

		if (queue.offer(event)) {
			return event;
		} else {
			throw new FsmException("Adding event to the queue failed: " + event);
		}
	}

	@Override
	public void run() {
		logger.info("Thread started");

		try {
			while (!stopRequested.get()) {
				try {
					Event event = queue.take();
					processor.process(event);
				} catch (InterruptedException e) {
					logger.warn("Waiting for the event interrupted: " + e);
				} catch (FsmException e) {
					logger.warn("Exception while processing event: " + e);
				}
			}

			logger.info("Thread finished normally");
		} catch (Throwable e) {
			Helpers.logThreadUnexpectedlyFinished(logger,
					"Unexpected exception while running thread", e);
			throw e;
		}
	}
}
