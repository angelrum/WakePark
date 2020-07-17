package ru.project.wakepark.util;

import org.slf4j.Logger;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import ru.project.wakepark.util.exception.ErrorType;
import ru.project.wakepark.util.exception.IllegalRequestDataException;
import ru.project.wakepark.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.*;
import java.util.List;
import java.util.Set;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, int id, int companyId) {
        checknotfoundwithid(object != null, id, companyId);
        return object;
    }

    public static <T> List<T> checkNotFoundWithId(List<T> objects, int id, int companyId) {
        checknotfoundwithid(!CollectionUtils.isEmpty(objects), id, companyId);
        return objects;
    }

    public static void checknotfoundwithid(boolean found, int id, int companyId) {
        checkNotFound(found, String.format("id=%s and companyId=%s", id, companyId));

    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String arg) {
        if (!found) {
            throw new NotFoundException(arg);
        }
    }

    public static <T> void checkNotNull(T object) {
        Assert.notNull(object, String.format("%s, must not be null", object.getClass().getName()));
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }

    //  http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }

    public static String getMessage(Throwable e) {
        return e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getClass().getName();
    }

    public static Throwable logAndGetRootCause(Logger log, HttpServletRequest req, Exception e, boolean logException, ErrorType errorType) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
        return rootCause;
    }

    private static final Validator validator;

    static {
        //  From Javadoc: implementations are thread-safe and instances are typically cached and reused.
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        //  From Javadoc: implementations of this interface must be thread-safe
        validator = factory.getValidator();
    }

    public static <T> void validate(T bean, Class<?>... groups) {
        // https://alexkosarev.name/2018/07/30/bean-validation-api/
        Set<ConstraintViolation<T>> violations = validator.validate(bean, groups);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

}
