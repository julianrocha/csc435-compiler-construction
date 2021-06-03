package ast.visitors;

import ast.type.*;
import ast.*;
import ast.expression.AddExpression;
import ast.expression.EqualityExpression;
import ast.expression.IntegerLiteral;
import ast.expression.LessThanExpression;
import ast.expression.MultExpression;
import ast.expression.ParenExpression;
import ast.expression.SubtractExpression;
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

    Object visit(ExpressionStatement expressionStatement);

    Object visit(IfElseStatement ifElseStatement);

    Object visit(EqualityExpression equalityExpression);

    Object visit(LessThanExpression lessThanExpression);

    Object visit(SubtractExpression subtractExpression);

    Object visit(MultExpression multExpression);

    Object visit(ParenExpression parenExpression);

    Object visit(AddExpression addExpression);

    Object visit(IntegerLiteral integerLiteral);
}