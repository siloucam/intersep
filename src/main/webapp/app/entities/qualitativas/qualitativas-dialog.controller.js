(function() {
    'use strict';

    angular
        .module('intersepHipsterApp')
        .controller('QualitativasDialogController', QualitativasDialogController);

    QualitativasDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Qualitativas'];

    function QualitativasDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Qualitativas) {
        var vm = this;

        vm.qualitativas = entity;
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
            if (vm.qualitativas.id !== null) {
                Qualitativas.update(vm.qualitativas, onSaveSuccess, onSaveError);
            } else {
                Qualitativas.save(vm.qualitativas, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intersepHipsterApp:qualitativasUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
