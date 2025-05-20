package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;

import java.util.HashMap;
import java.util.List;

public class AstFunctionDefinitionNode extends AstStmtNode {
    private final String name;
    private final HashMap<String, String> args;
    private final List<AstStmtNode> body;

    public AstFunctionDefinitionNode(CodeLocation codeLocation, String name, HashMap<String, String> args,
            List<AstStmtNode> body) {
        super(codeLocation);
        this.name = name;
        this.args = args;
        this.body = body;
        this.children.addAll(body);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitFunctionDefinition(this);
    }

}
