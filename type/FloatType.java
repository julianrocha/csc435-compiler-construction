package type;

import ast.*;

public class FloatType extends Type {

    public FloatType(int l, int o){
        super(l,o);
    }

    public String toString(){
        return "float";
    }

}