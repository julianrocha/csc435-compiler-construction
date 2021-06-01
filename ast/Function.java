package ast;

public class Function extends ASTNode {

    FunctionDeclaration funcDecl;
    FunctionBody funcBody;

    public Function(FunctionDeclaration funcDecl, FunctionBody funcBody){
        this.funcDecl = funcDecl;
        this.funcBody = funcBody;
    }

    @Override
    public Object accept(Visitor v){
        return v.visit(this);
    }
}
