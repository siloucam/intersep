(function() {
    'use strict';

    angular
        .module('intersepHipsterApp')
        .controller('QualitativasController', QualitativasController);

    QualitativasController.$inject = ['Qualitativas'];

    function QualitativasController(Qualitativas) {

        var vm = this;

        vm.qualitativas = [];

        loadAll();

        function loadAll() {
            Qualitativas.query(function(result) {
                vm.qualitativas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
