package ast.visitors;

import ast.type.*;
import ast.*;
import ast.statement.*;

public interface Visitor {

    Object visit(Function function);

    Object visit(Program program);

    Object visit(FunctionBody functionBody);

    Object visit(FunctionDeclaration functionDeclaration);

    Object visit(FormalParameter formalParameter);

    Object visit(Identifier identifier);

    Object visit(VariableDeclaration variableDeclaration);

    Object visit(ArrayType type);

    Object visit(CharType type);

    Object visit(FloatType type);

    Object visit(IntegerType type);

    Object visit(StringType type);

    Object visit(VoidType type);

    Object visit(BooleanType booleanType);

}