'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('building', {
                parent: 'entity',
                url: '/buildings',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.building.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/building/buildings.html',
                        controller: 'BuildingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('building');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('building.detail', {
                parent: 'entity',
                url: '/building/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.building.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/building/building-detail.html',
                        controller: 'BuildingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('building');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Building', function($stateParams, Building) {
                        return Building.get({id : $stateParams.id});
                    }]
                }
            })
            .state('building.new', {
                parent: 'building',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/building/building-dialog.html',
                        controller: 'BuildingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('building', null, { reload: true });
                    }, function() {
                        $state.go('building');
                    })
                }]
            })
            .state('building.edit', {
                parent: 'building',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/building/building-dialog.html',
                        controller: 'BuildingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Building', function(Building) {
                                return Building.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('building', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('building.delete', {
                parent: 'building',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/building/building-delete-dialog.html',
                        controller: 'BuildingDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Building', function(Building) {
                                return Building.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('building', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
