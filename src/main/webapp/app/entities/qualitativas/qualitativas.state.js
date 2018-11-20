(function() {
    'use strict';

    angular
        .module('intersepHipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('qualitativas', {
            parent: 'entity',
            url: '/qualitativas',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Qualitativas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/qualitativas/qualitativas.html',
                    controller: 'QualitativasController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('qualitativas-detail', {
            parent: 'qualitativas',
            url: '/qualitativas/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Qualitativas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/qualitativas/qualitativas-detail.html',
                    controller: 'QualitativasDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Qualitativas', function($stateParams, Qualitativas) {
                    return Qualitativas.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'qualitativas',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('qualitativas-detail.edit', {
            parent: 'qualitativas-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/qualitativas/qualitativas-dialog.html',
                    controller: 'QualitativasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Qualitativas', function(Qualitativas) {
                            return Qualitativas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('qualitativas.new', {
            parent: 'qualitativas',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/qualitativas/qualitativas-dialog.html',
                    controller: 'QualitativasDialogController',
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
                    $state.go('qualitativas', null, { reload: 'qualitativas' });
                }, function() {
                    $state.go('qualitativas');
                });
            }]
        })
        .state('qualitativas.edit', {
            parent: 'qualitativas',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/qualitativas/qualitativas-dialog.html',
                    controller: 'QualitativasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Qualitativas', function(Qualitativas) {
                            return Qualitativas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('qualitativas', null, { reload: 'qualitativas' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('qualitativas.delete', {
            parent: 'qualitativas',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/qualitativas/qualitativas-delete-dialog.html',
                    controller: 'QualitativasDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Qualitativas', function(Qualitativas) {
                            return Qualitativas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('qualitativas', null, { reload: 'qualitativas' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
