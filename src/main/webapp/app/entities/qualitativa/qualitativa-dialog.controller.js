(function() {
    'use strict';

    angular
        .module('intersepApp')
        .controller('QualitativaDialogController', QualitativaDialogController);

    QualitativaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Qualitativa'];

    function QualitativaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Qualitativa) {
        var vm = this;

        vm.qualitativa = entity;
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
            if (vm.qualitativa.id !== null) {
                Qualitativa.update(vm.qualitativa, onSaveSuccess, onSaveError);
            } else {
                Qualitativa.save(vm.qualitativa, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intersepApp:qualitativaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
