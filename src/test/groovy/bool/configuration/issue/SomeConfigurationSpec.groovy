package bool.configuration.issue

import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Value
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
class SomeConfigurationSpec extends Specification {

    @Inject SomeConfiguration configuration
    @Value('${it}') String valueFromConfig

    void 'value is read from config'() {
        expect:
            valueFromConfig == 'works'
    }

    void 'configuration is loaded'() {
        expect:
            verifyAll(configuration) {
                one
                two
                three
                !four
            }
    }

    @Property(name = 'foo.one', value = 'true')
    @Property(name = 'foo.two', value = 'true')
    @Property(name = 'foo.three', value = 'true')
    @Property(name = 'foo.four', value = 'false')
    void 'configuration works from annotations loaded'() {
        expect:
            verifyAll(configuration) {
                one
                two
                three
                !four
            }
    }


}
