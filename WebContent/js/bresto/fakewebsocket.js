(function () {
    'use strict';

    var bresto = util.namespace("bresto"),
        user_ageant = "bresto";

    bresto.FakeWebSocket = function () {
        this.readyState = 0;
        this.onopen = function () { };
        this.onclose = function () { };
        this.onerror = function () { };
        this.onmessage = function () { };
        this.send = function () { };

        window.fakeWebSocket = this;
    };

    bresto.FakeWebSocket.prototype = {
        open: function () {
            this.readyState = 1;
            this.onopen();
        },

        close: function () {
            this.readyState = 3;
            this.onclose();
        },

        error: function (error) {
            this.readyState = 3;
            this.onerror(error);
        },

        message: function (message) {
            this.onmessage({data: message});
        }
    };

    // ユーザ
    if (window.navigator.userAgent === user_ageant) {
        window.WebSocket = bresto.FakeWebSocket;
    }

}());

