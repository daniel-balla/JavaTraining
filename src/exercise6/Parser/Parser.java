/*
 * Copyright (c) 2021 Deloitte. All rights reserved.
 */
package exercise6.Parser;

import java.util.List;

import exercise6.Tokenizer.Token;
import exercise6.ast.SimpleAst;

public interface Parser {

	/**
	 * Parses the Input String and returns an abstract representation of it, divided
	 * into the nodes
	 *
	 * @param input to parse
	 * @return the simple AST representing the input
	 * @throws ScriptException 
	 */
	SimpleAst parse(List<Token> input);

}
