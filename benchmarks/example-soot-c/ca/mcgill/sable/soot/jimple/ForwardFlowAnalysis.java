/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Jimple, a 3-address code Java(TM) bytecode representation.        *
 * Copyright (C) 1997, 1998 Raja Vallee-Rai (kor@sable.mcgill.ca)    *
 * All rights reserved.                                              *
 *                                                                   *
 * This work was done as a project of the Sable Research Group,      *
 * School of Computer Science, McGill University, Canada             *
 * (http://www.sable.mcgill.ca/).  It is understood that any         *
 * modification not identified as such is not covered by the         *
 * preceding statement.                                              *
 *                                                                   *
 * This work is free software; you can redistribute it and/or        *
 * modify it under the terms of the GNU Library General Public       *
 * License as published by the Free Software Foundation; either      *
 * version 2 of the License, or (at your option) any later version.  *
 *                                                                   *
 * This work is distributed in the hope that it will be useful,      *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of    *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU *
 * Library General Public License for more details.                  *
 *                                                                   *
 * You should have received a copy of the GNU Library General Public *
 * License along with this library; if not, write to the             *
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,      *
 * Boston, MA  02111-1307, USA.                                      *
 *                                                                   *
 * Java is a trademark of Sun Microsystems, Inc.                     *
 *                                                                   *
 * To submit a bug report, send a comment, or get the latest news on *
 * this project and other Sable Research Group projects, please      *
 * visit the web site: http://www.sable.mcgill.ca/                   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

/*
 Reference Version
 -----------------
 This is the latest official version on which this file is based.
 The reference version is: $SootVersion: 1.beta.4.dev.7 $

 Change History
 --------------
 A) Notes:

 Please use the following template.  Most recent changes should
 appear at the top of the list.

 - Modified on [date (March 1, 1900)] by [name]. [(*) if appropriate]
   [description of modification].

 Any Modification flagged with "(*)" was done as a project of the
 Sable Research Group, School of Computer Science,
 McGill University, Canada (http://www.sable.mcgill.ca/).

 You should add your copyright, using the following template, at
 the top of this file, along with other copyrights.

 *                                                                   *
 * Modifications by [name] are                                       *
 * Copyright (C) [year(s)] [your name (or company)].  All rights     *
 * reserved.                                                         *
 *                                                                   *

 B) Changes:

 - Modified on November 2, 1998 by Raja Vallee-Rai (kor@sable.mcgill.ca) (*)
   Repackaged all source files and performed extensive modifications.
   First initial release of Soot.

 - Modified on 15-Jun-1998 by Raja Vallee-Rai (kor@sable.mcgill.ca). (*)
   First internal release (Version 0.1).
*/

package ca.mcgill.sable.soot.jimple;

import ca.mcgill.sable.soot.*;
import ca.mcgill.sable.util.*;

public abstract class ForwardFlowAnalysis extends FlowAnalysis
{
    public ForwardFlowAnalysis(StmtGraph graph)
    {
        super(graph);
    }

    protected boolean isForward()
    {
        return true;
    }
    protected void doAnalysis()
    {
        LinkedList changedStmts = new LinkedList();
        HashSet changedStmtsSet = new HashSet();

        int numNodes = graph.size();
        int numComputations = 0;
        
        // Set initial values and nodes to visit.
        {
            Iterator it = graph.iterator();

            while(it.hasNext())
            {
                Stmt s = (Stmt) it.next();

                changedStmts.addLast(s);
                changedStmtsSet.add(s);

                stmtToBeforeFlow.put(s, newInitialFlow());
                stmtToAfterFlow.put(s, newInitialFlow());
            }
        }

        // Perform fixed point flow analysis
        {
            Object previousAfterFlow = newInitialFlow();

            while(!changedStmts.isEmpty())
            {
                Object beforeFlow;
                Object afterFlow;

                Stmt s = (Stmt) changedStmts.removeFirst();

                changedStmtsSet.remove(s);

                copy(stmtToAfterFlow.get(s), previousAfterFlow);

                // Compute and store beforeFlow
                {
                    List preds = graph.getPredsOf(s);

                    beforeFlow = stmtToBeforeFlow.get(s);

                    if(preds.size() == 1)
                        copy(stmtToAfterFlow.get(preds.get(0)), beforeFlow);
                    else if(preds.size() != 0)
                    {
                        Iterator predIt = preds.iterator();

                        copy(stmtToAfterFlow.get(predIt.next()), beforeFlow);

                        while(predIt.hasNext())
                        {
                            Object otherBranchFlow = stmtToAfterFlow.get(predIt.
next());
                            merge(beforeFlow, otherBranchFlow, beforeFlow);
                        }
                    }
                }

                // Compute afterFlow and store it.
                {
                    afterFlow = stmtToAfterFlow.get(s);
                    flowThrough(beforeFlow, s, afterFlow);
                    numComputations++;
                }

                // Update queue appropriately
                    if(!afterFlow.equals(previousAfterFlow))
                    {
                        Iterator succIt = graph.getSuccsOf(s).iterator();

                        while(succIt.hasNext())
                        {
                            Stmt succ = (Stmt) succIt.next();
                            
                            if(!changedStmtsSet.contains(succ))
                            {
                                changedStmts.addLast(succ);
                                changedStmtsSet.add(succ);
                            }
                        }
                    }
            }
        }
        
        // System.out.println("{" + graph.getBody().getMethod().getSignature() + "} numNodes: " + numNodes + 
        //    " numComputations: " + numComputations + " avg: " + Main.truncatedOf((double) numComputations / numNodes, 2));
        
        Main.totalFlowNodes += numNodes;
        Main.totalFlowComputations += numComputations;
    }

}


