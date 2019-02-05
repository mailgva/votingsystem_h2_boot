let context, form;

function makeEditable(ctx) {
    context = ctx;
    form = $("#detailsForm");
    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});

    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
}

function add() {
    $("#modalTitle").html(i18n["addTitle"]);
    form.find(":input").val("");
    $("#editRow").modal();
}

function updateRow(id) {
    $("#modalTitle").html(i18n["editTitle"]);
    form.find(":input").val("");
    form.find("img[name='img_file_pic']").attr("src", "");
    $.get(context.ajaxUrl + id, function (data) {
        //console.log(data);
        $.each(data, function (key, value) {
            if(key != "imgFilePath") {
                form.find("input[name='" + key + "']").val(value);
            } else {
                form.find("img[name='img_file_pic']").attr("src", value);
            }
        });
        $("#editRow").modal();
    });
}

function deleteRow(id) {
    bootbox.confirm({
        message: i18n["common.confirmDelete"],
        buttons: {
            confirm: {
                label: "Yes",
                className: "btn-success"
            },
            cancel: {
                label: "No",
                className: "btn-danger"
            }
        },
        callback: function (result) {
            if(! result) return;
            $.ajax({
                url: context.ajaxUrl + id,
                type: "DELETE"
            }).done(function () {
                context.updateTable();
                successNoty("common.deleted");
            });
        }
    });
}

function updateTableByData(data) {
    context.datatableApi.clear().rows.add(data).draw();
}

function save() {
    let aForm = new FormData();

    $.each($(form).find("input"), function(i, item) {
        if($(item).prop("type") === "file") {
            aForm.append($(item).prop("name"), $(item).get(0).files[0] != undefined ? $(item).get(0).files[0] : null);
        } else {
            aForm.append($(item).prop("name"), $(item).val());
        }
    });


    $.ajax({
        /*type: "POST",
        url: context.ajaxUrl,
        data: form.serialize()*/
        type: "POST",
        url: context.ajaxUrl,
        data: aForm,
        cache: false,
        contentType: false,
        processData: false
    }).done(function () {
        $("#editRow").modal("hide");
        context.updateTable();
        successNoty("common.saved");
    });
}

let failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(key) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + i18n[key],
        type: "success",
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function failNoty(jqXHR) {
    closeNoty();
    // https://stackoverflow.com/questions/48229776
    const errorInfo = JSON.parse(jqXHR.responseText);
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;" + errorInfo.typeMessage + "<br>" + errorInfo.details.join("<br>"),
        type: "error",
        layout: "bottomRight"
    }).show();
}

function warnNoty(key) {
    closeNoty();
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-info-circle'></span> &nbsp;" + i18n[key],
        type: "information",
        layout: "bottomRight",
        timeout: 3000
    }).show();
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