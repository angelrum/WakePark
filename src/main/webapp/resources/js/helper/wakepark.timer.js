// переменная, которая отслеживает, сколько секунд прошло
var now_seconds = 0;
// ставим начальные зачение счётчиков
var timer_minutes = 0;
var timer_seconds = 0;

// счетчик общего таймера
var common_minutes = 0;
var common_seconds = 0;

// переменная, которая следит за количеством секунд в таймерах
var intervalVariable;

// разбиваем секунды на часы, минуты и секунды
function secondsToTime(seconds) {
    var h = parseInt(seconds / 3600 % 24);
    var m = parseInt(seconds /  60 % 60);
    var s = parseInt(seconds % 60);
    return {'hours': leadZero(parseInt(h)), 'minutes': leadZero(parseInt(m)), 'seconds': leadZero(parseInt(s))};
}

// для красоты ставим первым ноль, если число минут или секунд меньше девяти
function leadZero(num) {
    var s = "" + num;
    if (s.length < 2) {
        s = "0" + s ;
    }
    return s;
}

// отображение времени на табло
function renderTimerNums(seconds) {
    var timer_nums = secondsToTime(seconds);
    $('.timer_nums.minutes').text(timer_nums.minutes);
    $('.timer_nums.seconds').text(timer_nums.seconds);
}

function renderCommonTimer(seconds) {
    var timer_nums = secondsToTime(seconds);
    $('.timer_common.minutes').text(timer_nums.minutes);
    $('.timer_common.seconds').text(timer_nums.seconds);
}

function startTimer(state) {
    if (typeof state !== "undefined") {
        var timer_nums = secondsToTime(state.time);
        //счетчик очереди
        timer_minutes = parseInt(timer_nums.minutes);
        timer_seconds = parseInt(timer_nums.seconds); //закладываем погрешность в секунду
        //общий счетчик очереди
        timer_nums = secondsToTime(state.queueTime);
        common_minutes = parseInt(timer_nums.minutes);
        common_seconds = parseInt(timer_nums.seconds);

        now_seconds = 0; //сбрасываем счетчик, пришли новые данные
    } else {
        timer_minutes = 5;
        timer_seconds = 0;
    }
    // переменная, которая будет хранить параметры интервального цикла
    var timer_params = {};
    // устанавливаем продолжительность тренировки
    timer_params.time_work = timer_minutes*60 + timer_seconds;
    timer_params.time_common_work = common_minutes*60 + common_seconds;
    // задаём интервал обновления — одна секунда
    intervalVariable = setInterval(timerTick, 985, 'interval', timer_params);
    tooglePlayAndPause(state.state.toLowerCase());
    return false;
}

function tooglePlayAndPause(control) {
    $('#update').addClass('d-none');
    if (typeof control === "undefined") {
        $('#play').toggleClass('d-none');
        $('#pause').toggleClass('d-none');
    } else {
        $('#play').addClass('d-none');
        $('#pause').addClass('d-none');
        $('#' + control).removeClass('d-none');
    }
}

function playToUpdate() {
    $('#play').addClass('d-none');
    $('#update').removeClass('d-none');
}

function stopTimer() {
    pauseTimer();
    // обнуляем табло
    renderTimerNums(timer_minutes * 60 + timer_seconds);
    renderCommonTimer(common_minutes * 60 + common_seconds);
    $('#play').removeClass('d-none');
    $('#pause').addClass('d-none');
    return false;
}

function pauseTimer() {
    clearInterval(intervalVariable);
    tooglePlayAndPause();
    now_seconds = 0;
    return false;
}

// функция, которая отвечает за смену времени на таймере
function timerTick(type, timer_params) {
    // увеличиваем количество прошедших секунд на единицу
    now_seconds++;
    if(timer_params.time_work - now_seconds > 0) {
        renderTimerNums(timer_params.time_work - now_seconds); // показываем, сколько осталось времени
        renderCommonTimer(timer_params.time_common_work - now_seconds); //показываем сколько осталось в общем счетчике
    } else { // иначе, если тренировка закончилась
        //queueClick(QueueControl.STOP); //принудительно завершаем очередь
        //stopTimer();
        playToUpdate();
    }
}