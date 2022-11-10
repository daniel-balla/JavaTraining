/*
 * Copyright (c) 2021 Deloitte. All rights reserved.
 */
package exercise6.ast.nodes;

import java.util.List;

import exercise6.ast.Node;
import exercise6.ast.SimpleAst;

/**
 * PRINT statement Node, defined as "PRINT " followed by the message to be
 * printed
 */
public class PrintNode extends Node {

	private String message;

	/**
	 * Creates a new Print node
	 * 
	 * @param message to print
	 */
	public PrintNode(final String message, SimpleAst ast) {
		if (message.contains("(") && message.contains(")")) {
			int p = message.indexOf("(");
			String h = Character.toString(message.charAt(p));
			List<Node> nodes = ast.getNodes();
			if (nodes.size() != 0) {
				for (Node node : nodes) {
					if (node instanceof VariableDeclarationNode) {
						if (((VariableDeclarationNode) node).variable.equals(h)) {
							this.message = Double.toString(((VariableDeclarationNode) node).value);
						}
					}
				}
			}
		} else
			this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	@Override
	public String toString() {
		return message;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(!(o instanceof PrintNode)) {
			return false;
		}
		PrintNode other = (PrintNode) o;
		return this.message.equals(other.message);
	}
}
