const votesAjaxUrl = "ajax/admin/votes/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: votesAjaxUrl + "bydate/",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

$(function () {
    makeEditable({
            ajaxUrl: votesAjaxUrl,
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "language": dataTablesLang,
                "columns": [
                    {
                        "data": "resto.name"
                    },
                    {
                        "data": "user.name"
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
                $.get(votesAjaxUrl, updateFilteredTable);
            }
        }
    );
    updateFilteredTable();
});