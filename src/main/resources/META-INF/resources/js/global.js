$(document).ready(function () {
    $("#string-messages").submit(function (event) {
        $("#btn-send").prop('disabled', true);
        var formData = {
            topic: $("#topic-name").val(),
            data: $("#message-data").val()
        };
        $.ajax({
            type: "POST",
            url: "/string-messages",
            data: JSON.stringify(formData),
            datatype: 'json',
            contentType: 'application/json',
        }).done(function (data) {
            $('#string-messages').trigger("reset");
            $("#btn-send").prop('disabled', false);
        }).fail(function (data) {
            alert(JSON.stringify(data.responseJSON, null, 2));
            $("#btn-send").prop('disabled', false);
        });

        event.preventDefault();
    });
});