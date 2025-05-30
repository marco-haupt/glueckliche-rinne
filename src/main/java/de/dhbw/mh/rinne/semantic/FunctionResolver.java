package de.dhbw.mh.rinne.semantic;

import de.dhbw.mh.rinne.ErrorLogger;
import de.dhbw.mh.rinne.ast.AstSemanticVisitor;

public class FunctionResolver extends AstSemanticVisitor<Void> {

    protected FunctionResolver(ErrorLogger logger) {
        super(logger);
    }

}
