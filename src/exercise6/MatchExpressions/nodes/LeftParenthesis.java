package exercise6.MatchExpressions.nodes;

import exercise6.MatchExpressions.MathExpression;
import exercise6.ast.Node;

public class LeftParenthesis extends MathExpression{

	public LeftParenthesis(Node left, MathExpression operator, Node right) {
		super(left, operator, right);
		// TODO Auto-generated constructor stub
	}
	
	public LeftParenthesis() {
		
	}

	@Override
	public String toString() {
		return "(";
	}
}
