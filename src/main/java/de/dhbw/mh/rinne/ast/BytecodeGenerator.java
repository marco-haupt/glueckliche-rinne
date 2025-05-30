package de.dhbw.mh.rinne.ast;

public class BytecodeGenerator extends AstVisitor<String> {

    public void enterNode() {
    }

    public void exitNode() {
    }

    @Override
    public String visitProgram(AstProgramNode node) {
        enterNode();
        String bytecode = "";
        for (AstNode child : node.getChildren()) {
            if (child == null) {
                continue;
            }
            bytecode += child.accept(this);
        }
        exitNode();
        return bytecode;
    }

    @Override
    public String visitVariableDeclarationStmt(AstVariableDeclarationStmtNode node) {
        enterNode();
        String bytecode = "";
        if (node.getInitializer() != null) {
            bytecode += node.getInitializer().accept(this);
            bytecode += "istore <" + node.getName() + ">\n";
        }
        exitNode();
        return bytecode;
    }

    @Override
    public String visitVariableReference(AstVariableReferenceNode node) {
        enterNode();
        String bytecode = "iload <" + node.getName() + ">\n";
        exitNode();
        return bytecode;
    }

}
