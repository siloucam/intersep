(function() {
    'use strict';
    angular
        .module('intersepHipsterApp')
        .factory('Qualitativas', Qualitativas);

    Qualitativas.$inject = ['$resource'];

    function Qualitativas ($resource) {
        var resourceUrl =  'api/qualitativas/:id';

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
