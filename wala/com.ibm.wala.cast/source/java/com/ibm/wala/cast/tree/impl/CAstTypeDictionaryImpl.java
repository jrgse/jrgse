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
/*
 * Created on Sep 21, 2005
 */
package com.ibm.wala.cast.tree.impl;

import java.util.Iterator;
import java.util.Map;

import com.ibm.wala.cast.tree.CAstReference;
import com.ibm.wala.cast.tree.CAstType;
import com.ibm.wala.cast.tree.CAstTypeDictionary;
import com.ibm.wala.util.collections.HashMapFactory;

public class CAstTypeDictionaryImpl implements CAstTypeDictionary {
  protected final Map fMap = HashMapFactory.make();

  public CAstType getCAstTypeFor(Object/*ASTType*/ astType) {
      return (CAstType) fMap.get(astType);
  }

  @SuppressWarnings("unchecked")
  public void map(Object/*ASTType*/ astType, CAstType castType) {
    fMap.put(astType, castType);
  }

  public Iterator iterator() {
    return fMap.values().iterator();
  }

  public CAstReference resolveReference(CAstReference ref) {
    return ref;
  }
}