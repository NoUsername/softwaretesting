package at.paukl.example1;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.description.Description;

import java.util.Objects;

/**
 * @author ext.pkling
 */
public class SystemStatusAssert extends AbstractAssert<SystemStatusAssert, SystemStatus> {

    public SystemStatusAssert(SystemStatus actual) {
        super(actual, SystemStatusAssert.class);
    }

    public SystemStatusAssert hasStatus(SystemStatus.Status status) {
         if (!Objects.equals(actual.getStatus(), status)) {
             failWithMessage("expected status %s, got %s", actual.getStatus(), status);
         }
         return this;
    }

    public SystemStatusAssert messageContains(String substring) {
        if (!actual.getMessage().contains(substring)) {
            failWithMessage("expected message '%s', to contain '%s'", actual.getMessage(), substring);
        }
        return this;
    }
}
