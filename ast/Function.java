package ast;

import ast.visitors.Visitor;

public class Function extends ASTNode {

    public FunctionDeclaration funcDecl;
    public FunctionBody funcBody;

    public Function(FunctionDeclaration funcDecl, FunctionBody funcBody) {
        this.funcDecl = funcDecl;
        this.funcBody = funcBody;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
