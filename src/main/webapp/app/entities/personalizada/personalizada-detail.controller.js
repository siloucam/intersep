(function() {
    'use strict';

    angular
        .module('intersepHipsterApp')
        .controller('PersonalizadaDetailController', PersonalizadaDetailController);

    PersonalizadaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Personalizada'];

    function PersonalizadaDetailController($scope, $rootScope, $stateParams, previousState, entity, Personalizada) {
        var vm = this;

        vm.personalizada = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intersepHipsterApp:personalizadaUpdate', function(event, result) {
            vm.personalizada = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
