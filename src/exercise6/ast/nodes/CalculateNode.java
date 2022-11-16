package exercise6.ast.nodes;

import exercise6.ast.Node;

/**
 * CALCULATE statement Node, defined as "CALCULATE " followed by the expression
 * to be evaluated
 */
public class CalculateNode extends ValueProviderNode {

	public Node expression;
	public String var;
	
	/**
	 * Creates a new Calculate node
	 * 
	 * @param expression to be evaluated
	 * @throws ScriptException
	 */
	public CalculateNode(Node expression, String var) {
		this.expression = expression;
		this.var = var;
	}
	
	@Override
	public String toString() {
		return expression.toString();
	}
	
	@Override
	public boolean equals(Object o) {
	if(this == o) {
		return true;
	}
	if(!(o instanceof CalculateNode)) {
		return false;
	}
	CalculateNode n = (CalculateNode)o;
	return this.expression.equals(n.expression);
	}
}
