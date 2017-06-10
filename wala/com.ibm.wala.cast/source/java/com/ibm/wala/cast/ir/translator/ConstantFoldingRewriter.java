package com.ibm.wala.cast.ir.translator;

import java.util.Map;

import com.ibm.wala.cast.tree.CAst;
import com.ibm.wala.cast.tree.CAstControlFlowMap;
import com.ibm.wala.cast.tree.CAstNode;
import com.ibm.wala.cast.tree.impl.CAstOperator;
import com.ibm.wala.cast.tree.rewrite.CAstBasicRewriter;
import com.ibm.wala.util.collections.Pair;

public abstract class ConstantFoldingRewriter extends CAstBasicRewriter {

  protected ConstantFoldingRewriter(CAst Ast) {
    super(Ast, true);
  }

  protected abstract Object eval(CAstOperator op, Object lhs, Object rhs);
  
  @Override
  protected CAstNode copyNodes(CAstNode root, 
      CAstControlFlowMap cfg, 
      NonCopyingContext context,
      Map<Pair<CAstNode, NoKey>, CAstNode> nodeMap) 
  {
    CAstNode result;
    if (root.getKind() == CAstNode.BINARY_EXPR) {
      CAstNode left = copyNodes(root.getChild(1), cfg, context, nodeMap);
      CAstNode right = copyNodes(root.getChild(2), cfg, context, nodeMap);
      Object v;
      if (left.getKind() == CAstNode.CONSTANT && right.getKind() == CAstNode.CONSTANT && (v = eval((CAstOperator) root.getChild(0), left.getValue(), right.getValue())) != null) {
        result = Ast.makeConstant(v);
      } else {
        result = Ast.makeNode(CAstNode.BINARY_EXPR, root.getChild(0), left, right);
      }

    } else if (root.getKind() == CAstNode.IF_EXPR || root.getKind() == CAstNode.IF_STMT) {
      CAstNode expr = copyNodes(root.getChild(0), cfg, context, nodeMap);
      if (expr.getKind() == CAstNode.CONSTANT && expr.getValue() == Boolean.TRUE) {
        result = copyNodes(root.getChild(1), cfg, context, nodeMap);
      } else if (expr.getKind() == CAstNode.CONSTANT && root.getChildCount() > 2 && expr.getValue() == Boolean.FALSE) {
        result = copyNodes(root.getChild(2), cfg, context, nodeMap);
      } else {
        CAstNode then = copyNodes(root.getChild(1), cfg, context, nodeMap);
        if (root.getChildCount() == 3) {
          result = Ast.makeNode(root.getKind(), expr, then, copyNodes(root.getChild(2), cfg, context, nodeMap));
        } else {
          result = Ast.makeNode(root.getKind(), expr, then);
        }
      }
      
    } else if (root.getKind() == CAstNode.CONSTANT) {
      result = Ast.makeConstant(root.getValue());

    } else if (root.getKind() == CAstNode.OPERATOR) {
      result = root;
      
    } else {
      CAstNode children[] = new CAstNode[root.getChildCount()];
      for (int i = 0; i < children.length; i++) {
        children[i] = copyNodes(root.getChild(i), cfg, context, nodeMap);
      }
      for(Object label: cfg.getTargetLabels(root)) {
        if (label instanceof CAstNode) {
          copyNodes((CAstNode)label, cfg, context, nodeMap);
        }
      }
      CAstNode copy = Ast.makeNode(root.getKind(), children);
      result = copy;
    }

    nodeMap.put(Pair.make(root, context.key()), result);
    return result;
  }

}
