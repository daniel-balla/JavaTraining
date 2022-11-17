package exercise6.Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import exercise6.MatchExpressions.MathExpression;
import exercise6.Parser.ExpressionParser;
import exercise6.Tokenizer.KeyWord;
import exercise6.Tokenizer.Token;
import exercise6.ast.Node;

class ExpressionParserTests {

	private final ExpressionParser expressionParser = new ExpressionParser();
	private final Token two = new Token(KeyWord.Integer, "2");
	private final Token three = new Token(KeyWord.Integer, "3");
	private final Token four = new Token(KeyWord.Integer, "4");
	private final Token five = new Token(KeyWord.Integer, "5");
	private final Token six = new Token(KeyWord.Integer, "6");
	private final Token seven = new Token(KeyWord.Integer, "7");
	private final Token eight = new Token(KeyWord.Integer, "8");
	private final Token nine = new Token(KeyWord.Integer, "9");
	private final Token ten = new Token(KeyWord.Integer, "10");
	private final Token mult = new Token(KeyWord.Multiplication, "*");
	private final Token leftP = new Token(KeyWord.LeftParenthesis, "(");
	private final Token plus = new Token(KeyWord.Plus, "+");
	private final Token rightP = new Token(KeyWord.RightParenthesis, ")");
	private final Token minus = new Token(KeyWord.Minus, "-");
	private final Token div = new Token(KeyWord.Divide, "/");
	private final Token exp = new Token(KeyWord.Exponent, "^");

	@Test
	void testExpressionParser() {
//		final String a = "2 * (3 + (4 * 5 + (6 * 7) * 8) - 9) * 10";
		List<Token> list = Arrays.asList(two, mult, leftP, three, plus, leftP, four, mult, five, plus, leftP, six, mult,
				seven, rightP, mult, eight, rightP, minus, nine, rightP, mult, ten);
		Node b = expressionParser.parse(list, null);
		final String c = "((2.0 * ((3.0 + ((4.0 * 5.0) + ((6.0 * 7.0) * 8.0))) - 9.0)) * 10.0)";
		assertEquals(c, b.toString());
	}

	@Test
	void testExpressionParserEvalPlus() {
//		final String a = "2+3";
		List<Token> list = Arrays.asList(two, plus, three);
		Node b = expressionParser.parse(list, null);
		double c = expressionParser.evalTree((MathExpression) b);
		assertEquals(5, c);
	}

	@Test
	void testExpressionParserEvalMinus() {
//		final String a = "5-2";
		List<Token> list = Arrays.asList(five, minus, two);
		Node b = expressionParser.parse(list, null);
		double c = expressionParser.evalTree((MathExpression) b);
		assertEquals(3, c);
	}

	@Test
	void testExpressionParserEvalMult() {
//		final String a = "2*3";
		List<Token> list = Arrays.asList(two, mult, three);
		Node b = expressionParser.parse(list, null);
		double c = expressionParser.evalTree((MathExpression) b);
		assertEquals(6, c);
	}

	@Test
	void testExpressionParserEvalDiv() {
//		final String a = "6/2";
		List<Token> list = Arrays.asList(six, div, two);
		Node b = expressionParser.parse(list, null);
		double c = expressionParser.evalTree((MathExpression) b);
		assertEquals(3, c);
	}

	@Test
	void testExpressionParserEvalExpo() {
//		final String a = "2^2";
		List<Token> list = Arrays.asList(two, exp, two);
		Node b = expressionParser.parse(list, null);
		double c = expressionParser.evalTree((MathExpression) b);
		assertEquals(4, c);
	}

	@Test
	void testExpressionParserEvalMixed() {
//		final String a = "2 * (3 + (4 * 5 + (6 * 7) * 8) - 9) * 10";
		List<Token> list = Arrays.asList(two, mult, leftP, three, plus, leftP, four, mult, five, plus, leftP, six, mult,
				seven, rightP, mult, eight, rightP, minus, nine, rightP, mult, ten);
		Node b = expressionParser.parse(list, null);
		double c = expressionParser.evalTree((MathExpression) b);
		assertEquals(7000, c);
	}

}
