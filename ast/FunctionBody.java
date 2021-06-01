package ast;

public class FunctionBody extends ASTNode {
    @Override
    public Object accept(Visitor v){
        return v.visit(this);
    }
}
