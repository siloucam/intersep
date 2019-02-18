(function() {
    'use strict';

    angular
        .module('intersepApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('qualitativa', {
            parent: 'entity',
            url: '/qualitativa',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intersepApp.qualitativa.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/qualitativa/qualitativas.html',
                    controller: 'QualitativaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('qualitativa');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('qualitativa-detail', {
            parent: 'qualitativa',
            url: '/qualitativa/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'intersepApp.qualitativa.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/qualitativa/qualitativa-detail.html',
                    controller: 'QualitativaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('qualitativa');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Qualitativa', function($stateParams, Qualitativa) {
                    return Qualitativa.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'qualitativa',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('qualitativa-detail.edit', {
            parent: 'qualitativa-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/qualitativa/qualitativa-dialog.html',
                    controller: 'QualitativaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Qualitativa', function(Qualitativa) {
                            return Qualitativa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('qualitativa.new', {
            parent: 'qualitativa',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/qualitativa/qualitativa-dialog.html',
                    controller: 'QualitativaDialogController',
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
                    $state.go('qualitativa', null, { reload: 'qualitativa' });
                }, function() {
                    $state.go('qualitativa');
                });
            }]
        })
        .state('qualitativa.edit', {
            parent: 'qualitativa',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/qualitativa/qualitativa-dialog.html',
                    controller: 'QualitativaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Qualitativa', function(Qualitativa) {
                            return Qualitativa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('qualitativa', null, { reload: 'qualitativa' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('qualitativa.delete', {
            parent: 'qualitativa',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/qualitativa/qualitativa-delete-dialog.html',
                    controller: 'QualitativaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Qualitativa', function(Qualitativa) {
                            return Qualitativa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('qualitativa', null, { reload: 'qualitativa' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
