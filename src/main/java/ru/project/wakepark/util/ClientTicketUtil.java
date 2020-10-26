package ru.project.wakepark.util;

import org.springframework.util.CollectionUtils;
import ru.project.wakepark.model.Client;
import ru.project.wakepark.model.ClientTicket;
import ru.project.wakepark.model.Ticket;
import ru.project.wakepark.to.ClientTicketTo;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class ClientTicketUtil extends AbstractUtil<ClientTicket, ClientTicketTo> {

    private static ClientTicketUtil instance;

    //Double checked locking
    public static ClientTicketUtil getInstance() {
        if (instance == null) {
            synchronized (ClientTicketUtil.class) {
                if (instance == null)
                    instance = new ClientTicketUtil();
            }
        }
        return instance;
    }

    private ClientTicketUtil() {
    }

    @Override
    public ClientTicketTo createTo(ClientTicket ct) {
        Ticket t = ct.getTicket();
        boolean countEdit = TicketUtil.countEdit(t.getPass());
        return new ClientTicketTo(ct.getId(), ct.getClient().getId(),
                t.getId(), t.getPass().toString(), t.getName(), t.isEquipment(),
                countEdit, t.getStartTime(), t.getEndTime(), t.getMonth(), t.getDay(), t.getDuration(), 1);
    }

    public ClientTicketTo createToWithCount(ClientTicket ct, Long count) {
        ClientTicketTo to = createTo(ct);
        to.setCount(count.intValue());
        return to;
    }

    public List<ClientTicketTo> getTos(List<ClientTicket> ctl) {
        List<ClientTicketTo> tos = new ArrayList<>();
        Map<Ticket, Long> cnCtl = ctl.stream()
                .collect(Collectors.groupingBy(ClientTicket::getTicket, Collectors.counting()));

//        cnCtl.forEach((k, v)-> {
//            ClientTicket ctb = ctl.stream()
//                    .filter(ct -> ct.getTicket().equals(k)).findFirst().get();
//            tos.add(createToWithCount(ctb, v));
//        });

        cnCtl.forEach((k, v)->ctl.stream()
                .filter(ct->ct.getTicket().equals(k))
                .findFirst()
                .ifPresent(clientTicket -> tos.add(createToWithCount(clientTicket, v))));
        return tos;
    }

    public List<ClientTicketTo> getTosWithFilter(List<ClientTicket> ctl, LocalTime startTime, LocalTime endTime) {
        return getTos(ctl)
                .stream()
                .filter(ct -> DateTimeUtil.isBetweenHalfOpen(ct.getStartTime(), startTime, endTime) || DateTimeUtil.isBetweenHalfOpen(ct.getEndTime(), startTime, endTime))
                .collect(Collectors.toList());
    }

    public ClientTicket createModel(ClientTicketTo to, Client cl, Ticket t) {
        ClientTicket ct = createModel(cl, t);
        ct.setId(to.getId());
        return ct;
    }

    public ClientTicket createModel(Client cl, Ticket t) {
        return new ClientTicket(null, null, cl, t, t.getStartDate(), t.getEndDate(), true, null, null, null, null);
    }

    public List<ClientTicket> getByTicket(List<ClientTicket> clts, Ticket t) {
        return clts.stream()
                .filter(cl->cl.getTicket().equals(t))
                .collect(Collectors.toList());
    }

    public List<ClientTicket> getRemoveList(List<ClientTicket> clts, int count) {
        if (!CollectionUtils.isEmpty(clts)) {
            List<ClientTicket> removeList = new ArrayList<>();
            for (int i = 0; i < clts.size() - count; i++)
                removeList.add(clts.get(i));
            return removeList;
        }
        return null;
    }

    public List<ClientTicket> getAddList(Client cl, Ticket t, int count) {
        List<ClientTicket> list = new ArrayList<>();
        int step = 0;
        do {
            list.add(createModel(cl, t));
            step++;
        } while (step < count);
        return list;
    }

    public List<ClientTicket> filterList(List<ClientTicket> tickets, LinkedList<Set<ClientTicket>> queue) {
        queue.stream()
                .flatMap(Collection::stream)
                .filter(tickets::contains)
                .forEach(tickets::remove);
        return tickets;
    }
}
