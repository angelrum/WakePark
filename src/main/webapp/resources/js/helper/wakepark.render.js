function renderCheckBox(data, type, row) {
    if (type === "display") {
        var check = (data === true) ? 'checked' : '';
        return "<div class='custom-control custom-checkbox'>" +
            "<input type='checkbox' class='custom-control-input' disabled " + check +">" +
            "<span class='custom-control-label'>" +
            "</span></div>";
    }
}

function convertTime(data, type, row) {
    if (type === "display"
        && typeof data != "undefined" && data.length === 8) {
        return data.substring(0, 5); //убираем секунды
    }
}

function renderEditBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='updateRow(" + row.id + ");'><span class='fa fa-pencil'></span></a>";
    }
}

function renderDeleteBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='deleteRow(" + row.id + ");'><span class='fa fa-remove'></span></a>";
    }
}

function renderRadioId(data, type, row, name, onchange = true) {
    if (type === "display") {
        var nm = 'select';
        var onch = onchange ? "onchange='handleOnChange(this, true)'" : '';
        if (typeof name === 'string' && name.length > 0) {
            nm = name;
        }
        return "<input type='radio' id='radio"+ row.id +"' value='"+ data +"' name='"+ nm +"' " + onch +">";
    }
}
//может объединить?
function renderRadioIdChT(data, type, row) {
    if (type === "display") {
        return "<input type='radio' id='radio"+ row.id +"' value='"+ data +"' name='select_cht' onchange='handleOnChange(this, false)'>";
    }
}

function renderCount(data, type, row, enable, max = '99') {
    var disabled = "";
    if (typeof enable != "undefined" && enable === false)
        disabled = "disabled";
    data = (typeof data != "undefined") ? data : 1;
    return "<input id='count_"+ row.id + "' name='count' type='number' value = "+ data +" min='1' max="+ max +" "+ disabled +">";
}