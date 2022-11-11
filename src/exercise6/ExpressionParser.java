package exercise6;

import java.util.List;
import java.util.Stack;

import exercise6.MatchExpressions.MathExpression;
import exercise6.MatchExpressions.nodes.DivideNode;
import exercise6.MatchExpressions.nodes.ExponentNode;
import exercise6.MatchExpressions.nodes.LeftParenthesis;
import exercise6.MatchExpressions.nodes.MinusNode;
import exercise6.MatchExpressions.nodes.MultiplicationNode;
import exercise6.MatchExpressions.nodes.PlusNode;
import exercise6.MatchExpressions.nodes.RightParenthesis;
import exercise6.ast.Node;
import exercise6.ast.SimpleAst;
import exercise6.ast.nodes.LiteralNode;
import exercise6.ast.nodes.VariableDeclarationNode;

public class ExpressionParser {

	public Node parse(String input, SimpleAst ast) {
		int curIndex = 0;
		boolean afterOperand = false;
		Stack<Node> operands = new Stack<>();
		Stack<MathExpression> operators = new Stack<>();
		while (curIndex < input.length()) {
			int startIndex = curIndex;
			char c = input.charAt(curIndex++);

			if (Character.isWhitespace(c)) {
				continue;
			}
			if (afterOperand) {
				if (c == ')') {
					MathExpression operator;
					while (!operators.isEmpty() && (!((operator = operators.pop()) instanceof LeftParenthesis))) {
						createNewOperand(operator, operands);
					}
					continue;
				}
				afterOperand = false;
				MathExpression operator = convert(c);
				while (!operators.isEmpty() && (!(operators.peek() instanceof LeftParenthesis))
						&& (getRank(operators.peek()) >= getRank(operator))) {
					createNewOperand(operators.pop(), operands);
				}
				operators.push(operator);
				continue;
			}
			if (c == '(') {
				operators.push(new LeftParenthesis());
				continue;
			}
			afterOperand = true;
			while (curIndex < input.length()) {
				c = input.charAt(curIndex);
				if (((c < '0') || (c > '9')) && (c != '.') && (c < 'a') || (c > 'z')) {
					break;
				}
				curIndex++;
			}
			String in = input.substring(startIndex, curIndex);
			if (Character.isDigit(in.charAt(0))) {
				operands.push(new LiteralNode(Double.valueOf(input.substring(startIndex, curIndex))));
			} else {
				List<Node> nodes = ast.getNodes();
				if (nodes.size() != 0) {
					for (Node check : nodes) {
						if (check instanceof VariableDeclarationNode) {
							if (((VariableDeclarationNode) check).variable.equals(in)) {
								operands.push((VariableDeclarationNode) check);
							}
						}
					}
				}
			}
		}
		while (!operators.isEmpty()) {
			MathExpression operator = operators.pop();
			if (operator instanceof LeftParenthesis) {
				throw new IllegalArgumentException();
			}
			createNewOperand(operator, operands);
		}
		Node expression = operands.pop();
		if (!operands.isEmpty()) {
			throw new IllegalArgumentException();
		}
		return expression;

	}

	private static void createNewOperand(MathExpression operator, Stack<Node> operands) {
		Node right = operands.pop();
		Node left = operands.pop();
		operands.push(new MathExpression(left, operator, right));
	}

	public static boolean isOperator(Node node) {
		if (node instanceof MathExpression) {
			return true;
		}
		return false;
	}

	public static int getRank(char ch) {
		if (ch == '+' || ch == '-') {
			return 1;
		} else if (ch == '*' || ch == '/') {
			return 2;
		} else if (ch == '^') {
			return 3;
		} else if (ch == ')') {
			return 0;
		}
		return -1;
	}

	public static int getRank(MathExpression e) {
		if (e instanceof PlusNode || e instanceof MinusNode) {
			return 1;
		} else if (e instanceof MultiplicationNode || e instanceof DivideNode) {
			return 2;
		} else if (e instanceof ExponentNode) {
			return 3;
		} else if (e instanceof RightParenthesis) {
			return 0;
		}
		return -1;
	}

	public static MathExpression convert(char c) {
		switch (c) {
		case '+':
			return new PlusNode();
		case '-':
			return new MinusNode();
		case '*':
			return new MultiplicationNode();
		case '/':
			return new DivideNode();
		case '^':
			return new ExponentNode();
		case '(':
			return new LeftParenthesis();
		case ')':
			return new RightParenthesis();
		}
		return null;
	}

	public double evalTree(MathExpression root) {

		double leftEval, rightEval;
		if (root == null) {
			return 0;
		}
		if (root.left == null && root.right == null) {
			return 0;
		}
		if (root.left instanceof MathExpression) {
			 leftEval = evalTree((MathExpression)root.left);
		} else {
			if(root.left instanceof LiteralNode) {
				 leftEval = ((LiteralNode)root.left).value;
			} else {
				 leftEval = ((VariableDeclarationNode)root.left).value;
			}
		}
		if (root.right instanceof MathExpression) {
			 rightEval = evalTree((MathExpression)root.right);
		} else {
			if(root.right instanceof LiteralNode) {
				 rightEval = ((LiteralNode)root.right).value;
			} else {
				 rightEval = ((VariableDeclarationNode)root.right).value;
			}
		}
		if(root.operator instanceof PlusNode) {
			return leftEval + rightEval;
		} else if(root.operator instanceof MinusNode) {
			return leftEval - rightEval;
		} else if(root.operator instanceof MultiplicationNode) {
			return leftEval * rightEval;
		} else if(root.operator instanceof DivideNode) {
			return leftEval / rightEval; 
		} else if(root.operator instanceof ExponentNode) {
			return Math.pow(leftEval, rightEval);
		}
		return 0;
	}
}

















