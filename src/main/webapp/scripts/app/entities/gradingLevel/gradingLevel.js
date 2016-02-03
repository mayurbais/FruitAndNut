'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('gradingLevel', {
                parent: 'entity',
                url: '/gradingLevels',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.gradingLevel.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/gradingLevel/gradingLevels.html',
                        controller: 'GradingLevelController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('gradingLevel');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('gradingLevel.detail', {
                parent: 'entity',
                url: '/gradingLevel/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.gradingLevel.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/gradingLevel/gradingLevel-detail.html',
                        controller: 'GradingLevelDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('gradingLevel');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'GradingLevel', function($stateParams, GradingLevel) {
                        return GradingLevel.get({id : $stateParams.id});
                    }]
                }
            })
            .state('gradingLevel.new', {
                parent: 'gradingLevel',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/gradingLevel/gradingLevel-dialog.html',
                        controller: 'GradingLevelDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    minScore: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('gradingLevel', null, { reload: true });
                    }, function() {
                        $state.go('gradingLevel');
                    })
                }]
            })
            .state('gradingLevel.edit', {
                parent: 'gradingLevel',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/gradingLevel/gradingLevel-dialog.html',
                        controller: 'GradingLevelDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['GradingLevel', function(GradingLevel) {
                                return GradingLevel.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('gradingLevel', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('gradingLevel.delete', {
                parent: 'gradingLevel',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/gradingLevel/gradingLevel-delete-dialog.html',
                        controller: 'GradingLevelDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['GradingLevel', function(GradingLevel) {
                                return GradingLevel.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('gradingLevel', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
