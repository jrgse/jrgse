/******************************************************************************
 * Copyright (c) 2002 - 2006 IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *****************************************************************************/
package com.ibm.wala.cast.util;

import java.util.Iterator;
import java.util.Map;

import com.ibm.wala.cast.tree.CAstNode;
import com.ibm.wala.util.collections.Filter;
import com.ibm.wala.util.collections.FilterIterator;
import com.ibm.wala.util.collections.HashMapFactory;
import com.ibm.wala.util.graph.traverse.DFSDiscoverTimeIterator;

public class CAstFunctions {

  public static CAstNode findIf(CAstNode tree, Filter<CAstNode> f) {
    if (f.accepts(tree)) {
      return tree;
    } else {
      for (int i = 0; i < tree.getChildCount(); i++) {
        CAstNode result = findIf(tree.getChild(i), f);
        if (result != null) {
          return result;
        }
      }
    }

    return null;
  }

  public static Iterator<CAstNode> iterateNodes(final CAstNode tree) {
    return new DFSDiscoverTimeIterator<CAstNode>() {

      private final Map<Object, Iterator<? extends CAstNode>> pendingChildren = HashMapFactory.make();

      protected Iterator<? extends CAstNode> getPendingChildren(CAstNode n) {
        return pendingChildren.get(n);
      }

      protected void setPendingChildren(CAstNode v, Iterator<? extends CAstNode> iterator) {
        pendingChildren.put(v, iterator);
      }

      protected Iterator<CAstNode> getConnected(final CAstNode n) {
        return new Iterator<CAstNode>() {
          private int i = 0;

          public boolean hasNext() {
            return i < ((CAstNode) n).getChildCount();
          }

          public CAstNode next() {
            return ((CAstNode) n).getChild(i++);
          }

          public void remove() {
            throw new UnsupportedOperationException();
          }
        };
      }

      {
        init(tree);
      }
    };
  }

  public static Iterator<CAstNode> findAll(CAstNode tree, Filter<?> f) {
    return new FilterIterator<CAstNode>(iterateNodes(tree), f);
  }

}
