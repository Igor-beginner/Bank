package md.brainet.service.bank.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Validator;
import jakarta.validation.groups.Default;
import md.brainet.service.bank.controller.view.payload.AdminPayloadOfUserCreation;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@Component
public class EditedUserValidator implements ConstraintValidator<Password, AdminPayloadOfUserCreation> {

    private final Validator validator;

    public EditedUserValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public boolean isValid(AdminPayloadOfUserCreation payload,
                           ConstraintValidatorContext constraintValidatorContext) {

        PasswordFieldHandler passwordFieldHandler = new PasswordFieldHandler(payload);
        passwordFieldHandler.excludePasswordIf(String::isEmpty);

        return validateAll(passwordFieldHandler.getFields(), payload);
    }

    private boolean validateAll(Set<Field> fields, Object object) {
        for(Field field : fields) {
            var res = validator.validateProperty(object, field.getName(), Default.class);
            if(!res.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private static class PasswordFieldHandler {
        final Object handled;
        final Set<Field> fields;
        final Field password;

        PasswordFieldHandler(Object object) {
            this.handled = object;
            this.fields = getFieldsSet(object);
            this.password = findFieldAnnotatedPassword(fields);
            ReflectionUtils.makeAccessible(password);
        }

        void excludePasswordIf(Predicate<String> predicate) {
            String password = (String) ReflectionUtils.getField(this.password, handled);
            if (predicate.test(password)) {
                fields.remove(this.password);
            }
        }

        Field findFieldAnnotatedPassword(Set<Field> fields) {
            return fields.stream()
                    .filter(f -> f.isAnnotationPresent(Password.class))
                    .findFirst().get();
        }

        Set<Field> getFieldsSet(Object object) {
            return new HashSet<>(Set.of(object.getClass().getDeclaredFields()));
        }

        Set<Field> getFields() {
            return fields;
        }
    }
}