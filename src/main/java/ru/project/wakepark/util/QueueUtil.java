package ru.project.wakepark.util;

import org.springframework.util.CollectionUtils;
import ru.project.wakepark.model.Client;
import ru.project.wakepark.model.ClientTicket;
import ru.project.wakepark.to.ControlQueueRow;
import ru.project.wakepark.to.QueueRowTo;
import ru.project.wakepark.util.exception.ValidationInputDataException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.project.wakepark.util.ValidationUtil.checkNotFound;
import static ru.project.wakepark.util.exception.ValidationInputDataException.*;

public class QueueUtil {

    public static void checknotfoundwithid(boolean found, int companyId, int clientId, int ticketId) {
        checkNotFound(found, String.format("companyId=%s, clientId=%s and ticketId=%s", companyId, clientId, ticketId));
    }

    public static <T> List<T> checkNotFoundWithId(List<T> objects, int companyId, int clientId, int ticketId) {
        checknotfoundwithid(!CollectionUtils.isEmpty(objects), companyId, clientId, ticketId);
        return objects;
    }

    public static <T> List<T> checkNotEqualByCount(List<T> objects, int count) {
        if (objects.size() < count) {
            throw new ValidationInputDataException(EXCEPTION_VALID_CLIENT_TICKET_COUNT);
        }
        return objects;
    }

    private static QueueRowTo createTo(ClientTicket ct,  ControlQueueRow upOrDown, ControlQueueRow control, boolean disabled, int count) {
        Client cl = ct.getClient();
        return new QueueRowTo(ct.id(), upOrDown, disabled, control,
                String.format("%s %s %s", cl.getLastname(), cl.getFirstname(), cl.getMiddlename()), ct.getTicket().getPass(), count);
    }

    private static QueueRowTo createTo(Set<ClientTicket> set, ControlQueueRow upOrDown, ControlQueueRow control, boolean disabled) {
        return createTo(set.iterator().next(), upOrDown, control, disabled, set.size());
    }

    private static QueueRowTo createTo(Set<ClientTicket> set, ControlQueueRow control, boolean disabled, int count) {
        return createTo(set.iterator().next(), ControlQueueRow.ALL, control, disabled, count);
    }

    //для активной очереди, с проверкой, запущен ли таймер или нет
    public static List<QueueRowTo> createTos(LinkedList<Set<ClientTicket>> lists, boolean watchStart) {
        if (watchStart) {
            List<QueueRowTo> result = new ArrayList<>();
            LinkedList<Set<ClientTicket>> copy = new LinkedList<>(lists);
            Set<ClientTicket> sClTickets = copy.pollFirst();
            result.add(createTo(sClTickets, ControlQueueRow.ALL, ControlQueueRow.PAUSE, true));
            result.addAll(createTos(copy, ControlQueueRow.PAUSE));
            return result;
        } else
            return createTos(lists, ControlQueueRow.PAUSE);
    }

    //без проверки по запуску таймера
    private static List<QueueRowTo> createTos(LinkedList<Set<ClientTicket>> lists, ControlQueueRow control) {
        Predicate<Set<ClientTicket>> isFirst = x->lists.getFirst().equals(x);
        Predicate<Set<ClientTicket>> isLast = x->lists.getLast().equals(x);

        Function<Set<ClientTicket>, ControlQueueRow> getUpOrDown = x -> isFirst.test(x) && isLast.test(x) ? ControlQueueRow.NONE
                : isFirst.test(x) ? ControlQueueRow.DOWN : isLast.test(x) ? ControlQueueRow.UP : ControlQueueRow.ALL;

        return lists.stream()
                .map(x->createTo(x, getUpOrDown.apply(x), control, false))
                .collect(Collectors.toList());
    }

    //для не активной очереди, с блокировкой элементов управления
    public static List<QueueRowTo> createTos(LinkedList<Set<ClientTicket>> lists, ControlQueueRow control, ControlQueueRow upOrDown) {
        return lists.stream()
                .map(x->createTo(x, upOrDown, control, false))
                .collect(Collectors.toList());
    }


}
