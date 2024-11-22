package bool.configuration.issue

import groovy.transform.CompileStatic
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
class SomeConfigurationSpec extends Specification {

    @Inject SomeConfiguration configuration

    void 'configuration is loaded'() {
        expect:
            verifyAll(configuration) {
                one
                two
                three
                !four
            }
    }

}
