(function () {
    "use strict";

    var bresto = util.namespace("bresto"),
        defaults = {
            onClickNode: function () { },
            duration: 2000,
            easing: "swing",
            goodK: 1.05,
            // node
            nodeFillStyle: [
                "#ffa8a8",
                "#ffa8d3",
                "#ffa8ff",
                "#d3a8ff",
                "#a8a8ff",
                "#a8d3ff",
                "#a8ffff",
                "#a8ffd3",
                "#a8ffa8",
                "#d3ffa8",
                "#ffffa8",
                "#ffd3a8"
            ],
            // text
            textFillStyle: "#333",
            textFontBaseSize: 14,
            textFontBaseSizeK: 1.2,
            textFontFamily: "メイリオ",
            // edge
            edgeStrokeStyle: "#ccc",
            edgeStrokeWidth: 5
        };

    bresto.Canvas = function ($canvas, opt) {
        this.$canvas = $canvas;
        this.width = (opt && opt.width) || $canvas.attr("width");
        this.height = (opt && opt.height) || $canvas.attr("height");
        this.centerX = this.width / 2;
        this.centerY = this.height / 2;
        $canvas.attr("height", this.width);
        $canvas.attr("width", this.width);
        this.options = _.defaults(opt, defaults);
        this.nodeCount = 0;
    };

    bresto.Canvas.prototype = {
        addNode: function (node) {
            var self = this;

            self.$canvas
                .jCanvas({
                    layer: true,
                    group: ["group_" + node.name],
                    x: self.centerX + node.x,
                    y: self.centerY + node.y
                })
                .addLayer({
                    type: "ellipse",
                    name: node.name,
                    fillStyle: self.options.nodeFillStyle.length === 1 ?
                            self.options.nodeFillStyle :
                            //self.options.nodeFillStyle[Math.floor(Math.random() * self.options.nodeFillStyle.length)],
                            self.options.nodeFillStyle[self.nodeCount % self.options.nodeFillStyle.length],
                    width: 0,
                    height: 0
                })
                .addLayer({
                    type: "text",
                    name: "text_" + node.name,
                    fillStyle: self.options.textFillStyle,
                    strokeWidth: 0,
                    fontSize: 0,
                    fontFamily: self.options.textFontFamily,
                    text: node.keyword
                })
                .setLayerGroup("group_" + node.name, {
                    click: function () {
                        self.options.onClickNode(node);
                    },
                    touchStart: function () {
                        self.options.onClickNode(node);
                    }
                })
                .drawLayers();

            this.nodeCount++;

            return self;
        },

        updateNode: function (node) {
            var node_group = "group_" + node.name,
                layer0 = this.$canvas.getLayerGroup(node_group)[0],
                diff = {
                    x: node.x - (layer0.x - this.centerX),
                    y: node.y - (layer0.y - this.centerY)
                };

            this.$canvas
                .stopLayerGroup(node_group)
                .animateLayerGroup(node_group, {
                    x: '+=' + diff.x,
                    y: '+=' + diff.y,
                    //width: node.w,
                    //height: node.h,
                    width: node.w * this.options.goodK,
                    height: node.h * this.options.goodK,
                    fontSize: this.options.textFontBaseSize + this.options.textFontBaseSize * node.good
                }, this.options.duration, this.options.easing);

            return this;
        },

        addEdge: function (edge, px, py, cx, cy) {
            this.$canvas
                .jCanvas()
                .addLayer({
                    layer: true,
                    type: "line",
                    index: 0,
                    name: edge.name,
                    strokeStyle: this.options.edgeStrokeStyle,
                    strokeWidth: 0,
                    x1: this.centerX + px,
                    y1: this.centerY + py,
                    x2: this.centerX + cx,
                    y2: this.centerY + cy
                })
                .drawLayers();

            return this;
        },

        moveEdge: function (edge, px, py, cx, cy) {
            this.$canvas
                .stopLayer(edge.name)
                .animateLayer(edge.name, {
                    strokeWidth: this.options.edgeStrokeWidth,
                    x1: this.centerX + px,
                    y1: this.centerY + py,
                    x2: this.centerX + cx,
                    y2: this.centerY + cy
                }, this.options.duration, this.options.easing);

            return this;
        }
    };
}());