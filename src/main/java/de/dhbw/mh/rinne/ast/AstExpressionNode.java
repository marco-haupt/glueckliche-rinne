package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;
import de.dhbw.mh.rinne.RinneType;

public abstract class AstExpressionNode extends AstNode {

    private RinneType type;

    public AstExpressionNode(CodeLocation codeLocation) {
        super(codeLocation);
    }

    public CodeLocation getCodeLocation() {
        return codeLocation;
    }

    public RinneType getType() {
        return type;
    }

}
