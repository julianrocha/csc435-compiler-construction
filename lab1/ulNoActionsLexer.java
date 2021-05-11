// $ANTLR 3.5.2 ulNoActions.g 2021-05-11 14:42:59

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class ulNoActionsLexer extends Lexer {
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
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public ulNoActionsLexer() {} 
	public ulNoActionsLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public ulNoActionsLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "ulNoActions.g"; }

	// $ANTLR start "T__9"
	public final void mT__9() throws RecognitionException {
		try {
			int _type = T__9;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulNoActions.g:2:6: ( '(' )
			// ulNoActions.g:2:8: '('
			{
			match('('); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__9"

	// $ANTLR start "T__10"
	public final void mT__10() throws RecognitionException {
		try {
			int _type = T__10;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulNoActions.g:3:7: ( ')' )
			// ulNoActions.g:3:9: ')'
			{
			match(')'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__10"

	// $ANTLR start "T__11"
	public final void mT__11() throws RecognitionException {
		try {
			int _type = T__11;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulNoActions.g:4:7: ( '{' )
			// ulNoActions.g:4:9: '{'
			{
			match('{'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__11"

	// $ANTLR start "T__12"
	public final void mT__12() throws RecognitionException {
		try {
			int _type = T__12;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulNoActions.g:5:7: ( '}' )
			// ulNoActions.g:5:9: '}'
			{
			match('}'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__12"

	// $ANTLR start "IF"
	public final void mIF() throws RecognitionException {
		try {
			int _type = IF;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulNoActions.g:56:4: ( 'if' )
			// ulNoActions.g:56:6: 'if'
			{
			match("if"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "IF"

	// $ANTLR start "TYPE"
	public final void mTYPE() throws RecognitionException {
		try {
			int _type = TYPE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulNoActions.g:60:6: ( 'int' )
			// ulNoActions.g:60:8: 'int'
			{
			match("int"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "TYPE"

	// $ANTLR start "ID"
	public final void mID() throws RecognitionException {
		try {
			int _type = ID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulNoActions.g:67:4: ( ( 'a' .. 'z' )+ )
			// ulNoActions.g:67:6: ( 'a' .. 'z' )+
			{
			// ulNoActions.g:67:6: ( 'a' .. 'z' )+
			int cnt1=0;
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( ((LA1_0 >= 'a' && LA1_0 <= 'z')) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// ulNoActions.g:
					{
					if ( (input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt1 >= 1 ) break loop1;
					EarlyExitException eee = new EarlyExitException(1, input);
					throw eee;
				}
				cnt1++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ID"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulNoActions.g:75:9: ( ( '\\t' | ' ' | ( '\\r' | '\\n' ) )+ )
			// ulNoActions.g:75:11: ( '\\t' | ' ' | ( '\\r' | '\\n' ) )+
			{
			// ulNoActions.g:75:11: ( '\\t' | ' ' | ( '\\r' | '\\n' ) )+
			int cnt2=0;
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( ((LA2_0 >= '\t' && LA2_0 <= '\n')||LA2_0=='\r'||LA2_0==' ') ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// ulNoActions.g:
					{
					if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt2 >= 1 ) break loop2;
					EarlyExitException eee = new EarlyExitException(2, input);
					throw eee;
				}
				cnt2++;
			}

			 _channel = HIDDEN;
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	// $ANTLR start "COMMENT"
	public final void mCOMMENT() throws RecognitionException {
		try {
			int _type = COMMENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ulNoActions.g:78:9: ( '//' (~ ( '\\r' | '\\n' ) )* ( '\\r' | '\\n' ) )
			// ulNoActions.g:78:11: '//' (~ ( '\\r' | '\\n' ) )* ( '\\r' | '\\n' )
			{
			match("//"); 

			// ulNoActions.g:78:16: (~ ( '\\r' | '\\n' ) )*
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( ((LA3_0 >= '\u0000' && LA3_0 <= '\t')||(LA3_0 >= '\u000B' && LA3_0 <= '\f')||(LA3_0 >= '\u000E' && LA3_0 <= '\uFFFF')) ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// ulNoActions.g:
					{
					if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\uFFFF') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop3;
				}
			}

			if ( input.LA(1)=='\n'||input.LA(1)=='\r' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			 _channel = HIDDEN;
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "COMMENT"

	@Override
	public void mTokens() throws RecognitionException {
		// ulNoActions.g:1:8: ( T__9 | T__10 | T__11 | T__12 | IF | TYPE | ID | WS | COMMENT )
		int alt4=9;
		switch ( input.LA(1) ) {
		case '(':
			{
			alt4=1;
			}
			break;
		case ')':
			{
			alt4=2;
			}
			break;
		case '{':
			{
			alt4=3;
			}
			break;
		case '}':
			{
			alt4=4;
			}
			break;
		case 'i':
			{
			switch ( input.LA(2) ) {
			case 'f':
				{
				int LA4_9 = input.LA(3);
				if ( ((LA4_9 >= 'a' && LA4_9 <= 'z')) ) {
					alt4=7;
				}

				else {
					alt4=5;
				}

				}
				break;
			case 'n':
				{
				int LA4_10 = input.LA(3);
				if ( (LA4_10=='t') ) {
					int LA4_12 = input.LA(4);
					if ( ((LA4_12 >= 'a' && LA4_12 <= 'z')) ) {
						alt4=7;
					}

					else {
						alt4=6;
					}

				}

				else {
					alt4=7;
				}

				}
				break;
			default:
				alt4=7;
			}
			}
			break;
		case 'a':
		case 'b':
		case 'c':
		case 'd':
		case 'e':
		case 'f':
		case 'g':
		case 'h':
		case 'j':
		case 'k':
		case 'l':
		case 'm':
		case 'n':
		case 'o':
		case 'p':
		case 'q':
		case 'r':
		case 's':
		case 't':
		case 'u':
		case 'v':
		case 'w':
		case 'x':
		case 'y':
		case 'z':
			{
			alt4=7;
			}
			break;
		case '\t':
		case '\n':
		case '\r':
		case ' ':
			{
			alt4=8;
			}
			break;
		case '/':
			{
			alt4=9;
			}
			break;
		default:
			NoViableAltException nvae =
				new NoViableAltException("", 4, 0, input);
			throw nvae;
		}
		switch (alt4) {
			case 1 :
				// ulNoActions.g:1:10: T__9
				{
				mT__9(); 

				}
				break;
			case 2 :
				// ulNoActions.g:1:15: T__10
				{
				mT__10(); 

				}
				break;
			case 3 :
				// ulNoActions.g:1:21: T__11
				{
				mT__11(); 

				}
				break;
			case 4 :
				// ulNoActions.g:1:27: T__12
				{
				mT__12(); 

				}
				break;
			case 5 :
				// ulNoActions.g:1:33: IF
				{
				mIF(); 

				}
				break;
			case 6 :
				// ulNoActions.g:1:36: TYPE
				{
				mTYPE(); 

				}
				break;
			case 7 :
				// ulNoActions.g:1:41: ID
				{
				mID(); 

				}
				break;
			case 8 :
				// ulNoActions.g:1:44: WS
				{
				mWS(); 

				}
				break;
			case 9 :
				// ulNoActions.g:1:47: COMMENT
				{
				mCOMMENT(); 

				}
				break;

		}
	}



}
