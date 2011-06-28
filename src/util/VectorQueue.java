/*******************************************************************************
 * Copyright (c) 2011 Enrique Munoz de Cote.
 * repeatedgames is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * repeatedgames is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with repeatedgames.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Please send an email to: jemc@inaoep.mx for comments or to become part of this project.
 * Contributors:
 *     Enrique Munoz de Cote - initial API and implementation
 ******************************************************************************/
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

