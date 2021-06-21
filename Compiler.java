/*
 * Compiler.java
 *
 * Compiler for the unnamed language for CSC 435
 *
 */

import org.antlr.runtime.*;
import java.io.*;
import ast.*;
import ast.visitors.*;

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
			// PrettyPrintVisitor visitor = new PrettyPrintVisitor(); // a1
			TypeCheckVisitor visitor = new TypeCheckVisitor(); // a2
			tree.accept(visitor);
		} catch (RecognitionException e) {
			// A lexical or parsing error occured.
			// ANTLR will have already printed information on the
			// console due to code added to the grammar. So there is
			// nothing to do here.
		} catch (SemanticException e) {
			// Semantic Exeption thrown during typecheck traversal of AST
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
