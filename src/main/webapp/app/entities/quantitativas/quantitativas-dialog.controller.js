(function() {
    'use strict';

    angular
        .module('intersepHipsterApp')
        .controller('QuantitativasDialogController', QuantitativasDialogController);

    QuantitativasDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Quantitativas'];

    function QuantitativasDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Quantitativas) {
        var vm = this;

        vm.quantitativas = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.quantitativas.id !== null) {
                Quantitativas.update(vm.quantitativas, onSaveSuccess, onSaveError);
            } else {
                Quantitativas.save(vm.quantitativas, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intersepHipsterApp:quantitativasUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
