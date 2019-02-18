(function() {
    'use strict';

    angular
        .module('intersepApp')
        .controller('QuantitativaDetailController', QuantitativaDetailController);

    QuantitativaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Quantitativa'];

    function QuantitativaDetailController($scope, $rootScope, $stateParams, previousState, entity, Quantitativa) {
        var vm = this;

        vm.quantitativa = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intersepApp:quantitativaUpdate', function(event, result) {
            vm.quantitativa = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
