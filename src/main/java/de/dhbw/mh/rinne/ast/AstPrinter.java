package de.dhbw.mh.rinne.ast;

public class AstPrinter extends AstVisitor<String> {

    private static final String INDENTATION_PER_LEVEL = " ";

    private int level = 0;

    private String indentationFor(int level) {
        return level <= 1 ? "" : INDENTATION_PER_LEVEL + indentationFor(level - 1);
    }

    public void enterNode() {
        ++level;
    }

    public void exitNode() {
        --level;
    }

    @Override
    String visitChildren(AstNode node) {
        StringBuilder builder = new StringBuilder();
        for (AstNode child : node.getChildren()) {
            builder.append(child.accept(this));
        }
        return builder.toString();
    }

    @Override
    String visitProgram(AstProgramNode node) {
        enterNode();
        String temp = "Program(" + node.locationAsString() + ")\n" + visitChildren(node);
        exitNode();
        return temp;
    }

    @Override
    String visitVariableDeclarationStmt(AstVariableDeclarationStmtNode node) {
        enterNode();
        String temp = indentationFor(level) + "VarDecl(" + node.locationAsString() + ")\n" + visitChildren(node);
        exitNode();
        return temp;
    }

    @Override
    String visitVariableReference(AstVariableReferenceNode node) {
        enterNode();
        String temp = indentationFor(level) + "VarRef(" + node.locationAsString() + ")\n";
        exitNode();
        return temp;
    }

    // Team 1
    String visitAssignment(AstAssignmentNode node) {
        enterNode();
        String temp = indentationFor(level) + "Assig(" + node.locationAsString() + ")\n" + visitChildren(node);
        exitNode();
        return temp;
    }

    // Team 2

    // Team 3

    // Team 4
    @Override
    String visitIfElseStmt(AstIfElseStmtNode node) {
        enterNode();
        String temp = indentationFor(level) + "IfElseStmt(" + node.locationAsString() + ")\n" + visitChildren(node);
        exitNode();
        return temp;
    }

    // Team 5

    // Team 6

    // Team 7
    String visitDruckeStmt(AstDruckeStmtNode node) {
        enterNode();
        String temp = indentationFor(level) + "DruckeStmt(" + node.locationAsString() + ")\n";
        exitNode();
        return temp;
    }

    // Team 8
    @Override
    String visitReturnStmt(AstReturnStmtNode node) {
        enterNode();
        String temp = indentationFor(level) + "Return(" + node.locationAsString() + ")\n";
        exitNode();
        return temp;
    }

}
