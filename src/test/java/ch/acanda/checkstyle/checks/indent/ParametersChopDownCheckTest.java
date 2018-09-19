package ch.acanda.checkstyle.checks.indent;

import ch.acanda.checkstyle.checks.ErrorCollector;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ParametersChopDownCheckTest {

    @Test
    @DisplayName("ChopDownIndent should find incorrectly indented parameters")
    void shouldFindIncorrectlyIndentedParameters() throws CheckstyleException, URISyntaxException {
        final ErrorCollector errorCollector = new ErrorCollector();
        final Checker checker = createChecker(ParametersChopDownCheck.class, errorCollector);

        final File file = new File(ParametersChopDownCheckTest.class.getResource("ParametersChopDown.java").toURI());
        checker.process(Arrays.asList(file));

        assertThat(errorCollector.getErrors(), is(
            "[ERROR] 5:28 The indentation of parameter p2 should be aligned with the other parameters on column 27.\n"
            + "[ERROR] 28:21 The indentation of parameter p2 should be aligned with the other parameters on column 20.\n"
            + "[ERROR] 39:25 The indentation of parameter p2 should be aligned with the other parameters on column 17."
        ));
    }

    private Checker createChecker(final Class<? extends AbstractCheck> check, final ErrorCollector errorCollector) throws CheckstyleException {
        final DefaultConfiguration config = new DefaultConfiguration("configuration");
        config.addAttribute("charset", "UTF-8");
        final DefaultConfiguration twConf = new DefaultConfiguration(TreeWalker.class.getName());
        config.addChild(twConf);
        twConf.addChild(new DefaultConfiguration(check.getName()));

        final Checker checker = new Checker();
        final Locale locale = Locale.ENGLISH;
        checker.setLocaleCountry(locale.getCountry());
        checker.setLocaleLanguage(locale.getLanguage());
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(config);
        checker.addListener(errorCollector);
        return checker;
    }

}
