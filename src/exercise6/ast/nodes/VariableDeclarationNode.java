package exercise6.ast.nodes;

import exercise6.ast.Node;
import exercise6.ast.SimpleAst;

/**
 * VARIABLE statement Node, defined as "VARIABLE" followed by the variable
 * assignment
 */
public class VariableDeclarationNode extends Node {

	public double value;
	public String variable;

	public VariableDeclarationNode(ValueProviderNode node, String variable, SimpleAst ast) {
		if (node != null) {
			if (node instanceof CalculateNode) {
				ast.addNode((CalculateNode) node);
			}
			this.value = node.value;
		}
		this.variable = variable;
	}

	@Override
	public String toString() {
		return variable + " = " + value;
	}
}
