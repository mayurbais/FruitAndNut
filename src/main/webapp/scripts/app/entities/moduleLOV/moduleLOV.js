'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('moduleLOV', {
                parent: 'entity',
                url: '/moduleLOVs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.moduleLOV.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/moduleLOV/moduleLOVs.html',
                        controller: 'ModuleLOVController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('moduleLOV');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('moduleLOV.detail', {
                parent: 'entity',
                url: '/moduleLOV/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.moduleLOV.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/moduleLOV/moduleLOV-detail.html',
                        controller: 'ModuleLOVDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('moduleLOV');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ModuleLOV', function($stateParams, ModuleLOV) {
                        return ModuleLOV.get({id : $stateParams.id});
                    }]
                }
            })
            .state('moduleLOV.new', {
                parent: 'moduleLOV',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/moduleLOV/moduleLOV-dialog.html',
                        controller: 'ModuleLOVDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    type: null,
                                    name: null,
                                    value: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('moduleLOV', null, { reload: true });
                    }, function() {
                        $state.go('moduleLOV');
                    })
                }]
            })
            .state('moduleLOV.edit', {
                parent: 'moduleLOV',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/moduleLOV/moduleLOV-dialog.html',
                        controller: 'ModuleLOVDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ModuleLOV', function(ModuleLOV) {
                                return ModuleLOV.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('moduleLOV', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('moduleLOV.delete', {
                parent: 'moduleLOV',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/moduleLOV/moduleLOV-delete-dialog.html',
                        controller: 'ModuleLOVDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ModuleLOV', function(ModuleLOV) {
                                return ModuleLOV.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('moduleLOV', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
