package ru.project.wakepark.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.project.wakepark.model.ClientTicket;
import ru.project.wakepark.model.Pass;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@Repository
public class QueueRepository {

    private ConcurrentHashMap<Integer, LinkedList<Set<ClientTicket>>> activeQueue;

    private ConcurrentHashMap<Integer, LinkedList<Set<ClientTicket>>> stoppedQueue;

    //изменение может выполняться только одно, параллельно удаления и добавления данных быть не может
    //нужно пересмотреть реализацию перед работой с несколькими компаниями
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    @Autowired
    public QueueRepository(ConcurrentHashMap<Integer, LinkedList<Set<ClientTicket>>> activeQueue,
                           ConcurrentHashMap<Integer, LinkedList<Set<ClientTicket>>> stoppedQueue) {
        this.activeQueue = activeQueue;
        this.stoppedQueue = stoppedQueue;
    }

    public LinkedList<Set<ClientTicket>> getActiveQueue(int companyId) {
        return activeQueue.computeIfAbsent(companyId, s -> new LinkedList<>());
    }

    public LinkedList<Set<ClientTicket>> getStoppedQueue(int companyId) {
        return stoppedQueue.computeIfAbsent(companyId, s -> new LinkedList<>());
    }

    //Добавляем в конец или наполняем текуший список
    public void add(int companyId, Set<ClientTicket> ctl) {
        ClientTicket ct = ctl.iterator().next();

        lock.writeLock().lock();
        try {
            Set<ClientTicket> list = get(companyId, ct, true);
            if (CollectionUtils.isEmpty(list))
                getActiveQueue(companyId).addLast(ctl);
            else
                list.addAll(ctl);
        } finally {
            lock.writeLock().unlock();
        }
    }
    /*
    * Переносим данные из активной очереди в очередь остановленных.
    * Если в активной очререди билетов клиента нет, то возвращаем false
     */
    public boolean moveToStopped(int companyId, ClientTicket ct) {
        lock.writeLock().lock();
        try {
            Set<ClientTicket> list = get(companyId, ct, true);
            if (!CollectionUtils.isEmpty(list)
                    && getActiveQueue(companyId).remove(list))
                return getStoppedQueue(companyId).add(list);

            return false;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /*
    * Переносим данные из остановленной очереди в активную
    * Если в очереди остановленных билетов клиента нет, то возвращаем false
     */
    public boolean moveToActive(int companyId, ClientTicket ct) {
        lock.writeLock().lock();
        try {
            Set<ClientTicket> list = get(companyId, ct, false);
            if (!CollectionUtils.isEmpty(list)
                    && getStoppedQueue(companyId).remove(list))
                return getActiveQueue(companyId).add(list);
            return false;
        } finally {
            lock.writeLock().unlock();
        }
    }

    //если установлен active = true, то смотрим activeQueue, иначе stoppedQueue
    private Set<ClientTicket> get(int companyId, ClientTicket ct, boolean active) {
        LinkedList<Set<ClientTicket>> list = active ? getActiveQueue(companyId) : getStoppedQueue(companyId);
        return list.stream()
                .filter(cts -> cts.iterator().next().getCompanyId().compareTo(ct.getCompanyId())==0)
                .filter(cts -> cts.iterator().next().getClient().getId().compareTo(ct.getClient().getId())==0)
                .filter(cts -> cts.iterator().next().getTicket().getId().compareTo(ct.getTicket().getId())==0)
                .findFirst().orElse(new HashSet<>());
    }

    //удаляем все записи, которые относятся к этому билету
    public boolean removeFromActive(int companyId, ClientTicket ct) {
        lock.writeLock().lock();
        try {
            Set<ClientTicket> list = get(companyId, ct, true);
            if (!CollectionUtils.isEmpty(list))
                return getActiveQueue(companyId).remove(list);

            return false;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean removeFromStopped(int companyId, ClientTicket ct) {
        lock.writeLock().lock();
        try {
            Set<ClientTicket> list = get(companyId, ct, false);
            if (!CollectionUtils.isEmpty(list))
                return getStoppedQueue(companyId).remove(list);

            return false;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean moveUp(int companyId, ClientTicket ct) {
        lock.writeLock().lock();
        try {
            Set<ClientTicket> list = get(companyId, ct, true);
            LinkedList<Set<ClientTicket>> active = getActiveQueue(companyId);
            if (!CollectionUtils.isEmpty(list)) {
                int index = active.indexOf(list);
                if (index!=0) {
                    active.remove(list);
                    active.add(index-1, list);
                    return true;
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
        return false;
    }

    public boolean moveDown(int companyId, ClientTicket ct) {
        lock.writeLock().lock();
        try {
            Set<ClientTicket> list = get(companyId, ct, true);
            LinkedList<Set<ClientTicket>> active = getActiveQueue(companyId);
            if (!CollectionUtils.isEmpty(list)) {
                int index = active.indexOf(list);
                if (index != active.size() - 1) {
                    active.remove(list);
                    active.add(index + 1, list);
                    return true;
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
        return false;
    }

    /*** Списываем билет ***
    * Получаем первый блок билетов, удаляя его.
    * Списываем первый билет и блок возвращаем в список, в самый конец
    ***/
    public ClientTicket reedemTicket(int companyId) {
        lock.writeLock().lock();
        try {
            LinkedList<Set<ClientTicket>> active = getActiveQueue(companyId);
            Set<ClientTicket> list = active.pollFirst();
            if (!CollectionUtils.isEmpty(list)) {
                Iterator<ClientTicket> iterator = list.iterator();
                ClientTicket ct = iterator.next();
                if (ct.getTicket().getPass().equals(Pass.SINGLE)) iterator.remove();
                if (!list.isEmpty()) active.addLast(list);
                return ct;
            }
        } finally {
            lock.writeLock().unlock();
        }
        return null;
    }

    public ClientTicket getFistTicket(int companyId) {
        LinkedList<Set<ClientTicket>> active = getActiveQueue(companyId);
        if (!CollectionUtils.isEmpty(active)) {
            Set<ClientTicket> set = active.getFirst();
            return CollectionUtils.isEmpty(set) ? null : set.iterator().next();
        }
            return null;
    }

    //Удаляем только одну запись и ее возвращаем
    private ClientTicket remove(int companyId, ClientTicket ct) {
        Set<ClientTicket> list = get(companyId, ct, true);
        if (!CollectionUtils.isEmpty(list)) {
            if (list.size() == 1) {
                getActiveQueue(companyId).remove(list);
                return list.iterator().next();
            }
            else {
                ClientTicket ctt = list.iterator().next();
                list.remove(ctt);
                return ctt;
            }
        }

        return null;
    }
}
