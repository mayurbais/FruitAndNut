'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('rankingLevel', {
                parent: 'entity',
                url: '/rankingLevels',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.rankingLevel.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rankingLevel/rankingLevels.html',
                        controller: 'RankingLevelController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rankingLevel');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('rankingLevel.detail', {
                parent: 'entity',
                url: '/rankingLevel/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.rankingLevel.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rankingLevel/rankingLevel-detail.html',
                        controller: 'RankingLevelDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rankingLevel');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'RankingLevel', function($stateParams, RankingLevel) {
                        return RankingLevel.get({id : $stateParams.id});
                    }]
                }
            })
            .state('rankingLevel.new', {
                parent: 'rankingLevel',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rankingLevel/rankingLevel-dialog.html',
                        controller: 'RankingLevelDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    percentageLimit: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('rankingLevel', null, { reload: true });
                    }, function() {
                        $state.go('rankingLevel');
                    })
                }]
            })
            .state('rankingLevel.edit', {
                parent: 'rankingLevel',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rankingLevel/rankingLevel-dialog.html',
                        controller: 'RankingLevelDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['RankingLevel', function(RankingLevel) {
                                return RankingLevel.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rankingLevel', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('rankingLevel.delete', {
                parent: 'rankingLevel',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rankingLevel/rankingLevel-delete-dialog.html',
                        controller: 'RankingLevelDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['RankingLevel', function(RankingLevel) {
                                return RankingLevel.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rankingLevel', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
