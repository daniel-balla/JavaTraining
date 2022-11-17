package exercise6.Parser;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

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
		ListIterator<Token> iterator = input.listIterator();
		SimpleAst ast = new SimpleAst();
		while (iterator.hasNext()) {
			Token p0 = iterator.next();
			Token p1 = iterator.next();
			if (p0.keyWord.equals(KeyWord.Command)) {
				switch (p0.text) {
				case "INPUT":
					ast.addNode(new InputNode());
					break;
				case "PRINTI":
					ast.addNode(new PrintiNode());
					break;
				case "PRINT":
					ast.addNode(new PrintNode(p1.text, ast));
					iterator.previous();
					break;
				case "CALCULATE":
					List<Token> exp = new LinkedList<>();
					Token pm = p1; 
					exp.add(pm);
					while (iterator.hasNext()) {
						pm = iterator.next();
						if (!(pm.keyWord.equals(KeyWord.SemiColon))) {
							exp.add(pm);
						} else {
							break;
						}
					}
					ast.addNode(new CalculateNode(expressionParser.parse(exp, ast), null));
					break;
				case "VARIABLE":
					String var = p1.text;
					Token p2 = iterator.next();
					switch (p2.keyWord) {
					case SemiColon:
						ast.addNode(new VariableDeclarationNode(null, var, null));
						break;
					case Equals:
						Token p3 = iterator.next();
						if (p3.keyWord.equals(KeyWord.Integer)) {
							LiteralNode n = new LiteralNode(p3.text, ast);
							ast.addNode(new VariableDeclarationNode(n, var, ast));
							iterator.next();
						} else {
							List<Token> exs = new LinkedList<>();
							Token pn = iterator.next();
							while (iterator.hasNext()) {
								if (!(pn.keyWord.equals(KeyWord.SemiColon))) {
									exs.add(pn);
									pn = iterator.next();
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
