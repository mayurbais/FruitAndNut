'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dairyDescription', {
                parent: 'entity',
                url: '/dairyDescriptions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.dairyDescription.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dairyDescription/dairyDescriptions.html',
                        controller: 'DairyDescriptionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dairyDescription');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('dairyDescription.detail', {
                parent: 'entity',
                url: '/dairyDescription/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.dairyDescription.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dairyDescription/dairyDescription-detail.html',
                        controller: 'DairyDescriptionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dairyDescription');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DairyDescription', function($stateParams, DairyDescription) {
                        return DairyDescription.get({id : $stateParams.id});
                    }]
                }
            })
            .state('dairyDescription.new', {
                parent: 'dairyDescription',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/dairyDescription/dairyDescription-dialog.html',
                        controller: 'DairyDescriptionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    rules: null,
                                    contactNoOfManagment: null,
                                    mission: null,
                                    objective: null,
                                    declaration: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('dairyDescription', null, { reload: true });
                    }, function() {
                        $state.go('dairyDescription');
                    })
                }]
            })
            .state('dairyDescription.edit', {
                parent: 'dairyDescription',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/dairyDescription/dairyDescription-dialog.html',
                        controller: 'DairyDescriptionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DairyDescription', function(DairyDescription) {
                                return DairyDescription.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dairyDescription', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('dairyDescription.delete', {
                parent: 'dairyDescription',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/dairyDescription/dairyDescription-delete-dialog.html',
                        controller: 'DairyDescriptionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DairyDescription', function(DairyDescription) {
                                return DairyDescription.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dairyDescription', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
