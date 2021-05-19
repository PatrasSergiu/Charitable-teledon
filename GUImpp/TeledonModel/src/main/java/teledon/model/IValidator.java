package teledon.model;

import teledon.model.ValidationException;

public interface IValidator<T> {
    void validate(T entity) throws ValidationException;
}

