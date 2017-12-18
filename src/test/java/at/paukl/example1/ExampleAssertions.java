package at.paukl.example1;

/**
 * @author ext.pkling
 */
public class ExampleAssertions {

    public static SystemStatusAssert assertThat(SystemStatus systemStatus) {
        return new SystemStatusAssert(systemStatus);
    }
}
