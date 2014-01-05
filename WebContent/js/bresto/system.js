(function () {
    "use strict";

    var bresto = util.namespace("bresto");

    bresto.System = function (canvas) {
        var that = this,
            nodes = {},
            edges = [],
            oldNodes = {},
            oldEdges = [];

        function addNode(kid, node) {
            nodes[kid] = node;
            canvas.addNode(node);
        }

        function updateNode(kid, node) {
            nodes[kid]
                .move(node.x, node.y)
                .zoom(node.w, node.h);
            canvas.updateNode(nodes[kid]);
        }

        function addEdge(use_nodes, edge) {
            var p = use_nodes[edge.from_kid] || {x: 0, y: 0},
                c = use_nodes[edge.to_kid] || p || {x: 0, y: 0};

            edges.push(edge);
            canvas.addEdge(edge, p.x, p.y, c.x, c.y);
        }

        function moveEdge(use_nodes, edge) {
            var n = use_nodes || nodes,
                p = n[edge.from_kid],
                c = n[edge.to_kid];

            canvas.moveEdge(edge, p.x, p.y, c.x, c.y);
        }

        function getFrom(kid, use_edges, use_nodes) {
            var edge,
                es = use_edges || edges,
                ns = use_nodes || nodes;

            edge = _(es).find(function (edge) {
                return edge.to_kid === kid;
            });

            return edge === undefined ? undefined : ns[edge.from_kid];
        }

        that = {
            update: function (new_nodes, new_edges) {
                // 直前のデータを保存
                oldNodes = _.clone(nodes);
                oldEdges = _.clone(edges);

                // ノード追加
                _.each(new_nodes, function (node, kid) {
                    var from;
                    kid = parseInt(kid, 10);
                    if (!_.has(nodes, kid)) {
                        // 親がいたら、その座標に描画する
                        from = getFrom(kid, new_edges);
                        addNode(kid,
                            new bresto.Node(kid,
                                from ? from.x : 0,
                                from ? from.y : 0,
                                node.w, node.h, node.keyword, node.good));
                    }
                });

                // エッジ追加
                _.each(new_edges, function (edge) {
                    if (_.isEmpty(edges) || !_.some(edges, _(bresto.Edge.prototype.isEqual).bind(edge))) {
                        addEdge(oldNodes, new bresto.Edge(edge.from_kid, edge.to_kid));
                    }
                });

                // goodの更新
                _.each(new_nodes, function (node, kid) {
                    nodes[kid].good = node.good;
                });

                // ノードが既に存在していたら動かす
                _.each(new_nodes, function (node, kid) {
                    if (_.has(nodes, kid)) {
                        updateNode(parseInt(kid, 10), node);
                    }
                });

                // 全てのエッジを動かす
                _.each(edges, _(moveEdge).bind(that, nodes));
            },

            setSuggestion: function (kid, values) {
                if (nodes[kid] !== undefined) {
                    nodes[kid].replaceSuggestion(values);
                }
            }
        };

        return that;
    };
}());