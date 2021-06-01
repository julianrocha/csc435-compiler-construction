package ast;

import java.util.List;

public class Program extends ASTNode {
  
    public List<Function> funcList;

    public Program(List<Function> funcList){
        this.funcList = funcList;        
    }

    @Override
    public Object accept(Visitor v){
        return v.visit(this);
    }
}
