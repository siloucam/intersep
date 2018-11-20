(function() {
    'use strict';

    angular
        .module('intersepHipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('personalizada', {
            parent: 'entity',
            url: '/personalizada',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Personalizadas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/personalizada/personalizadas.html',
                    controller: 'PersonalizadaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('personalizada-detail', {
            parent: 'personalizada',
            url: '/personalizada/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Personalizada'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/personalizada/personalizada-detail.html',
                    controller: 'PersonalizadaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Personalizada', function($stateParams, Personalizada) {
                    return Personalizada.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'personalizada',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('personalizada-detail.edit', {
            parent: 'personalizada-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personalizada/personalizada-dialog.html',
                    controller: 'PersonalizadaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Personalizada', function(Personalizada) {
                            return Personalizada.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('personalizada.new', {
            parent: 'personalizada',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personalizada/personalizada-dialog.html',
                    controller: 'PersonalizadaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                identificador: null,
                                query: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('personalizada', null, { reload: 'personalizada' });
                }, function() {
                    $state.go('personalizada');
                });
            }]
        })
        .state('personalizada.edit', {
            parent: 'personalizada',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personalizada/personalizada-dialog.html',
                    controller: 'PersonalizadaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Personalizada', function(Personalizada) {
                            return Personalizada.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('personalizada', null, { reload: 'personalizada' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('personalizada.delete', {
            parent: 'personalizada',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personalizada/personalizada-delete-dialog.html',
                    controller: 'PersonalizadaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Personalizada', function(Personalizada) {
                            return Personalizada.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('personalizada', null, { reload: 'personalizada' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
