package exercise6.MatchExpressions.nodes;

import exercise6.MatchExpressions.MathExpression;
import exercise6.ast.Node;

public class DivideNode extends MathExpression{

	public DivideNode(Node left, MathExpression operator, Node right) {
		super(left, operator, right);
		// TODO Auto-generated constructor stub
	}
	
	public DivideNode() {
		
	}
	
	@Override
	public String toString() {
		return "/";
	}

}
