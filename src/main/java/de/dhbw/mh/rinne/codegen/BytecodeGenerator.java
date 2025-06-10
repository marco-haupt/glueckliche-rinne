package de.dhbw.mh.rinne.codegen;

import java.util.List;

import de.dhbw.mh.rinne.BinaryOperation;
import de.dhbw.mh.rinne.RinneType;
import de.dhbw.mh.rinne.ast.AstBinaryExpressionNode;
import de.dhbw.mh.rinne.ast.AstIfElseStmtNode;
import de.dhbw.mh.rinne.ast.AstLiteralNode;
import de.dhbw.mh.rinne.ast.AstLiteralNode.Wahrheitswert;
import de.dhbw.mh.rinne.ast.AstPostCheckLoopNode;
import de.dhbw.mh.rinne.ast.AstPreCheckLoopNode;
import de.dhbw.mh.rinne.ast.AstProgramNode;
import de.dhbw.mh.rinne.ast.AstVariableDeclarationStmtNode;
import de.dhbw.mh.rinne.ast.AstVariableReferenceNode;

public class BytecodeGenerator extends BaseBytecodeGenerator {

    public void enterNode() {
    }

    public void exitNode() {
    }

    private String bytecodePrefixFor(RinneType type) {
        switch (type) {
            case GANZZAHL:
                return "l";
            case FLIEßZAHL:
                return "d";
            case SCHNUR:
                return "a";
            case WAHRHEITSWERT:
                return "i";
            default:
                throw new RuntimeException("unexpected type: " + type);
        }
    }

    @Override
    public String visitPost(AstProgramNode node, List<String> children) {
        enterNode();
        String bytecode = String.join("", children);
        exitNode();
        return bytecode;
    }

    @Override
    public String visitVariableDeclarationStmt(AstVariableDeclarationStmtNode node) {
        enterNode();
        String bytecode = "";
        if (node.getInitializer() != null) {
            bytecode += node.getInitializer().accept(this);
            bytecode += "istore " + loadVariableIndex(node.getName()) + "\n";
        }
        exitNode();
        return bytecode;
    }

    @Override
    public String visitVariableReference(AstVariableReferenceNode node) {
        enterNode();
        String bytecode = "iload " + loadVariableIndex(node.getName()) + "\n";
        exitNode();
        return bytecode;
    }

    public String pushFalse() {
        return "ldc 0\n";
    }

    public String pushTrue() {
        return "ldc 1\n";
    }

    @Override
    public String visitLiteral(AstLiteralNode node) {
        if (node.getType().isBoolean()) {
            return ((Wahrheitswert) node.getValue()).state ? pushTrue() : pushFalse();
        }
        return "";
    }

    @Override
    public String visitPost(AstBinaryExpressionNode node, BinaryOperation op, RinneType type, String lhs, String rhs) {
        if (op.isConcatenation()) {
            return "";
        }
        if (op.isArithmetic()) {
            return binaryArithmetic(op, type, lhs, rhs);
        }
        if (op.checksEquality()) {
            return equalityExpressions(op, type, lhs, rhs);
        }
        if (op.isComparison()) {
            return relationalExpressions(op, type, lhs, rhs);
        }
        if (op.isLogical()) {
            return logicalExpressions(op, type, lhs, rhs);
        }
        throw new RuntimeException("unexpected binary operation in code generator");
    }

    public String binaryArithmetic(BinaryOperation op, RinneType type, String lhs, String rhs) {
        String bytecode = lhs + rhs;
        bytecode += bytecodePrefixFor(type);
        switch (op) {
            case ADD:
                bytecode += "add\n";
                break;
            case SUBTRACT:
                bytecode += "sub\n";
                break;
            case MULTIPLY:
                bytecode += "mul\n";
                break;
            case DIVIDE:
                bytecode += "div\n";
                break;
            default:
                throw new RuntimeException("unexpected arithmetic operation");
        }
        return bytecode;
    }

    // Team 1: conjunction and disjunction
    public String logicalExpressions(BinaryOperation op, RinneType type, String lhs, String rhs) {
        return "";
    }

    // Team 2: equal and not equal
    public String equalityExpressions(BinaryOperation op, RinneType type, String lhs, String rhs) {
        return "";
    }

    // Team 3: less than (or equal) and greater than (or equal)
    public String relationalExpressions(BinaryOperation op, RinneType type, String lhs, String rhs) {
        return "";
    }

    // Team 4: if-then-else
    @Override
    public String visitPost(AstIfElseStmtNode node, String condition, String thenBlock, String elseBlock) {
        return "";
    }

    // Team 5: pre-check loop
    @Override
    public String visitPost(AstPreCheckLoopNode node, String condition, String body) {
        return "";
    }

    // Team 6: post-check loop
    @Override
    public String visitPost(AstPostCheckLoopNode node, String condition, String body) {
        return "";
    }

    // Lecturer

}
