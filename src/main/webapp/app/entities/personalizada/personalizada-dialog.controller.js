(function() {
    'use strict';

    angular
        .module('intersepApp')
        .controller('PersonalizadaDialogController', PersonalizadaDialogController);

    PersonalizadaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Personalizada'];

    function PersonalizadaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Personalizada) {
        var vm = this;

        vm.personalizada = entity;
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
            if (vm.personalizada.id !== null) {
                Personalizada.update(vm.personalizada, onSaveSuccess, onSaveError);
            } else {
                Personalizada.save(vm.personalizada, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intersepApp:personalizadaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
