/* Copyright (c) 2021 Deloitte. All rights reserved. */
package exercise6;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import exercise6.MatchExpressions.MathExpression;
import exercise6.Parser.ExpressionParser;
import exercise6.Parser.Parser;
import exercise6.Parser.ParserImp;
import exercise6.Tokenizer.KeyWord;
import exercise6.Tokenizer.Token;
import exercise6.Tokenizer.TokenizerSt;
import exercise6.ast.AstInterpreter;
import exercise6.ast.Node;
import exercise6.ast.SimpleAst;
import exercise6.ast.nodes.CalculateNode;
import exercise6.ast.nodes.PrintNode;

/**
 * Diese kleine feine Sprache benötigt noch einen Parser, der aus den Statements
 * einen Abstrakten Syntax Baum generiert, der danach durchlaufen und
 * interpretiert werden kann.
 * 
 * Außerdem könnte man die "Sprache" noch um ein paar Funktionalitäten
 * erweitern. z.B. um ein PRINTI statment, das den nächsten INPUT ausgiebt, oder
 * um ein CALCULATE was einen Arithmetischen Ausdruck entgegen nimmt und
 * evaluiert.
 */
class TestSimpleInterpretedLanguageRegexParser {

	private final Parser simpleLanguageParser = new ParserImp();
	private final ExpressionParser expressionParser = new ExpressionParser();
	private final TokenizerSt tokenizerSt = new TokenizerSt();
	private final Token ii = new Token(KeyWord.Integer, "1");
	private final Token d = new Token(KeyWord.Integer, "2");
	private final Token e = new Token(KeyWord.Multiplication, "*");
	private final Token f = new Token(KeyWord.LeftParenthesis, "(");
	private final Token g = new Token(KeyWord.Integer, "3");
	private final Token h = new Token(KeyWord.Plus, "+");
	private final Token j = new Token(KeyWord.Integer, "4");
	private final Token l = new Token(KeyWord.Integer, "5");
	private final Token o = new Token(KeyWord.Integer, "6");
	private final Token q = new Token(KeyWord.Integer, "7");
	private final Token r = new Token(KeyWord.RightParenthesis, ")");
	private final Token t = new Token(KeyWord.Integer, "8");
	private final Token v = new Token(KeyWord.Minus, "-");
	private final Token w = new Token(KeyWord.Integer, "9");
	private final Token z = new Token(KeyWord.Integer, "10");
	private final Token di = new Token(KeyWord.Divide, "/");
	private final Token ex = new Token(KeyWord.Exponent, "^");
	private final Token pr = new Token(KeyWord.Command, "PRINT");
	private final Token pri = new Token(KeyWord.Command, "PRINTI");
	private final Token in = new Token(KeyWord.Command, "INPUT");
	private final Token cal = new Token(KeyWord.Command, "CALCULATE");
	private final Token var = new Token(KeyWord.Command, "VARIABLE");
	private final Token semi = new Token(KeyWord.SemiColon, ";");

	@Test
	void testParser() {
//		final String input = "PRINT \"h\";CALCULATE 8+9;";
		final SimpleAst ast1 = new SimpleAst();
		List<Token> tok = Arrays.asList(pr, new Token(KeyWord.StringLiteral, "h"), semi, cal, t, h, w);
		List<Token> exp = Arrays.asList(t, h, w);
		ast1.addNode(new PrintNode("h", ast1));
		ast1.addNode(new CalculateNode(expressionParser.parse(exp, null), null));
		final SimpleAst ast2 = simpleLanguageParser.parse(tok);
		assertEquals(ast1.getNodes().get(0), ast2.getNodes().get(0));
//		assertEquals(ast1.getNodes().get(1), ast2.getNodes().get(1));
	}

	@Test
	void testPrint() {
		final List<String> printMessages = new LinkedList<>();
//		final String input = "PRINT \"Hello\";PRINT \"World!\";";
		List<Token> tok = Arrays.asList(pr, new Token(KeyWord.StringLiteral, "Hello"), semi, pr,
				new Token(KeyWord.StringLiteral, "World!"));
		final SimpleAst ast = simpleLanguageParser.parse(tok);
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(2, printMessages.size());
		assertEquals("Hello", printMessages.get(0));
		assertEquals("World!", printMessages.get(1));
	}

	@Test
	void testInput() {
		final Deque<String> inputMessages = new LinkedList<>();
		inputMessages.add("Some Input");

		final String input = "INPUT;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.inputMessageProvider(() -> inputMessages.pop()).build();
		astInterpreter.execute(ast);
		assertEquals(0, inputMessages.size());
	}

	@Test
	void testPrinti1() {
		final Deque<String> inputMsg = new LinkedList<>();
		final List<String> printMsg = new LinkedList<>();
		inputMsg.add("Hello");

		final String input = "INPUT;PRINTI;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(msg -> printMsg.add(msg)).inputMessageProvider(() -> inputMsg.pop()).build();
		astInterpreter.execute(ast);
		assertEquals("Hello", printMsg.get(0));
		assertEquals(0, inputMsg.size());
	}

	@Test
	void testPrinti2() {
		final Deque<String> inputMsg = new LinkedList<>();
		final List<String> printMsg = new LinkedList<>();
		inputMsg.add("Ding");
		inputMsg.add("Dong");
		inputMsg.add("Dong");

		final String input = "INPUT;INPUT;PRINTI;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(msg -> printMsg.add(msg)).inputMessageProvider(() -> inputMsg.pop()).build();
		astInterpreter.execute(ast);
		assertEquals("Dong", printMsg.get(0));
		assertEquals(1, inputMsg.size());
	}

	@Test
	void testCalculateMult() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "CALCULATE 3*4;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(1, printMessages.size());
		assertEquals("12.0", printMessages.get(0));
	}

	@Test
	void testCalculateAddi() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "CALCULATE 6+11;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(1, printMessages.size());
		assertEquals("17.0", printMessages.get(0));
	}

	@Test
	void testCalculateSub() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "CALCULATE 12-11;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(1, printMessages.size());
		assertEquals("1.0", printMessages.get(0));
	}

	@Test
	void testCalculateDiv() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "CALCULATE 10/5;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(1, printMessages.size());
		assertEquals("2.0", printMessages.get(0));
	}

	@Test
	void testCalculateExp1() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "CALCULATE 2^2;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(1, printMessages.size());
		assertEquals("4.0", printMessages.get(0));
	}

	@Test
	void testCalculateExp2() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "CALCULATE 5+3^2;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(1, printMessages.size());
		assertEquals("14.0", printMessages.get(0));
	}

	@Test
	void testCalculateParen1() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "CALCULATE 2*(2+3);";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(1, printMessages.size());
		assertEquals("10.0", printMessages.get(0));
	}

	@Test
	void testCalculateParen2() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "CALCULATE ((2*(2+3)));";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(1, printMessages.size());
		assertEquals("10.0", printMessages.get(0));
	}

	@Test
	void testCalculateMixed1() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "CALCULATE 3+4*2/(1-5)^2^3;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(1, printMessages.size());
		assertEquals("3.001953125", printMessages.get(0));
	}

	@Test
	void testCalculateMixed2() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "CALCULATE (3*8/6)*0;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(1, printMessages.size());
		assertEquals("0.0", printMessages.get(0));
	}

	@Test
	void testCalculateNegative() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "CALCULATE 1-8;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(1, printMessages.size());
		assertEquals("-7.0", printMessages.get(0));
	}

	@Test
	void testVariable1() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "VARIABLE x = 7;CALCULATE x+1;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals("8.0", printMessages.get(1));
	}

	@Test
	void testVariable2() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "VARIABLE x = 7;VARIABLE z = 1;CALCULATE x+z;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals("8.0", printMessages.get(2));
	}

	@Test
	void testVariable3() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "VARIABLE x;VARIABLE z = 1;VARIABLE y = 2;VARIABLE x = CALCULATE y+z;PRINT (x);";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals("3.0", printMessages.get(3));
	}

	@Test
	void testExpressionParser() {
//		final String a = "2 * (3 + (4 * 5 + (6 * 7) * 8) - 9) * 10";
		List<Token> list = Arrays.asList(d, e, f, g, h, f, j, e, l, h, f, o, e, q, r, e, t, r, v, w, r, e, z);
		Node b = expressionParser.parse(list, null);
		final String c = "((2.0 * ((3.0 + ((4.0 * 5.0) + ((6.0 * 7.0) * 8.0))) - 9.0)) * 10.0)";
		assertEquals(c, b.toString());
	}

	@Test
	void testExpressionParserEvalPlus() {
//		final String a = "2+3";
		List<Token> list = Arrays.asList(d, h, g);
		Node b = expressionParser.parse(list, null);
		double c = expressionParser.evalTree((MathExpression) b);
		assertEquals(5, c);
	}

	@Test
	void testExpressionParserEvalMinus() {
//		final String a = "5-2";
		List<Token> list = Arrays.asList(l, v, d);
		Node b = expressionParser.parse(list, null);
		double c = expressionParser.evalTree((MathExpression) b);
		assertEquals(3, c);
	}

	@Test
	void testExpressionParserEvalMult() {
//		final String a = "2*3";
		List<Token> list = Arrays.asList(d, e, g);
		Node b = expressionParser.parse(list, null);
		double c = expressionParser.evalTree((MathExpression) b);
		assertEquals(6, c);
	}

	@Test
	void testExpressionParserEvalDiv() {
//		final String a = "6/2";
		List<Token> list = Arrays.asList(o, di, d);
		Node b = expressionParser.parse(list, null);
		double c = expressionParser.evalTree((MathExpression) b);
		assertEquals(3, c);
	}

	@Test
	void testExpressionParserEvalExpo() {
//		final String a = "2^2";
		List<Token> list = Arrays.asList(d, ex, d);
		Node b = expressionParser.parse(list, null);
		double c = expressionParser.evalTree((MathExpression) b);
		assertEquals(4, c);
	}

	@Test
	void testExpressionParserEvalMixed() {
//		final String a = "2 * (3 + (4 * 5 + (6 * 7) * 8) - 9) * 10";
		List<Token> list = Arrays.asList(d, e, f, g, h, f, j, e, l, h, f, o, e, q, r, e, t, r, v, w, r, e, z);
		Node b = expressionParser.parse(list, null);
		double c = expressionParser.evalTree((MathExpression) b);
		assertEquals(7000, c);
	}

	@Test
	void testTokenizerStPrint() {
		final String a = "PRINT \"abc\";";
		List<Token> tokens = tokenizerSt.tokenize(a);
		Token g = new Token(KeyWord.StringLiteral, "abc");
		assertEquals(pr, tokens.get(0));
		assertEquals(g, tokens.get(1));
		assertEquals(semi, tokens.get(2));
	}

	@Test
	void testTokenizerStPrinti() {
		final String a = "PRINTI";
		List<Token> tokens = tokenizerSt.tokenize(a);
		assertEquals(pri, tokens.get(0));
		assertEquals(semi, tokens.get(1));
	}

	@Test
	void testTokenizerStInput() {
		final String a = "INPUT";
		List<Token> tokens = tokenizerSt.tokenize(a);
		assertEquals(in, tokens.get(0));
		assertEquals(semi, tokens.get(1));
	}

	@Test
	void testTokenizerStVar() {
		final String a = "VARIABLE x = 1;";
		List<Token> tokens = tokenizerSt.tokenize(a);
		Token g = new Token(KeyWord.Variable, "x");
		Token h = new Token(KeyWord.Equals, "=");
		assertEquals(var, tokens.get(0));
		assertEquals(g, tokens.get(1));
		assertEquals(h, tokens.get(2));
		assertEquals(ii, tokens.get(3));
		assertEquals(semi, tokens.get(4));
	}

	@Test
	void testTokenizerStCalc1() {
		final String a = "CALCULATE 1+2;";
		List<Token> tokens = tokenizerSt.tokenize(a);
		assertEquals(cal, tokens.get(0));
		assertEquals(ii, tokens.get(1));
		assertEquals(h, tokens.get(2));
		assertEquals(d, tokens.get(3));
		assertEquals(semi, tokens.get(4));
	}

	@Test
	void testTokenizerStCalc1a() {
		final String a = "CALCULATE 11+223;";
		List<Token> tokens = tokenizerSt.tokenize(a);
		Token i = new Token(KeyWord.Integer, "11");
		Token z = new Token(KeyWord.Integer, "223");
		assertEquals(cal, tokens.get(0));
		assertEquals(i, tokens.get(1));
		assertEquals(h, tokens.get(2));
		assertEquals(z, tokens.get(3));
		assertEquals(semi, tokens.get(4));
	}

	@Test
	void testTokenizerStCalc2() {
		final String a = "CALCULATE 3*(9+2)/4^2-1;";
		List<Token> tokens = tokenizerSt.tokenize(a);
		Token l = new Token(KeyWord.Integer, "1");
		assertEquals(cal, tokens.get(0));
		assertEquals(g, tokens.get(1));
		assertEquals(e, tokens.get(2));
		assertEquals(f, tokens.get(3));
		assertEquals(w, tokens.get(4));
		assertEquals(h, tokens.get(5));
		assertEquals(d, tokens.get(6));
		assertEquals(r, tokens.get(7));
		assertEquals(di, tokens.get(8));
		assertEquals(j, tokens.get(9));
		assertEquals(ex, tokens.get(10));
		assertEquals(d, tokens.get(11));
		assertEquals(v, tokens.get(12));
		assertEquals(l, tokens.get(13));
		assertEquals(semi, tokens.get(14));
	}

	@Test
	void testTokenizerStVarCalc() {
		final String a = "VARIABLE x = CALCULATE 1+2;";
		List<Token> tokens = tokenizerSt.tokenize(a);
		Token c = new Token(KeyWord.Variable, "x");
		Token eq = new Token(KeyWord.Equals, "=");
		assertEquals(var, tokens.get(0));
		assertEquals(c, tokens.get(1));
		assertEquals(eq, tokens.get(2));
		assertEquals(cal, tokens.get(3));
		assertEquals(ii, tokens.get(4));
		assertEquals(h, tokens.get(5));
		assertEquals(d, tokens.get(6));
		assertEquals(semi, tokens.get(7));
	}

	@Test
	void testTokenizerStVarX() {
		final String a = "VARIABLE x;";
		List<Token> tokens = tokenizerSt.tokenize(a);
		Token c = new Token(KeyWord.Variable, "x");
		assertEquals(var, tokens.get(0));
		assertEquals(c, tokens.get(1));
		assertEquals(semi, tokens.get(2));
	}

	@Test
	void testTokenizerStPrIn() {
		final String a = "PRINT \"abc\";INPUT";
		List<Token> tokens = tokenizerSt.tokenize(a);
		Token c = new Token(KeyWord.StringLiteral, "abc");
		assertEquals(pr, tokens.get(0));
		assertEquals(c, tokens.get(1));
		assertEquals(semi, tokens.get(2));
		assertEquals(in, tokens.get(3));
	}
}
