const ajaxUrl = "ajax/voting/";

$(function () {
    $(function () {
        makeEditable({
                ajaxUrl: ajaxUrl}
        );
    });

    let inp_date = $('#filter :input[name="date"]');

    updateFace();

});

function updateFace() {
    let form = $('#voteForm');
    let inpDate = $('#filter :input[name="date"]').val();

    form.empty();

    $.ajax({
         type: "GET",
         url: ajaxUrl,
         dataType: "json",
         data: {date: inp_date},
         success: function(data){
             if(jQuery.isEmptyObject(data)) {
                 warnNoty("common.noPresentMenuDay");
             } else {
                drawTablePanels(data,form, inpDate);
                //$('[data-toggle="tooltip"]').tooltip();
                 $('[data-toggle="tooltip"]').tooltip({
                     animated: "fade",
                     placement: "bottom",
                     html: true
                 });
             }
         }
     });


}

function drawTablePanels(data,form,inp_date) {
    closeNoty();
    let input_date = $("<input/>")
        .attr("type", "hidden")
        .attr("name", "date");

    let div = $('<div/>').addClass("row");

    form.append(input_date);
    form.append(div);
    $.each(data, function(i, item_r) {
        if (input_date.val() === "") input_date.val(item_r.date);

        let restoId = item_r.resto.id;

        //let divInLine = $('<div/>').addClass("col-xs-6");
        let divInLine = $('<div/>').attr("style", "width: 50%; float: left;");
        div.append(divInLine);

        let dl = $("<dl/>").attr("data-restSelected", item_r.selected);
        divInLine.append(dl);

        let dt = $('<dt/>');
        let input = $("<input/>")
            .attr("type", "radio")
            .attr("id", restoId)
            .attr("name", "restoId_" + restoId)
            .attr("checked", item_r.selected)
            .change(function () {
                setVote(this);
            })
            .val(restoId);
        if (item_r.selected === "true") input.attr("checked", "true");

        let label = $("<label/>")
            .attr("for", restoId)
            .text(item_r.resto.name);

        dt.append(input);
        dt.append(label);

        let dd = $("<dd/>");
        let table = $("<table/>").addClass("table table-bordered");
        let thead = $("<thead/>").addClass("thead-dark");
        let tr = $("<tr/>");
        let thn = $("<th/>").attr("style", "width:250px;").text(i18n["dish.name"]);
        let thp = $("<th/>").text(i18n["dish.price"]);

        tr.append(thn);
        tr.append(thp);
        thead.append(tr);

        var tbody = $("<tbody/>");

        table.append(thead);
        table.append(tbody);
        dd.append(table);

        dl.append(dt);
        dl.append(dd);

        divInLine.append(dl);

        //drawTableBodyManualy(tbody, item_r.resto.dishes);
        drawTableBodyAuto(table, item_r.resto.dishes);
    });
}

function drawTableBodyManualy(tbody, data) {
   $.each(data, function(j, item_d) {
                let trb = $("<tr/>");
                let tdn = $("<td/>").text(item_d.name);
                let tdp = $("<td/>").text(item_d.price);

                trb.append(tdn);
                trb.append(tdp);
                tbody.append(trb);
    })
}

function drawTableBodyAuto(table, data) {
    $(table).DataTable({
        "searching": false,
        "paging": false,
        "language": dataTablesLang,
        "info": false,
        "data": data,
        "columns": [
            /*{"data": "name" },*/
            {
                "data": "name",
                "render": function (data, type, row) {
                    if (type === "display" && (row.imgFilePath != undefined) && (row.imgFilePath != "")) {
                        return '<a data-toggle="tooltip" data-placement="bottom" ' +
                            'title="<img class=\'img-thumbnail img-block\' src=\'' + row.imgFilePath + '\'/>">' + data + '</a>';
                    }
                    return data;
                }
            },
            {"data": "price"}],
        "order": [ [ 0, "asc" ] ]
    });
}

function setVote(radioBox) {
    let fDate    = $('#filter :input[name="date"]').val();
    let restoId = radioBox.id;

    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: { date: fDate, restoId: restoId}
    }).done(function () {
        $('#voteForm  :input[type="radio"]').prop('checked', false);
        $('#voteForm dl').attr("data-restSelected", false);
        $(radioBox).prop('checked', true);
        $(radioBox).closest("dl").attr("data-restSelected", true);

        successNoty("common.selectSave");
    }).fail(function (jqXHR, textStatus, errorThrown) {
        $(radioBox).prop('checked', false);
    });
}

function generateDailyMenu() {
    let fDate    = $('#filter :input[name="date"]').val();
    $.ajax({
        type: "POST",
        url: "ajax/admin/dailymenu/",
        data: { date: fDate}
    }).done(function () {
        updateFace();
        successNoty("common.menuWasGenerated");
    });
}