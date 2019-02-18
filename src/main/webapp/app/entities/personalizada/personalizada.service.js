(function() {
    'use strict';
    angular
        .module('intersepApp')
        .factory('Personalizada', Personalizada);

    Personalizada.$inject = ['$resource'];

    function Personalizada ($resource) {
        var resourceUrl =  'api/personalizadas/:id';

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
