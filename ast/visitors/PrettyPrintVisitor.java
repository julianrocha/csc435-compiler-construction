package ast.visitors;

import java.util.List;

import ast.*;
import ast.statement.*;
import ast.type.*;

public class PrettyPrintVisitor implements Visitor {

    private int indented_blocks; // number of blocks (4 spaces each) currently indented
    private boolean cursor_new_line; // true when cursor on a fresh line
    private final int tab_size = 4; // spaces in a tab

    public PrettyPrintVisitor() {
        indented_blocks = 0;
        cursor_new_line = true;
    }

    private void print_line(String str) {
        print(str);
        new_line();
    }

    private void new_line() {
        System.out.println();
        cursor_new_line = true;
    }

    private void print(String str) {
        if (cursor_new_line) {
            for (int i = 0; i < tab_size * indented_blocks; i++) {
                System.out.print(" ");
            }
            cursor_new_line = false;
        }
        System.out.print(str);
    }

    @Override
    public Object visit(Program program) {
        for (Function f : program.funcList) {
            visit(f);
            new_line();
        }
        return null;
    }

    @Override
    public Object visit(Function function) {
        visit(function.funcDecl);
        print_line("{");
        indented_blocks++;
        visit(function.funcBody);
        indented_blocks--;
        print_line("}");
        return null;
    }

    @Override
    public Object visit(FunctionBody functionBody) {
        for (VariableDeclaration d : functionBody.vlist) {
            visit(d);
        }
        for (Statement s : functionBody.slist) {
            s.accept(this);
        }
        return null;
    }

    @Override
    public Object visit(FunctionDeclaration functionDeclaration) {
        functionDeclaration.type.accept(this);
        print(" ");
        visit(functionDeclaration.id);
        print("(");

        List<FormalParameter> fpl = functionDeclaration.formalParameterList;
        if (fpl.size() >= 1) {
            visit(fpl.get(0));
        }
        for (FormalParameter fp : fpl) {
            if (fp == fpl.get(0))
                continue;
            print(", ");
            visit(fp);
        }

        print_line(")");
        return null;
    }

    @Override
    public Object visit(FormalParameter formalParameter) {
        formalParameter.type.accept(this);
        print(" ");
        visit(formalParameter.id);
        return null;
    }

    @Override
    public Object visit(Identifier identifier) {
        print(identifier.id_string);
        return null;
    }

    @Override
    public Object visit(VariableDeclaration variableDeclaration) {
        variableDeclaration.type.accept(this);
        print(" ");
        visit(variableDeclaration.id);
        print_line(";");
        return null;
    }

    @Override
    public Object visit(ArrayType type) {
        print(type.element_type.toString() + "[" + Integer.toString(type.array_size) + "]");
        return null;
    }

    @Override
    public Object visit(CharType type) {
        print("char");
        return null;
    }

    @Override
    public Object visit(FloatType type) {
        print("float");
        return null;
    }

    @Override
    public Object visit(IntegerType type) {
        print("int");
        return null;
    }

    @Override
    public Object visit(StringType type) {
        print("string");
        return null;
    }

    @Override
    public Object visit(VoidType type) {
        print("void");
        return null;
    }

    @Override
    public Object visit(BooleanType booleanType) {
        print("boolean");
        return null;
    }

}