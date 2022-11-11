/*
 * Copyright (c) 2021 Deloitte. All rights reserved.
 */
package exercise6.ast;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import exercise6.ExpressionParser;
import exercise6.MatchExpressions.MathExpression;
import exercise6.MatchExpressions.nodes.DivideNode;
import exercise6.MatchExpressions.nodes.ExponentNode;
import exercise6.MatchExpressions.nodes.MinusNode;
import exercise6.MatchExpressions.nodes.MultiplicationNode;
import exercise6.MatchExpressions.nodes.PlusNode;
import exercise6.ast.nodes.CalculateNode;
import exercise6.ast.nodes.InputNode;
import exercise6.ast.nodes.LiteralNode;
import exercise6.ast.nodes.PrintNode;
import exercise6.ast.nodes.PrintiNode;
import exercise6.ast.nodes.VariableDeclarationNode;

public class AstInterpreter {

	/**
	 * Stack containing all inputs of the input statements called.
	 */
	private final Deque<String> inputStack = new LinkedList<>();

	private Consumer<String> printMessageConsumer;
	private Supplier<String> inputMessageProvider;

	private AstInterpreter(Consumer<String> printMessageConsumer, Supplier<String> inputMessageProvider) {
		this.printMessageConsumer = printMessageConsumer;
		this.inputMessageProvider = inputMessageProvider;
	}

	/**
	 * Executes each instruction in given AST.
	 * 
	 * @param ast to execute
	 */
	public void execute(final SimpleAst ast) {
		final List<Node> nodes = ast.getNodes();
		nodes.forEach(s -> execute(s, ast));
	}

	private void execute(final Node node, SimpleAst ast) {
		/* New commands can be added below */
		ExpressionParser expressionParser = new ExpressionParser();
		if (node instanceof PrintNode) {
			if (printMessageConsumer == null) {
				throw new IllegalStateException("Please provide a print message consumer to use the PRINT statements.");
			}
			printMessageConsumer.accept(((PrintNode) node).getMessage());
		} else if (node instanceof InputNode) {
			if (inputMessageProvider == null) {
				throw new IllegalStateException("Please provide a input message supplier to use the INPUT statements.");
			}
			inputStack.push(inputMessageProvider.get());
		} else if (node instanceof PrintiNode) {
			if (inputStack == null) {
				throw new IllegalStateException("InputStack is empty");
			}
			printMessageConsumer.accept(inputStack.pop());
		} else if (node instanceof CalculateNode) {
			if (printMessageConsumer == null) {
				throw new IllegalStateException("Error");
			}
			CalculateNode cN = (CalculateNode)node;
			cN.value = evalTree((MathExpression) expressionParser.parse(cN.expression, ast));
			printMessageConsumer.accept(Double.toString(((CalculateNode) node).value));
		} else if (node instanceof VariableDeclarationNode) {
			if (printMessageConsumer == null) {
				throw new IllegalStateException("");
			}
			printMessageConsumer.accept(((VariableDeclarationNode) node).toString());
		} else {
			throw new IllegalStateException("Unhandled Node Type: " + node.getClass());
		}
	}

	/**
	 * Builder Pattern (this is the next level of code style: usage of design
	 * patterns)
	 */
	public static class Builder {

		private Consumer<String> printMessageConsumer;
		private Supplier<String> inputMessageProvider;

		public Builder printMessageConsumer(Consumer<String> printMessageConsumer) {
			this.printMessageConsumer = printMessageConsumer;
			return this;
		}

		public Builder inputMessageProvider(Supplier<String> inputMessageProvider) {
			this.inputMessageProvider = inputMessageProvider;
			return this;
		}

		public AstInterpreter build() {
			return new AstInterpreter(printMessageConsumer, inputMessageProvider);
		}
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
