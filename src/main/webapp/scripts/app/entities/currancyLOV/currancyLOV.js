'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('currancyLOV', {
                parent: 'entity',
                url: '/currancyLOVs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.currancyLOV.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/currancyLOV/currancyLOVs.html',
                        controller: 'CurrancyLOVController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('currancyLOV');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('currancyLOV.detail', {
                parent: 'entity',
                url: '/currancyLOV/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.currancyLOV.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/currancyLOV/currancyLOV-detail.html',
                        controller: 'CurrancyLOVDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('currancyLOV');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CurrancyLOV', function($stateParams, CurrancyLOV) {
                        return CurrancyLOV.get({id : $stateParams.id});
                    }]
                }
            })
            .state('currancyLOV.new', {
                parent: 'currancyLOV',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/currancyLOV/currancyLOV-dialog.html',
                        controller: 'CurrancyLOVDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    value: null,
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('currancyLOV', null, { reload: true });
                    }, function() {
                        $state.go('currancyLOV');
                    })
                }]
            })
            .state('currancyLOV.edit', {
                parent: 'currancyLOV',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/currancyLOV/currancyLOV-dialog.html',
                        controller: 'CurrancyLOVDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CurrancyLOV', function(CurrancyLOV) {
                                return CurrancyLOV.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('currancyLOV', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('currancyLOV.delete', {
                parent: 'currancyLOV',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/currancyLOV/currancyLOV-delete-dialog.html',
                        controller: 'CurrancyLOVDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CurrancyLOV', function(CurrancyLOV) {
                                return CurrancyLOV.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('currancyLOV', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
