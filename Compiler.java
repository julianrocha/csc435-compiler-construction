/*
 * Compiler.java
 *
 * Compiler for the unnamed language for CSC 435
 *
 */

import org.antlr.runtime.*;
import java.io.*;
import ast.*;
import ast.visitors.PrettyPrintVisitor;

public class Compiler {
	public static void main(String[] args) throws Exception {
		ANTLRInputStream input;

		if (args.length == 0) {
			System.out.println("Usage: Compiler filename.ul");
			return;
		} else {
			input = new ANTLRInputStream(new FileInputStream(args[0]));
		}

		ulGrammarLexer lexer = new ulGrammarLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ulGrammarParser parser = new ulGrammarParser(tokens);

		try {
			Program tree = parser.program(); // Invoke 'program' as this is the start production rule
			PrettyPrintVisitor prettyPrintVisitor = new PrettyPrintVisitor();
			tree.accept(prettyPrintVisitor);
		} catch (RecognitionException e) {
			// A lexical or parsing error occured.
			// ANTLR will have already printed information on the
			// console due to code added to the grammar. So there is
			// nothing to do here.
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
