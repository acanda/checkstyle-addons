package ch.acanda.checkstyle.checks.indent;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class ParametersChopDownCheck extends AbstractCheck {

    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    public int[] getAcceptableTokens() {
        return new int[]{TokenTypes.PARAMETERS};
    }

    public int[] getRequiredTokens() {
        return new int[0];
    }

    @Override
    public void visitToken(final DetailAST ast) {
        final List<? extends DetailAST> defs = getChildToken(ast, TokenTypes.PARAMETER_DEF);
        if (defs.size() > 1) {
            final int lineMin = defs.stream().mapToInt(DetailAST::getLineNo).min().orElse(-1);
            final int lineMax = defs.stream().mapToInt(DetailAST::getLineNo).max().orElse(-1);
            if (lineMin != lineMax) {
                final DetailAST firstDef = defs.get(0);
                final int firstDefCol = firstDef.getColumnNo();
                final boolean allSameColumn = defs.stream().allMatch(t -> t.getColumnNo() == firstDefCol);
                if (!allSameColumn) {
                    final int col = 1 + CommonUtil.lengthExpandedTabs(getLines()[firstDef.getLineNo() - 1],
                                                                      firstDef.getColumnNo(),
                                                                      getTabWidth());
                    defs.stream()
                        .filter(t -> t.getColumnNo() != firstDefCol)
                        .forEach(t -> report(t, col));
                }
            }
        }
    }

    private void report(final DetailAST paramDef, final int col) {
        final String param = paramDef.findFirstToken(TokenTypes.IDENT).getText();
        final String msg = "The indentation of parameter %s should be aligned with the other parameters on column %s.";
        log(paramDef, String.format(msg, param, col));
    }

    private static List<? extends DetailAST> getChildToken(final DetailAST ast, final int tokenType) {
        final List<DetailAST> children = new ArrayList<>();
        for (DetailAST child = ast.findFirstToken(tokenType); child != null; child = child.getNextSibling()) {
            if (child.getType() == tokenType) {
                children.add(child);
            }
        }
        return children;
    }

}
