/*
 * Copyright (c) 2021 Deloitte. All rights reserved.
 */
package exercise6.ast;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Supplier;

import exercise6.ast.nodes.CalculateNode;
import exercise6.ast.nodes.InputNode;
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
			((CalculateNode)node).value = calc(((CalculateNode) node), ast);
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

	private double calc(CalculateNode node, SimpleAst ast) {
		char[] tokens = node.expression.toCharArray();
		Stack<Double> values = new Stack<Double>();
		Stack<Character> ops = new Stack<Character>();
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i] == ' ') {
				continue;
			}
			if (Character.isDigit(tokens[i])) {
				StringBuilder number = new StringBuilder();

				while (i < tokens.length && Character.isDigit(tokens[i])) {
					number.append(tokens[i++]);
				}
				values.push(Double.parseDouble((number.toString())));
				i--;
			}
			if (Character.isLetter(tokens[i])) {
				StringBuilder variable = new StringBuilder();

				while (i < tokens.length && Character.isLetter(tokens[i])) {
					variable.append(tokens[i++]);
				}
				List<Node> nodes = ast.getNodes();
				if (nodes.size() != 0) {
					for (Node check : nodes) {
						if (check instanceof VariableDeclarationNode) {
							if (node.var != null) {
								if (((VariableDeclarationNode) check).variable.equals(node.var)) {
									node.var = null;
									((VariableDeclarationNode) check).value = calc(node, ast);
								}
							} else if (((VariableDeclarationNode) check).variable.equals(variable.toString())) {
								values.push(((VariableDeclarationNode) check).value);
							}
						}
					}
				}
				i--;
			} else if (tokens[i] == '(') {
				ops.push(tokens[i]);
			} else if (tokens[i] == ')') {
				while (ops.peek() != '(') {
					values.push(applyOp(ops.pop(), values.pop(), values.pop()));
				}
				ops.pop();
			} else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/'
					|| tokens[i] == '^') {
				while (!ops.empty() && hasPrecedence(tokens[i], ops.peek())) {
					values.push(applyOp(ops.pop(), values.pop(), values.pop()));
				}
				ops.push(tokens[i]);
			}
		}
		while (!ops.empty()) {
			values.push(applyOp(ops.pop(), values.pop(), values.pop()));
		}
		double ret = (double) Math.round(values.pop() * 100d) / 100d;
		return ret;
	}

	private boolean hasPrecedence(char op2, Character op1) {
		if (op1 == '(' || op1 == ')') {
			return false;
		}
		if (op2 == '*' || op2 == '/' && op1 == '+' || op1 == '-') {
			return false;
		}
		if (op2 == '^' && op1 == '*' || op1 == '/' || op1 == '+' || op1 == '-') {
			return false;
		} else
			return true;
	}

	private Double applyOp(Character op, Double num2, Double num1) {
		switch (op) {
		case '+':
			return num1 + num2;
		case '-':
			return num1 - num2;
		case '*':
			return num1 * num2;
		case '/':
			if (num2 == 0) {
				throw new UnsupportedOperationException("Can't divide by 0");
			} else
				return num1 / num2;
		case '^':
			return (Double) Math.pow(num1, num2);
		}
		return 0.0;
	}
}
