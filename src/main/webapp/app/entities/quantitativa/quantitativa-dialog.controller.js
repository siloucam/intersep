(function() {
    'use strict';

    angular
        .module('intersepApp')
        .controller('QuantitativaDialogController', QuantitativaDialogController);

    QuantitativaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Quantitativa'];

    function QuantitativaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Quantitativa) {
        var vm = this;

        vm.quantitativa = entity;
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
            if (vm.quantitativa.id !== null) {
                Quantitativa.update(vm.quantitativa, onSaveSuccess, onSaveError);
            } else {
                Quantitativa.save(vm.quantitativa, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intersepApp:quantitativaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
