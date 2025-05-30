package de.dhbw.mh.rinne.semantic;

import de.dhbw.mh.rinne.ast.AstNode;

public class SemanticException extends RuntimeException {

    private static final long serialVersionUID = -3746582466992273134L;

    public SemanticException(AstNode node, String message) {
        super(message); // node.locationAsString() + ": " +
    }

}
