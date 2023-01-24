package ru.otusstudy.otuset.exeptions;

import static java.lang.String.format;

public class EntityNotFoundException extends ServiceException {

    public EntityNotFoundException(String entityType, String entityId) {
        super(format("Сущность %s с идентификатором %s не найдена", entityType, entityId));
    }

    public EntityNotFoundException(Class entityType, String entityId) {
        this(entityType.getName(), entityId);
    }
}
