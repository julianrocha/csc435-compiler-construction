// $ANTLR 3.5.2 ulNoActions.g 2021-05-11 14:42:59

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class ulNoActionsParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "COMMENT", "ID", "IF", "TYPE", 
		"WS", "'('", "')'", "'{'", "'}'"
	};
	public static final int EOF=-1;
	public static final int T__9=9;
	public static final int T__10=10;
	public static final int T__11=11;
	public static final int T__12=12;
	public static final int COMMENT=4;
	public static final int ID=5;
	public static final int IF=6;
	public static final int TYPE=7;
	public static final int WS=8;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public ulNoActionsParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public ulNoActionsParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return ulNoActionsParser.tokenNames; }
	@Override public String getGrammarFileName() { return "ulNoActions.g"; }


	protected void mismatch (IntStream input, int ttype, BitSet follow)
	        throws RecognitionException
	{
	        throw new MismatchedTokenException(ttype, input);
	}
	public Object recoverFromMismatchedSet (IntStream input,
	                                      RecognitionException e,
	                                      BitSet follow)
	        throws RecognitionException
	{
	        reportError(e);
	        throw e;
	}



	// $ANTLR start "program"
	// ulNoActions.g:36:1: program : ( function )+ EOF ;
	public final void program() throws RecognitionException {
		try {
			// ulNoActions.g:36:9: ( ( function )+ EOF )
			// ulNoActions.g:36:11: ( function )+ EOF
			{
			// ulNoActions.g:36:11: ( function )+
			int cnt1=0;
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==TYPE) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// ulNoActions.g:36:11: function
					{
					pushFollow(FOLLOW_function_in_program29);
					function();
					state._fsp--;

					}
					break;

				default :
					if ( cnt1 >= 1 ) break loop1;
					EarlyExitException eee = new EarlyExitException(1, input);
					throw eee;
				}
				cnt1++;
			}

			match(input,EOF,FOLLOW_EOF_in_program32); 
			}

		}

		        catch (RecognitionException ex) {
		                reportError(ex);
		                throw ex;
		        }

		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "program"



	// $ANTLR start "function"
	// ulNoActions.g:39:1: function : functionDecl functionBody ;
	public final void function() throws RecognitionException {
		try {
			// ulNoActions.g:39:9: ( functionDecl functionBody )
			// ulNoActions.g:39:11: functionDecl functionBody
			{
			pushFollow(FOLLOW_functionDecl_in_function41);
			functionDecl();
			state._fsp--;

			pushFollow(FOLLOW_functionBody_in_function43);
			functionBody();
			state._fsp--;

			}

		}

		        catch (RecognitionException ex) {
		                reportError(ex);
		                throw ex;
		        }

		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "function"



	// $ANTLR start "functionDecl"
	// ulNoActions.g:42:1: functionDecl : type identifier '(' ')' ;
	public final void functionDecl() throws RecognitionException {
		try {
			// ulNoActions.g:42:13: ( type identifier '(' ')' )
			// ulNoActions.g:42:15: type identifier '(' ')'
			{
			pushFollow(FOLLOW_type_in_functionDecl52);
			type();
			state._fsp--;

			pushFollow(FOLLOW_identifier_in_functionDecl54);
			identifier();
			state._fsp--;

			match(input,9,FOLLOW_9_in_functionDecl56); 
			match(input,10,FOLLOW_10_in_functionDecl58); 
			}

		}

		        catch (RecognitionException ex) {
		                reportError(ex);
		                throw ex;
		        }

		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "functionDecl"



	// $ANTLR start "functionBody"
	// ulNoActions.g:45:1: functionBody : '{' '}' ;
	public final void functionBody() throws RecognitionException {
		try {
			// ulNoActions.g:45:13: ( '{' '}' )
			// ulNoActions.g:45:15: '{' '}'
			{
			match(input,11,FOLLOW_11_in_functionBody67); 
			match(input,12,FOLLOW_12_in_functionBody69); 
			}

		}

		        catch (RecognitionException ex) {
		                reportError(ex);
		                throw ex;
		        }

		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "functionBody"



	// $ANTLR start "identifier"
	// ulNoActions.g:48:1: identifier : ID ;
	public final void identifier() throws RecognitionException {
		try {
			// ulNoActions.g:48:12: ( ID )
			// ulNoActions.g:48:14: ID
			{
			match(input,ID,FOLLOW_ID_in_identifier79); 
			}

		}

		        catch (RecognitionException ex) {
		                reportError(ex);
		                throw ex;
		        }

		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "identifier"



	// $ANTLR start "type"
	// ulNoActions.g:51:1: type : TYPE ;
	public final void type() throws RecognitionException {
		try {
			// ulNoActions.g:51:5: ( TYPE )
			// ulNoActions.g:51:7: TYPE
			{
			match(input,TYPE,FOLLOW_TYPE_in_type88); 
			}

		}

		        catch (RecognitionException ex) {
		                reportError(ex);
		                throw ex;
		        }

		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "type"

	// Delegated rules



	public static final BitSet FOLLOW_function_in_program29 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_EOF_in_program32 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_functionDecl_in_function41 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_functionBody_in_function43 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_type_in_functionDecl52 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_identifier_in_functionDecl54 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_9_in_functionDecl56 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_10_in_functionDecl58 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_11_in_functionBody67 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_12_in_functionBody69 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_identifier79 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_TYPE_in_type88 = new BitSet(new long[]{0x0000000000000002L});
}
