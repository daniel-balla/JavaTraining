/*
 * Copyright (c) 2021 Deloitte. All rights reserved.
 */
package exercise6.ast;

import java.util.LinkedList;
import java.util.List;

/**
 * Just a simple syntax tree (AST = Abstract Syntax Tree)
 */
public class SimpleAst {

	private final List<Node> nodes = new LinkedList<>();

	/**
	 * Retrieves all the AST nodes
	 * @return list of AST nodes
	 */
	public List<Node> getNodes() {
		return nodes;
	}

	/**
	 * Adds a node to this Ast.
	 * @param node to add
	 */
	public void addNode(Node node) {
		nodes.add(node);
	}
	
	@Override
	public String toString() {
		return nodes.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(!(o instanceof SimpleAst)) {
			return false;
		}
		SimpleAst other = (SimpleAst) o;
		for(Node no : this.getNodes()) {
			for(Node ne : other.getNodes()) {
				if(!(no.equals(ne))) {
					return false;
				}
			}
		}
		return true;
	}

}
