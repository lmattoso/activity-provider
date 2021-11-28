package com.activity.provider.utils.annotations;

import org.springframework.core.annotation.AliasFor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    String name() default "John Doe";


    /**
     * Convenience mechanism for specifying the username. The default is "user". If
     * {@link #username()} is specified it will be used instead of {@link #value()}
     *
     * @return
     */
    String value() default "user";

    /**
     * The username to be used. Note that {@link #value()} is a synonym for
     * {@link #username()}, but if {@link #username()} is specified it will take
     * precedence.
     *
     * @return
     */
    String username() default "john_doe";

    /**
     * <p>
     * The roles to use. The default is "USER". A {@link GrantedAuthority} will be created
     * for each value within roles. Each value in roles will automatically be prefixed
     * with "ROLE_". For example, the default will result in "ROLE_USER" being used.
     * </p>
     * <p>
     * If {@link #authorities()} is specified this property cannot be changed from the default.
     * </p>
     *
     * @return
     */
    String[] roles() default {"USER"};

    /**
     * <p>
     * The authorities to use. A {@link GrantedAuthority} will be created for each value.
     * </p>
     *
     * <p>
     * If this property is specified then {@link #roles()} is not used. This differs from
     * {@link #roles()} in that it does not prefix the values passed in automatically.
     * </p>
     *
     * @return
     */
    String[] authorities() default {};

    /**
     * The password to be used. The default is "password".
     *
     * @return
     */
    String password() default "password";

    @AliasFor(annotation = WithSecurityContext.class)
    TestExecutionEvent setupBefore() default TestExecutionEvent.TEST_METHOD;
}
