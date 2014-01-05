    (function () {
    "use strict";

    var bresto = util.namespace("bresto"),
        id_count = 1;

    bresto.Edge = function (parent, child, id) {
        this.id = id || id_count++;
        this.from_kid = parent;
        this.to_kid = child;
        this.name = "edge" + this.id;
    };

    bresto.Edge.prototype = {
        isEqual: function (edge) {
            return this.from_kid === edge.from_kid && this.to_kid === edge.to_kid;
        }
    };
}());