package de.dhbw.mh.rinne.ast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.dhbw.mh.rinne.CodeLocation;

public abstract class AstNode {

    protected final CodeLocation codeLocation;
    protected LinkedList<AstNode> children = new LinkedList<>();

    public AstNode(CodeLocation codeLocation) {
        this.codeLocation = codeLocation;
    }

    public abstract <T> T accept(AstVisitor<T> visitor);

    public List<AstNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public String locationAsString() {
        return codeLocation.inColonNotation();
    }

}
