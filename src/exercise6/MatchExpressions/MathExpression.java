package exercise6.MatchExpressions;

import exercise6.MatchExpressions.nodes.DivideNode;
import exercise6.MatchExpressions.nodes.ExponentNode;
import exercise6.MatchExpressions.nodes.MinusNode;
import exercise6.MatchExpressions.nodes.MultiplicationNode;
import exercise6.MatchExpressions.nodes.PlusNode;
import exercise6.ast.Node;

public class MathExpression extends Node{

	public Node left;
	public MathExpression operator;
	public Node right;
	
	public MathExpression(Node left, MathExpression operator, Node right) {
		this.left = left;
		this.right = right;
		this.operator = operator;
	}
	
	public double eval(double leftVal, double rightVal) {
		if(this instanceof PlusNode) {
			return leftVal + rightVal;
		} else if(this instanceof MinusNode) {
			return leftVal - rightVal;
		} else if(this instanceof MultiplicationNode) {
			return leftVal * rightVal;
		} else if(this instanceof DivideNode) {
			return leftVal / rightVal; 
		} else if(this instanceof ExponentNode) {
			return Math.pow(leftVal, rightVal);
		}
		return 0;
	}
	
	public MathExpression() {
		
	}
	
	@Override
	public String toString() {
		return "(" + left + " " + operator + " " + right + ")";
	}
}
