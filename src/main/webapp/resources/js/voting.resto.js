const restoAjaxUrl = "ajax/admin/resto/";

function enable(chkbox, id) {
    const enabled = chkbox.is(":checked");
//  https://stackoverflow.com/a/22213543/548473
    $.ajax({
        url: restoAjaxUrl + id,
        type: "POST",
        data: "enabled=" + enabled
    }).done(function () {
        chkbox.closest("tr").attr("data-userEnabled", enabled);
        successNoty(enabled ? "common.enabled" : "common.disabled");
    }).fail(function () {
        $(chkbox).prop("checked", !enabled);
    });
}

// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: restoAjaxUrl,
            datatableApi: $("#datatable").DataTable({
                "ajax": {
                    "url": restoAjaxUrl,
                    "dataSrc": ""
                },
                "paging": false,
                "info": true,
                "language": dataTablesLang,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "address",
                        "defaultContent": ""
                    },
                    {
                        "orderable": false,
                        "defaultContent": "",
                        "render": renderEditBtn
                    },
                    {
                        "orderable": false,
                        "defaultContent": "",
                        "render": renderDeleteBtn
                    }
                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ],
                "createdRow": function (row, data, dataIndex) {
                    if (!data.enabled) {
                        $(row).attr("data-userEnabled", false);
                    }
                }
            }),
            updateTable: function () {
                $.get(restoAjaxUrl, updateTableByData);
            }
        }
    );
});