//Основной блок страницы
$(function () {
    $.fn.dataTable.ext.errMode = 'throw';
    makeEditable(ticketsTable);

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });
    /*
        // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
        $.ajaxSetup({cache: false});

     */
});