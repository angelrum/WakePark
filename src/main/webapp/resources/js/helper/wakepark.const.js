const languageDatatable = {
    "language" : {
        processing: "Подождите...",
        search: "",
        lengthMenu: "<h6>Показать _MENU_ записей</h6>",
        info: "Записи с _START_ до _END_ из _TOTAL_ записей",
        infoEmpty: "Записи с 0 до 0 из 0 записей",
        infoFiltered: "(отфильтровано из _MAX_ записей)",
        infoPostFix: "",
        loadingRecords: "Загрузка записей...",
        zeroRecords: "Записи отсутствуют.",
        emptyTable: "В таблице отсутствуют данные",
        paginate: {
            first: "Первая",
            previous: "Предыдущая",
            next: "Следующая",
            last: "Последняя"
        },
        aria: {
            sortAscending: ": активировать для сортировки столбца по возрастанию",
            sortDescendin: ": активировать для сортировки столбца по убыванию"
        },
        select: {
            rows: {
                "0": "Кликните по записи для выбора",
                "1": "Выбрана одна запись",
                "_": "Выбрано записей: %d"
            }
        }
    }
};

const HtmlType = {
    NAME: 'name',
    ID: 'id'
};

const MoveRow = {
    UP:     'UP',
    DOWN:   'DOWN',
    PLAY:   'PLAY',
    PAUSE:  'PAUSE',
    DELETE: 'DELETE',
    NONE:   'NONE',
    ALL:    'ALL'
};

const QueueControl = {
    PLAY: 'PLAY',
    PAUSE: 'PAUSE',
    STOP: 'STOP'
};

const Role = {
    ADMIN: "Администратор",
    MANAGER: "Менеджер",
    USER: "Оператор",

};

function getRoleCode(name) {
    switch (name) {
        case 'ADMIN':
            return Role.ADMIN;
        case 'MANAGER':
            return Role.MANAGER;
        case 'USER':
            return Role.USER
    }
}
