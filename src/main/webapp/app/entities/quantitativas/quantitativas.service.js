(function() {
    'use strict';
    angular
        .module('intersepHipsterApp')
        .factory('Quantitativas', Quantitativas);

    Quantitativas.$inject = ['$resource'];

    function Quantitativas ($resource) {
        var resourceUrl =  'api/quantitativas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
