## Micronaut Boolean Configuration Issue Demo

There is a single configuration bean `SomeConfiguration` that has four boolean fields `one`, `two`, `three`, and `four` with different setup. It uses `@ConfigurationProperties` annotation to load the configuration with prefix `foo`.

```java
package bool.configuration.issue;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.bind.annotation.Bindable;
import jakarta.annotation.Nullable;

@ConfigurationProperties("foo")
public interface SomeConfiguration {

    @Bindable(defaultValue = "false")
    boolean isOne();

    @Nullable
    Boolean getTwo();

    boolean isThree();

    @Bindable(defaultValue = "true")
    boolean isFour();

}
```

* `one` is using `@Bindable` annotation with default value `false`
* `two` is only using `@Nullable` annotation and is of wrapper type `Boolean`
* `three` is plain `boolean` property
* `four` is using `@Bindable` annotation with default value `true`

The values are supposed to be overridden by the configuration properties in `application-test.yml` file:

```yaml
foo:
  one: true
  two: true
  three: true
  four: false
```

There is a test `SomeConfigurationSpec` that verifies that all the values are `true` except `four`:

```groovy
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


```

When you run the test, you get a following failure instead:

```
Multiple Failures (4 failures)
	org.spockframework.runtime.ConditionNotSatisfiedError: Condition not satisfied:

one
|
false

	org.spockframework.runtime.ConditionNotSatisfiedError: Condition not satisfied:

two
|
null

	org.spockframework.runtime.ConditionFailedWithExceptionError: Condition failed with Exception:

three
|
io.micronaut.core.value.PropertyNotFoundException: No property found for name [foo.three] and type [boolean]
	at io.micronaut.runtime.context.env.ConfigurationIntroductionAdvice.lambda$resolveProperty$0(ConfigurationIntroductionAdvice.java:120)
	at java.base/java.util.Optional.orElseThrow(Optional.java:403)
	at io.micronaut.runtime.context.env.ConfigurationIntroductionAdvice.resolveProperty(ConfigurationIntroductionAdvice.java:120)
	at io.micronaut.runtime.context.env.ConfigurationIntroductionAdvice.intercept(ConfigurationIntroductionAdvice.java:89)
	at io.micronaut.aop.chain.MethodInterceptorChain.proceed(MethodInterceptorChain.java:143)
	at groovy.lang.Closure.getPropertyTryThese(Closure.java:325)
	at groovy.lang.Closure.getPropertyDelegateFirst(Closure.java:315)
	at groovy.lang.Closure.getProperty(Closure.java:301)
	at bool.configuration.issue.SomeConfigurationSpec.configuration is loaded_closure1(SomeConfigurationSpec.groovy:18)
	at groovy.lang.Closure.call(Closure.java:433)
	at spock.lang.Specification.verifyAll(Specification.java:276)
	at bool.configuration.issue.SomeConfigurationSpec.configuration is loaded(SomeConfigurationSpec.groovy:15)

	org.spockframework.runtime.ConditionNotSatisfiedError: Condition not satisfied:

!four
||
|true
false
```


