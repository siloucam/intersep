(function() {
    'use strict';

    angular
        .module('intersepApp')
        .controller('QualitativaDetailController', QualitativaDetailController);

    QualitativaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Qualitativa'];

    function QualitativaDetailController($scope, $rootScope, $stateParams, previousState, entity, Qualitativa) {
        var vm = this;

        vm.qualitativa = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intersepApp:qualitativaUpdate', function(event, result) {
            vm.qualitativa = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
