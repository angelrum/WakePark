package ru.project.wakepark.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.time.LocalTime;
import java.util.Objects;
import java.util.concurrent.*;

@Service
public class TimerService {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private QueueControl control;

    private ConcurrentHashMap<Integer, Task> timers;

    private ExecutorService executor;

    public TimerService(QueueControl control) {
        this.control = control;
        this.executor = Executors.newFixedThreadPool(5);
        this.timers = new ConcurrentHashMap<>();
    }

    void startTimer(int companyId, int userId, int duration) {
        if (timers.containsKey(companyId)) {
            Task task = timers.remove(companyId);
            if (task.isPause()) {
                log.info("Last timer was on pause. Pause time {} sec", task.getDuration());
                start(new Task(companyId, userId, task.getDuration()));
            } else {
                //таймер был запущен, он не на паузе, но пришел новый старт
                //пока ничего не делаем
                log.info("timer was start later");
                timers.put(companyId, task);
            }
        } else {
            log.info("start timer on {} sec", duration);
            start(new Task(companyId, userId, duration));
        }
    }

    void stopTimer(int companyId) {
        Task task = timers.remove(companyId);
        if (Objects.nonNull(task)) {
            log.info("timer was interrupt");
            task.getFuture().cancel(true);
        }
    }

    void pauseTimer(int companyId, int time) {
        Task task = timers.get(companyId);
        if (Objects.nonNull(task)) {
            if (!task.isPause()) {
                task.setPause(true);
                task.setDuration(time);
            } else
                log.info("the timer is already paused. Company {}", companyId);
        } else
            log.info("timer not started. Company {}", companyId);
    }

    private void start(Task task) {
        Future<?> submit = executor.submit(task);
        task.setFuture(submit);
        timers.put(task.companyId, task);
    }

    boolean onPause(int companyId) {
        final Task task = timers.get(companyId);
        return Objects.nonNull(task) && task.isPause();
    }

    boolean isStart(int companyId) {
        return timers.containsKey(companyId);
    }

    int getDuration(int companyId) {
        Task task = timers.get(companyId);
        return Objects.nonNull(task) ? task.getDuration() : 0;
    }

    @PreDestroy
    public void destroy() {
        executor.shutdownNow();
    }
    class Task implements Runnable {

        private final int companyId;
        private final int userId;

        volatile private Future future;
        //продолжительность отсчета в сек.
        //если ставится на паузу, то переписывается
        volatile private int duration;
        volatile private boolean pause = false;

        Task(int companyId, int userId, int duration) {
            this.companyId = companyId;
            this.userId = userId;
            this.duration = duration;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public boolean isPause() {
            return pause;
        }

        public void setPause(boolean pause) {
            this.pause = pause;
        }

        Future getFuture() {
            return future;
        }

        void setFuture(Future future) {
            this.future = future;
        }

        @Override
        public void run() {
            log.info("Watch start user {} from company {}", userId, companyId);
            try {
                TimeUnit.SECONDS.sleep(duration);
                if (!pause) {
                    timers.remove(companyId);
                    control.queueStop(companyId, userId);
                }
            } catch (InterruptedException e) {
                log.info("error in watch task");
                //e.printStackTrace();
            } finally {
                log.info("Watch stop. Company {}", companyId);
            }
        }
    }

}
