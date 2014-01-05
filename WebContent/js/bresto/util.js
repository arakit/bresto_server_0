/**
 * ユーティリティモジュール
 */
var util = util || (function (global) {
    "use strict";

    return {
        /**
         * 名前空間解決関数
         */
        namespace: function (ns_string) {
            var parts = ns_string.split('.'),
                parent = global,
                i;

            for (i = 0; i < parts.length; i++) {
                if (parent[parts[i]] === undefined) {
                    parent[parts[i]] = {};
                }
                parent = parent[parts[i]];
            }
            return parent;
        }
    };
}(document));