package exercise6.MatchExpressions.nodes;

import exercise6.MatchExpressions.MathExpression;
import exercise6.ast.Node;

public class MinusNode extends MathExpression{

	public MinusNode(Node left, MathExpression operator, Node right) {
		super(left, operator, right);
		// TODO Auto-generated constructor stub
	}

	public MinusNode() {
		
	}
	
	@Override
	public String toString() {
		return "-";
	}
}
