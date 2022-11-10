package exercise6.ast.nodes;

/**
 * CALCULATE statement Node, defined as "CALCULATE " followed by the expression
 * to be evaluated
 */
public class CalculateNode extends ValueProviderNode {

	public String expression;
	public String var;
	
	/**
	 * Creates a new Calculate node
	 * 
	 * @param expression to be evaluated
	 * @throws ScriptException
	 */
	public CalculateNode(String expression, String var) {
		this.expression = expression;
		this.var = var;
	}
	
	@Override
	public String toString() {
		return expression;
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
