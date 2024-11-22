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
