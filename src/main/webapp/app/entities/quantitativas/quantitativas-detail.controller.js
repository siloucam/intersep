(function() {
    'use strict';

    angular
        .module('intersepHipsterApp')
        .controller('QuantitativasDetailController', QuantitativasDetailController);

    QuantitativasDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Quantitativas'];

    function QuantitativasDetailController($scope, $rootScope, $stateParams, previousState, entity, Quantitativas) {
        var vm = this;

        vm.quantitativas = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intersepHipsterApp:quantitativasUpdate', function(event, result) {
            vm.quantitativas = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
