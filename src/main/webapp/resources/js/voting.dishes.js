const dishAjaxUrl = "ajax/admin/dishes/";

$(function () {
    makeEditable({
            ajaxUrl: dishAjaxUrl,
            datatableApi: $("#datatable").DataTable({
                "ajax": {
                    "url": dishAjaxUrl,
                    "dataSrc": ""
                },
                "paging": false,
                "language": dataTablesLang,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "price"
                    },
                    {
                        "data": "imgFilePath",
                        "orderable": false,
                        "defaultContent": "",
                        "render": function (data, type, row) {
                            if (type === "display" && (data != undefined) && (data != "")) {
                                return "<img src='" + data + "' class='img-thumbnail img-block'/>";
                            }
                            return data;
                        }
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
                $.get(dishAjaxUrl, updateTableByData);
            }
        }
    );
});


function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#img_file_pic').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
    }
}


