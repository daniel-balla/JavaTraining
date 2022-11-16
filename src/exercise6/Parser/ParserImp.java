package exercise6.Parser;

import java.util.LinkedList;
import java.util.List;

import exercise6.Tokenizer.KeyWord;
import exercise6.Tokenizer.Token;
import exercise6.ast.SimpleAst;
import exercise6.ast.nodes.CalculateNode;
import exercise6.ast.nodes.InputNode;
import exercise6.ast.nodes.LiteralNode;
import exercise6.ast.nodes.PrintNode;
import exercise6.ast.nodes.PrintiNode;
import exercise6.ast.nodes.VariableDeclarationNode;

public class ParserImp implements Parser {

	@Override
	public SimpleAst parse(List<Token> input) {
		ExpressionParser expressionParser = new ExpressionParser();
		SimpleAst ast = new SimpleAst();
		for (int i = 0; i < input.size(); i++) {
			if (input.get(i).keyWord.equals(KeyWord.Command)) {
				switch (input.get(i).text) {
				case "INPUT":
					ast.addNode(new InputNode());
					break;
				case "PRINTI":
					ast.addNode(new PrintiNode());
					break;
				case "PRINT":
					ast.addNode(new PrintNode(input.get(i + 1).text, ast));
					break;
				case "CALCULATE":
					List<Token> exp = new LinkedList<>();
					for (int j = i + 1; j < input.size(); j++) {
						if (!(input.get(j).keyWord.equals(KeyWord.SemiColon))) {
							exp.add(input.get(j));
						} else {
							break;
						}
					}
					ast.addNode(new CalculateNode(expressionParser.parse(exp, ast), null));
					break;
				case "VARIABLE":
					String var = input.get(i + 1).text;
					switch (input.get(i + 2).keyWord) {
					case SemiColon:
						ast.addNode(new VariableDeclarationNode(null, var, null));
						break;
					case Equals:
						if (input.get(i + 3).keyWord.equals(KeyWord.Integer)) {
							LiteralNode n = new LiteralNode(input.get(i + 3).text, ast);
							ast.addNode(new VariableDeclarationNode(n, var, ast));
						} else {
							List<Token> exs = new LinkedList<>();
							for (int j = i + 4; j < input.size(); j++) {
								if (!(input.get(j).keyWord.equals(KeyWord.SemiColon))) {
									exs.add(input.get(j));
								} else {
									break;
								}
							}
							CalculateNode c = new CalculateNode(expressionParser.parse(exs, ast), var);
							ast.addNode(new VariableDeclarationNode(c, var, ast));
						}
					default:
						break;
					}
				}
			}
		}
		return ast;
	}
}
