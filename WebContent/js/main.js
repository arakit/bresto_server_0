$(function () {
    "use strict";

    var bresto = util.namespace('bresto'),
        host = ""+location.host,
        //host = "192.168.1.4:8080",
        wsUrl = "ws://"+host+"/BreStoServer0/api/socket_node_edge",
        submitUrl = "http://"+host+"/BreStoServer0/api/add_node",
        goodUrl = "http://"+host+"/BreStoServer0/api/set_good",
        //wsUrl = "ws://192.168.1.117:8080/BreStoServer0/api/socket_node_edge",
        //submitUrl = "http://192.168.1.117:8080/BreStoServer0/api/add_node",
        //goodUrl = "http://192.168.1.117:8080/BreStoServer0/api/set_good",
        is_form_enabled = ko.observable(false),
        node_suggestion = ko.observableArray([]),
        node_from = ko.observable(""),
        node_to = ko.observable(""),
        node_good = ko.observable(0),
        canvas,
        system,
        connection;

    console.log("host = " + host);
    console.log("websocket = " + wsUrl);

    function show_indicator() {
        $("#loading").fadeIn();
        $(".bresto").animate({
            opacity: 0.5
        }, 500);
    }

    function hide_indicator() {
        $("#loading").fadeOut();
        $(".bresto").animate({
            opacity: 1
        }, 500);
    }

    function show_aside() {
        Flipsnap(".suggestion", {
            distance: 80
        });
        $("#footer")
            .css("display", "block")
            .animate({bottom: 0}, is_form_enabled.bind(undefined, true));
        $("#good")
            .css("display", "block")
            .animate({right: 0});
    }

    function hide_aside() {
        is_form_enabled(false);
        $("#footer").animate({bottom: -82}, function () {
            node_from("");
            node_to("");
            node_suggestion("");
            $(this).css("display", "none");
        });
        $("#good").animate({right: -100}, function () {
            $(this).css("display", "none");
        });
    }

    function alert(message, klass, time) {
        var $dom = $(
            '<div class="message alert alert-dismissable fade in ' + klass + '">' +
                '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>' +
                message + '</div>'
        );
        time = time || 2000;

        $("body").append($dom);
        setTimeout(function () {
            $dom.alert('close');
        }, time);
    }

    // knockout
    ko.applyBindings({
        isFormEnabled: is_form_enabled,
        suggestion: node_suggestion,
        onClickSuggestion: function () {
            if (is_form_enabled()) {
                node_to(this.data);
            }
        },
        from: node_from,
        to: node_to,
        good: node_good,
        hideFooter: hide_aside,
        onSubmit: function () {
            if (node_to() !== "") {
                show_indicator();
                is_form_enabled(false);
                connection.addKeyword(node_from().kid, node_to(),
                    function (data, type) { // success
                        hide_aside();
                        hide_indicator();
                        alert("successfully add keyword!", "alert-success");
                    }, function (xhr, status, thrown) { // error
                        hide_indicator();
                        is_form_enabled(true);
                        alert("error add keyword...", "alert-danger");
                    });
            }
            return false;
        },
        onGood: function () {
            show_indicator();
            is_form_enabled(false);
            connection.addGood(node_from().kid,
                function (data, type) { // success
                    hide_aside();
                    hide_indicator();
                    alert("successfully add good!", "alert-success");
                }, function (xhr, status, thrown) { // error
                    hide_indicator();
                    is_form_enabled(true);
                    alert("error add good...", "alert-danger");
                });
            return false;
        }
    });

    // init
    canvas = new bresto.Canvas($('canvas.bresto'), {
        "width": 1024,
        "height": 1024,
        onClickNode: function (node) {
            node_to("");
            node_from(node);
            node_suggestion(node.getSuggestion());
            node_good(node.good);
            show_aside();
        }
    });

    system = new bresto.System(canvas);

    connection = new bresto.Connection(wsUrl, submitUrl, goodUrl, {
        onReservedNodeEdge: system.update.bind(system),
        onReservedSuggestion: function (data) {
            system.setSuggestion(data.kid, data.suggest);
        },
        onOpen: hide_indicator,
        onClose: show_indicator,
        onInit: function () {
            console.log("init");
        },
        onMessage: function (data) {
            console.log(data.data);
//            console.log("onmessage");
        }
    });

    // 最初は隠しておく
    $("#footer")
        .css("display", "none")
        .css("bottom", -82);
    $("#good")
        .css("display", "none")
        .css("right", -100);

    connection.init();
});