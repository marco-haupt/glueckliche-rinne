package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;

import java.util.List;

public class AstIfElseStmtNode extends AstStmtNode {

    final AstExpressionNode condition;
    final List<AstStmtNode> statements;
    final List<AstStmtNode> elseBlock;

    public AstIfElseStmtNode(CodeLocation codeLocation, AstExpressionNode condition, List<AstStmtNode> statements, List<AstStmtNode> elseBlock) {

        super(codeLocation);
        this.condition = condition;
        this.elseBlock = elseBlock;
        
        int temp = statements.size() - this.elseBlock.size();
        this.statements = statements.subList(0, temp);
        
        this.children.add(condition);
        this.children.addAll(this.statements);
        this.children.addAll(elseBlock);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitIfElseStmt(this);
    }
}