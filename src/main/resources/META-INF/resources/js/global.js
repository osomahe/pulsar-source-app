$(document).ready(function () {
    $("#string-messages").submit(async function (event) {
        event.preventDefault();
        $("#btn-send").prop('disabled', true);

        const file = $("#file-zip")[0].files[0];
        let formData;
        let url;
        let jsonValid;
        if (file) {
            url = "/zip-messages"
            formData = {
                topic: $("#topic-name").val(),
                zipBase64: await toBase64(file)
            };
        } else {
            url = "/string-messages"
            formData = {
                topic: $("#topic-name").val(),
                data: $("#message-data").val()
            };
            jsonValid = $("#json-valid").is(':checked');
            localStorage.setItem("jsonValid", jsonValid);
            if(jsonValid && !isValidJson(formData.data)){
                alert("JSON is not valid");
                $("#btn-send").prop('disabled', false);
                return;
            }
        }

        localStorage.setItem("topicName", formData.topic);


        $.ajax({
            type: "POST",
            url,
            data: JSON.stringify(formData),
            datatype: 'json',
            contentType: 'application/json',
        }).done(function (data) {
            $("#btn-send").prop('disabled', false);
            $('#string-messages').trigger("reset");
            $('.input-file__info').text("No file chosen");
            $("#topic-name").val(formData.topic);
            if (jsonValid) {
                $("#json-valid").prop('checked', jsonValid);
            }
            rowVisibity();
        }).fail(function (data) {
            $("#btn-send").prop('disabled', false);
            $('#file-zip').trigger("reset");
            alert(JSON.stringify(data.responseJSON, null, 2));
        });

    });

    let storageTopicName = localStorage.getItem("topicName");
    if(storageTopicName && storageTopicName !== "undefined"){
        $("#topic-name").val(storageTopicName);
    }
    let storageJsonValid = localStorage.getItem("jsonValid");
    if(storageJsonValid && storageJsonValid !== "undefined"){
        $("#json-valid").prop('checked', storageJsonValid);
    }
});

const isValidJson = (json) => {
    try {
        JSON.parse(json);
        return true;
    } catch (e) {
        return false;
    }
}

const rowVisibity = () => {
    const file = $("#file-zip")[0].files[0];
    if (file) {
        $("#row-string").hide();
        $("#message-data").prop('required', false);
    } else {
        $("#row-string").show();
        $("#message-data").prop('required', true);
    }
    const msg = $("#message-data").val();
    if (msg) {
        $("#row-file").hide();
        $("#file-zip").prop('required', false);
    } else {
        $("#row-file").show();
        $("#file-zip").prop('required', true);
    }

}

const toBase64 = file => new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result.substring(reader.result.indexOf(',') + 1));
    reader.onerror = error => reject(error);
});

(function ($) {
    'use strict';

    /*[ File Input Config ]
        ===========================================================*/

    try {

        var file_input_container = $('.js-input-file');

        if (file_input_container[0]) {

            file_input_container.each(function () {

                var that = $(this);

                var fileInput = that.find(".input-file");
                var info = that.find(".input-file__info");

                fileInput.on("change", function () {

                    var fileName;
                    fileName = $(this).val();

                    if (fileName.substring(3, 11) == 'fakepath') {
                        fileName = fileName.substring(12);
                    }

                    if (fileName == "") {
                        info.text("No file chosen");
                    } else {
                        info.text(fileName);
                    }

                })

            });

        }


    } catch (e) {
        console.log(e);
    }

})(jQuery);

$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "/info",
    }).done(function (data) {
        $('#version').html('(' + data.version + ')');
    }).fail(function (data) {
        $('#version').html("(unknown)");
    });
});
