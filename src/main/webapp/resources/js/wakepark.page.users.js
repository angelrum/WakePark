//Основной блок страницы
$(function () {
    $.fn.dataTable.ext.errMode = 'throw';
    makeEditable(usersTable);

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });
    $.ajaxSetup({cache: false});
    /*
        // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
        $.ajaxSetup({cache: false});
     */
});

$('.btn-group button').click(function () {
    $('.btn-group button').removeClass('btn-outline-active');
    $(this).addClass('btn-outline-active');
});