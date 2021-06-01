package ast;

import type.*;

public class FormalParameter extends ASTNode{

    Type type;
    Identifier id;

    public FormalParameter(Type type, Identifier id){
        this.type = type;
        this.id = id;
    }

    @Override
    public Object accept(Visitor v){
        return v.visit(this);
    }
}
