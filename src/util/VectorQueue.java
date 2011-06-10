package util;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

/*
 * This class implements a queue using a Vector.  It has the standard queue
 * operations enEqueue and deQueue, as well as a peek method to see the first
 * item without removing it, and an isEmpty method to check if there is
 * anything currently in the queue.
 *
 * We will fix the size of the Vector
 * that we are using. This size is determined as a parameter to the
 * constructor. The head and tail of the queue are managed by the headIndex
 * and tailIndex instance variables.
 */
public class VectorQueue<E> extends LinkedBlockingDeque<E> {

    public VectorQueue() {
        super();
    }

    public VectorQueue(int capacity) {
        super(capacity);
    }

    @Override
    public synchronized boolean offerFirst(E e) {
        if (remainingCapacity() == 0) {
            removeLast();
        }
        super.offerFirst(e);
        return true;
    }
}

