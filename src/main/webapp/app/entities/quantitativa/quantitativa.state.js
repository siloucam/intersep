(function() {
    'use strict';

    angular
        .module('intersepApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('quantitativa', {
            parent: 'entity',
            url: '/quantitativa',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intersepApp.quantitativa.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/quantitativa/quantitativas.html',
                    controller: 'QuantitativaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('quantitativa');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('quantitativa-detail', {
            parent: 'quantitativa',
            url: '/quantitativa/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intersepApp.quantitativa.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/quantitativa/quantitativa-detail.html',
                    controller: 'QuantitativaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('quantitativa');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Quantitativa', function($stateParams, Quantitativa) {
                    return Quantitativa.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'quantitativa',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('quantitativa-detail.edit', {
            parent: 'quantitativa-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/quantitativa/quantitativa-dialog.html',
                    controller: 'QuantitativaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Quantitativa', function(Quantitativa) {
                            return Quantitativa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('quantitativa.new', {
            parent: 'quantitativa',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/quantitativa/quantitativa-dialog.html',
                    controller: 'QuantitativaDialogController',
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
                    $state.go('quantitativa', null, { reload: 'quantitativa' });
                }, function() {
                    $state.go('quantitativa');
                });
            }]
        })
        .state('quantitativa.edit', {
            parent: 'quantitativa',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/quantitativa/quantitativa-dialog.html',
                    controller: 'QuantitativaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Quantitativa', function(Quantitativa) {
                            return Quantitativa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('quantitativa', null, { reload: 'quantitativa' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('quantitativa.delete', {
            parent: 'quantitativa',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/quantitativa/quantitativa-delete-dialog.html',
                    controller: 'QuantitativaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Quantitativa', function(Quantitativa) {
                            return Quantitativa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('quantitativa', null, { reload: 'quantitativa' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
