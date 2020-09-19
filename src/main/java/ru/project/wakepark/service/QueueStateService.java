package ru.project.wakepark.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.project.wakepark.model.ClientTicket;
import ru.project.wakepark.model.ClientTicketStory;
import ru.project.wakepark.model.Ticket;
import ru.project.wakepark.repository.QueueRepository;
import ru.project.wakepark.to.ControlQueueRow;
import ru.project.wakepark.to.QueueState;
import ru.project.wakepark.util.ControlQueue;
import ru.project.wakepark.util.DateTimeUtil;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Set;
import static ru.project.wakepark.util.ValidationUtil.*;

@Service
public class QueueStateService {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private TimerService watch;

    private QueueRepository repository;

    private StoryService storyService;

    @Autowired
    public QueueStateService(TimerService watch, QueueRepository repository, StoryService storyService) {
        this.watch = watch;
        this.repository = repository;
        this.storyService = storyService;
    }

    /*
    * Если на паузе
    *   @state     = PLAY
    *   @time      = время из таймера @Task.duration
    *   @queueTime = сумма времени очереди без первой записи + @time
    * Иначе если запущено
    *   @state     = PAUSE
    *   @time      = пройденной время с момента запуска
    *   @queueTime = сумма времени очереди без первой записи + @time
    * Иначе
    *   @state     = PLAY
    *   @time      = Продолжительноть первой строки
    *   @queueTime = сумма всех строк
     */

    public QueueState getState(int companyId) {
        LinkedList<Set<ClientTicket>> queue = repository.getActiveQueue(companyId);
        QueueState state = new QueueState(ControlQueue.PLAY, 0, 0, 0);
        if (CollectionUtils.isEmpty(queue)) return state;

        ClientTicket first = repository.getFistTicket(companyId);
        LocalTime end = LocalTime.now().withNano(0);
        LocalTime time = LocalTime.of(0, first.getTicket().getDuration());
        int sum = queue.stream().filter(set -> !set.iterator().next().equals(first))
                .map(set -> set.iterator().next().getTicket())
                .mapToInt(Ticket::getDuration).sum(); //сумма очереди без первого элемента, в минутах

        state.setEndTime(time.toSecondOfDay());

        if (watch.onPause(companyId)) {
            state.setState(ControlQueue.PLAY);
            time = LocalTime.ofSecondOfDay(watch.getDuration(companyId));
        } else if (watch.isStart(companyId)) {
            state.setState(ControlQueue.PAUSE);
            ClientTicketStory story = checkNotFoundWithId(storyService.getOpenStory(companyId, first.getId()), first.getId(), companyId);
            time = DateTimeUtil.remainderOfTime(story.getStartTime(), end, LocalTime.ofSecondOfDay(watch.getDuration(companyId)));
            log.info("calculate state time with start time {}, end time {}, duration {}", story.getStartTime(), end, LocalTime.ofSecondOfDay(watch.getDuration(companyId)));
            //time = LocalTime.ofSecondOfDay(watch.getDuration(companyId));
        }
        log.info("result = {} sec", time.toSecondOfDay());
        state.setTime(time.toSecondOfDay());
        state.setQueueTime(sum*60 + time.toSecondOfDay());
        return state;
    }
//    public QueueState getState(int companyId) {
//        QueueState state = new QueueState();
//        LinkedList<Set<ClientTicket>> queue = repository.getActiveQueue(companyId);
//        int sum = queue.stream().map(set -> set.iterator().next().getTicket()).mapToInt(Ticket::getDuration).sum();
//
//        LocalTime duration = LocalTime.of(0, queue.getFirst().iterator().next().getTicket().getDuration());
//        ClientTicket fistTicket = checkNotFound(repository.getFistTicket(companyId), "Ошибка при определении активной очереди");
//        LocalTime queueTime = LocalTime.of(0, sum);
//        LocalTime time = LocalTime.from(duration);
//
//        if (watch.onPause(companyId)) {
//            state.setState(ControlQueueRow.PLAY);
//            state.setTime(watch.getDuration(companyId));
//        }else if (watch.isStart(companyId)) {
//            state.setState(ControlQueueRow.PAUSE);
//            ClientTicketStory story = checkNotFound(storyService.getOpenStory(companyId, fistTicket.getId()), "Ошибка при опеределении истории списания");
//            time = DateTimeUtil.remainderOfTime(story.getStartTime(), LocalTime.now(), time);
//            queueTime = DateTimeUtil.remainderOfTime(story.getStartTime(), LocalTime.now(), queueTime);
//            state.setTime(time.toSecondOfDay());
//        } else {
//            state.setState(ControlQueueRow.PLAY);
//            state.setTime(time.toSecondOfDay());
//        }
//
//        state.setQueueTime(queueTime.toSecondOfDay());
//        state.setEndTime(duration.toSecondOfDay());
//
//        return state;
//    }
    
}
