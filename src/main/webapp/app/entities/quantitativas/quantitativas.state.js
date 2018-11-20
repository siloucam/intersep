(function() {
    'use strict';

    angular
        .module('intersepHipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('quantitativas', {
            parent: 'entity',
            url: '/quantitativas',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Quantitativas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/quantitativas/quantitativas.html',
                    controller: 'QuantitativasController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('quantitativas-detail', {
            parent: 'quantitativas',
            url: '/quantitativas/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Quantitativas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/quantitativas/quantitativas-detail.html',
                    controller: 'QuantitativasDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Quantitativas', function($stateParams, Quantitativas) {
                    return Quantitativas.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'quantitativas',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('quantitativas-detail.edit', {
            parent: 'quantitativas-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/quantitativas/quantitativas-dialog.html',
                    controller: 'QuantitativasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Quantitativas', function(Quantitativas) {
                            return Quantitativas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('quantitativas.new', {
            parent: 'quantitativas',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/quantitativas/quantitativas-dialog.html',
                    controller: 'QuantitativasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('quantitativas', null, { reload: 'quantitativas' });
                }, function() {
                    $state.go('quantitativas');
                });
            }]
        })
        .state('quantitativas.edit', {
            parent: 'quantitativas',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/quantitativas/quantitativas-dialog.html',
                    controller: 'QuantitativasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Quantitativas', function(Quantitativas) {
                            return Quantitativas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('quantitativas', null, { reload: 'quantitativas' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('quantitativas.delete', {
            parent: 'quantitativas',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/quantitativas/quantitativas-delete-dialog.html',
                    controller: 'QuantitativasDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Quantitativas', function(Quantitativas) {
                            return Quantitativas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('quantitativas', null, { reload: 'quantitativas' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
