(function () {
    "use strict";

    var bresto = util.namespace("bresto"),
        MyWebSocket = WebSocket || MozWebSocket,
        defaults = {
            onInit: function () { },
            onOpen: function () { },
            onClose: function () { },
            onError: function () { },
            onMessage: function () { },
            onReservedNodeEdge: function () { },
            onReservedSuggestion: function () { }
        };


    bresto.Connection = function (wsUrl, submitUrl, goodUrl, o) {
        this.options = _.defaults(o, defaults);
        this.wsUrl = wsUrl;
        this.submitUrl = submitUrl;
        this.goodUrl = goodUrl;
        this.socket = null;
        this.restartWait = 500;
        this.monitorWait = 500;
    };

    bresto.Connection.prototype = {
        init: function () {
            var self = this;

            if (self.socket && self.socket.readyState === 0) {
                return;
            }

            self.options.onInit();

            self.socket = new MyWebSocket(self.wsUrl);

            self.socket.onopen = function () {
                self.monitor();
                self.options.onOpen();
            };

            self.socket.onclose = function () {
                setTimeout(self.init.bind(self), self.restartWait);
                self.options.onClose();
            };

            self.socket.onerror = function (error) {
                self.options.onError(error);
            };

            self.socket.onmessage = function (data) {
                var json;
                try {
                    json = JSON.parse(data.data);
                    if (json.type && json.type === "node_edge" && json.data) {
                        self.options.onReservedNodeEdge(json.data.node, json.data.edge);
                    } else if (json.type && json.type === "suggest" && json.data) {
                        self.options.onReservedSuggestion(json.data);
                    }
                } catch (e) {
                    console.log(e);
                }

                self.options.onMessage(data);
            };
        },

        monitor: function () {
            if (this.socket.readyState === 1) { // connecting
                this.socket.send(1); // test send
                setTimeout(this.monitor.bind(this), this.monitorWait);
            }
        },

        addKeyword: function (from_kid, to_keyword, success, error) {
            success = success || function () { };
            error = error || function () { };

            // execute api
//            setTimeout(error, 1500); // dummy
            $.ajax({
                url: this.submitUrl,
                type: "POST",
                //data: encodeURI("keyword=" + to_keyword + "&parent_kid=" + from_kid),
                data: {
                	keyword : to_keyword,
                	parent_kid : from_kid
                },
                success: success,
                error: error
            });
        },

        addGood: function (kid, success, error) {
            success = success || function () { };
            error = error || function () { };

            // execute api
//            setTimeout(success, 1500); //dummy
            $.ajax({
                url: this.goodUrl,
                type: "POST",
//                data: encodeURI("kid=" + kid + "&value=" + true),
                data: {
                	kid : kid,
                	value : true
                },
                success: success,
                error: error
            });
        }
    };
}());