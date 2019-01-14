package com.voting.util;

import com.voting.util.exception.ErrorType;
import org.slf4j.Logger;
import com.voting.HasId;
import com.voting.model.Vote;
import com.voting.util.exception.NotFoundException;
import com.voting.util.exception.PastDateException;
import com.voting.util.exception.TooLateEcxeption;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ValidationUtil {

    private static Calendar setTimeTo(Calendar calendar, int hours, int minutes, int seconds, int milliseconds) {
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE,minutes);
        calendar.set(Calendar.SECOND,seconds);
        calendar.set(Calendar.MILLISECOND,milliseconds);
        return calendar;
    }

    public static void checkTooLate(Vote vote) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // HH:mm:ss

        Calendar voteDate = Calendar.getInstance();
        voteDate.setTime(vote.getDate());

        Calendar limitDate = Calendar.getInstance();

        // если дата меньше сегодняшней
        limitDate = setTimeTo(limitDate, 0,0,0,0);
        if(voteDate.getTime().before(limitDate.getTime()))
            throw new PastDateException(sdf.format(vote.getDate()) + " - Not allowed to choose a restaurant for the past date!");
        // если сегодняшняя дата
        if(voteDate.getTime().equals(limitDate.getTime())){
            limitDate = setTimeTo(limitDate, 11,0,0,0);
            if(Calendar.getInstance().getTime().after(limitDate.getTime()))
                throw new TooLateEcxeption(sdf.format(vote.getDate()) + " - it's to late to select restaurant, already after 11:00 ");
        }
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }


    private ValidationUtil() {
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalArgumentException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
//      http://stackoverflow.com/a/32728226/548473
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.getId() != id) {
            throw new IllegalArgumentException(bean + " must be with id=" + id);
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
}