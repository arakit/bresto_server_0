(function () {
    "use strict";

    var bresto = util.namespace("bresto");

    bresto.Node = function (kid, x, y, w, h, keyword, good) {
        this.kid = kid;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.keyword = keyword || "";
        this.good = good || 0;
        this.name = "node" + this.kid;
        this.suggestion = [];
    };

    bresto.Node.prototype = {
        move: function (x, y) {
            this.x = x;
            this.y = y;
            return this;
        },

        zoom: function (w, h) {
            this.w = w;
            this.h = h;
            return this;
        },

        getSuggestion: function () {
            return _.clone(this.suggestion);
        },

        setSuggestion: function (values) {
            this.suggestion = _(this.suggestion).union(values);
        },

        replaceSuggestion: function (values) {
            this.suggestion = _.clone(values);
        }
    };


}());