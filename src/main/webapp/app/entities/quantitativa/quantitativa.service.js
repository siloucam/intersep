(function() {
    'use strict';
    angular
        .module('intersepApp')
        .factory('Quantitativa', Quantitativa);

    Quantitativa.$inject = ['$resource'];

    function Quantitativa ($resource) {
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
