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

function renderCount(data, type, row, enable) {
    var disabled = "";
    if (typeof enable != "undefined"
            && enable === false)
        disabled = "disabled";
    data = (typeof data != "undefined") ? data : 1;
    return "<input id='count_"+ row.id + "' name='count' type='number' value = "+ data +" min='1' max='99' "+ disabled +">";
}

function renderRadioId(data, type, row, name) {
    if (type === "display") {
        var nm = 'select';
        if (typeof name === 'string' && name.length > 0) {
            nm = name;
        }
        return "<input type='radio' id='radio"+ row.id +"' value='"+ data +"' name='"+ nm +"' onchange='handleOnChange(this, true)'>";
    }
}

function renderLabelRadio(data, type, row) {
    if (type === "display") {
        if (data === undefined) {
            return null;
        }
        return "<label for='radio"+ row.id +"'>"+ data +"</label>"
    }
}

function renderCheckBoxWithLabelRadio(data, type, row) {
    if (type === "display") {
        var check = (data === true) ? 'checked' : '';
        var res = "<div class='custom-control custom-checkbox'>" +
            "<input type='checkbox' class='custom-control-input' disabled " + check +">" +
            "<span class='custom-control-label'>" +
            "</span></div>";
        return renderLabelRadio(res, type, row);
    }
}

function convertTimeWithLabelRadio(data, type, row) {
    if (type === "display"
        && typeof data != "undefined" && data.length === 8) {
        return renderLabelRadio(data.substring(0, 5), type, row) //убираем секунды
    }

}