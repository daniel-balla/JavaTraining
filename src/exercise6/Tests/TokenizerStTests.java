/* Copyright (c) 2021 Deloitte. All rights reserved. */
package exercise6.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import exercise6.Tokenizer.KeyWord;
import exercise6.Tokenizer.Token;
import exercise6.Tokenizer.TokenizerSt;

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
class TokenizerStTests {

	private final TokenizerSt tokenizerSt = new TokenizerSt();
	private final Token one = new Token(KeyWord.Integer, "1");
	private final Token two = new Token(KeyWord.Integer, "2");
	private final Token three = new Token(KeyWord.Integer, "3");
	private final Token four = new Token(KeyWord.Integer, "4");
	private final Token nine = new Token(KeyWord.Integer, "9");
	private final Token mult = new Token(KeyWord.Multiplication, "*");
	private final Token leftP = new Token(KeyWord.LeftParenthesis, "(");
	private final Token plus = new Token(KeyWord.Plus, "+");
	private final Token rightP = new Token(KeyWord.RightParenthesis, ")");
	private final Token minus = new Token(KeyWord.Minus, "-");
	private final Token div = new Token(KeyWord.Divide, "/");
	private final Token exp = new Token(KeyWord.Exponent, "^");
	private final Token print = new Token(KeyWord.Command, "PRINT");
	private final Token printi = new Token(KeyWord.Command, "PRINTI");
	private final Token input = new Token(KeyWord.Command, "INPUT");
	private final Token calc = new Token(KeyWord.Command, "CALCULATE");
	private final Token var = new Token(KeyWord.Command, "VARIABLE");
	private final Token semi = new Token(KeyWord.SemiColon, ";");

	@Test
	void testTokenizerStPrint() {
		final String a = "PRINT \"abc\";";
		List<Token> tokens = tokenizerSt.tokenize(a);
		Token g = new Token(KeyWord.StringLiteral, "abc");
		assertEquals(print, tokens.get(0));
		assertEquals(g, tokens.get(1));
		assertEquals(semi, tokens.get(2));
	}

	@Test
	void testTokenizerStPrinti() {
		final String a = "PRINTI";
		List<Token> tokens = tokenizerSt.tokenize(a);
		assertEquals(printi, tokens.get(0));
		assertEquals(semi, tokens.get(1));
	}

	@Test
	void testTokenizerStInput() {
		final String a = "INPUT";
		List<Token> tokens = tokenizerSt.tokenize(a);
		assertEquals(input, tokens.get(0));
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
		assertEquals(one, tokens.get(3));
		assertEquals(semi, tokens.get(4));
	}

	@Test
	void testTokenizerStCalc1() {
		final String a = "CALCULATE 1+2;";
		List<Token> tokens = tokenizerSt.tokenize(a);
		assertEquals(calc, tokens.get(0));
		assertEquals(one, tokens.get(1));
		assertEquals(plus, tokens.get(2));
		assertEquals(two, tokens.get(3));
		assertEquals(semi, tokens.get(4));
	}

	@Test
	void testTokenizerStCalc1a() {
		final String a = "CALCULATE 11+223;";
		List<Token> tokens = tokenizerSt.tokenize(a);
		Token i = new Token(KeyWord.Integer, "11");
		Token z = new Token(KeyWord.Integer, "223");
		assertEquals(calc, tokens.get(0));
		assertEquals(i, tokens.get(1));
		assertEquals(plus, tokens.get(2));
		assertEquals(z, tokens.get(3));
		assertEquals(semi, tokens.get(4));
	}

	@Test
	void testTokenizerStCalc2() {
		final String a = "CALCULATE 3*(9+2)/4^2-1;";
		List<Token> tokens = tokenizerSt.tokenize(a);
		assertEquals(calc, tokens.get(0));
		assertEquals(three, tokens.get(1));
		assertEquals(mult, tokens.get(2));
		assertEquals(leftP, tokens.get(3));
		assertEquals(nine, tokens.get(4));
		assertEquals(plus, tokens.get(5));
		assertEquals(two, tokens.get(6));
		assertEquals(rightP, tokens.get(7));
		assertEquals(div, tokens.get(8));
		assertEquals(four, tokens.get(9));
		assertEquals(exp, tokens.get(10));
		assertEquals(two, tokens.get(11));
		assertEquals(minus, tokens.get(12));
		assertEquals(one, tokens.get(13));
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
		assertEquals(calc, tokens.get(3));
		assertEquals(one, tokens.get(4));
		assertEquals(plus, tokens.get(5));
		assertEquals(two, tokens.get(6));
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
		assertEquals(print, tokens.get(0));
		assertEquals(c, tokens.get(1));
		assertEquals(semi, tokens.get(2));
		assertEquals(input, tokens.get(3));
	}
}
