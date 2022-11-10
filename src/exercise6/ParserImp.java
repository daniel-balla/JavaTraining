package exercise6;

import exercise6.ast.SimpleAst;
import exercise6.ast.nodes.CalculateNode;
import exercise6.ast.nodes.InputNode;
import exercise6.ast.nodes.LiteralNode;
import exercise6.ast.nodes.PrintNode;
import exercise6.ast.nodes.PrintiNode;
import exercise6.ast.nodes.VariableDeclarationNode;

public class ParserImp implements Parser {

	@Override
	public SimpleAst parse(String[][] input) {
		SimpleAst ast = new SimpleAst();
		Tokenizer tokenizer = new Tokenizer();
		for (int i = 0; i < input.length; i++) {
			switch (input[i][0]) {
			case "PRINT":
				ast.addNode(new PrintNode(input[i][1], ast));
				break;
			case "INPUT":
				ast.addNode(new InputNode());
				break;
			case "PRINTI":
				ast.addNode(new PrintiNode());
				break;
			case "CALCULATE":
				ast.addNode(new CalculateNode(input[i][1], null));
				break;
			case "VARIABLE":
				String a = input[i][1];
				String var = Character.toString(a.charAt(0));
				if (a.contains("CALCULATE")) {
					while (a.charAt(0) != 'C') {
						a = a.replaceFirst(Character.toString(a.charAt(0)), "");
					}
					a += ";";
					String[][] nd = tokenizer.tokenize(a);
					CalculateNode c = new CalculateNode(nd[0][1], var);
					VariableDeclarationNode v = new VariableDeclarationNode(c, var, ast);
					ast.addNode(v);
				} else if (a.contains("=")) {
					int g = a.indexOf("=");
					StringBuilder b = new StringBuilder();
					for (int f = g + 1; f < a.length(); f++) {
						if (a.charAt(f) != ' ') {
							b.append(a.charAt(f));
						}
					}
					LiteralNode l = new LiteralNode(b.toString(), ast);
					VariableDeclarationNode y = new VariableDeclarationNode(l, var, ast);
					ast.addNode(y);
				} else {
					VariableDeclarationNode x = new VariableDeclarationNode(null, var, ast);
					ast.addNode(x);
				}
				break;
			}
		}
		return ast;
	}
}
