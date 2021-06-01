package ast;

import type.*;

public interface Visitor {

    Object visit(Function function);
    Object visit(Program program);
	Object visit(FunctionBody functionBody);
	Object visit(FunctionDeclaration functionDeclaration);
    Object visit(Type type);
    Object visit(FormalParameter formalParameter);
    Object visit(Identifier identifier);
}