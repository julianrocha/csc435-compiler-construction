package type;

import ast.*;

public class StringType extends Type {

    public StringType(int l, int o){
        super(l,o);
    }

    public String toString(){
        return "string";
    }

}