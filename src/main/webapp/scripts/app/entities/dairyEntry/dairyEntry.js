'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dairyEntry', {
                parent: 'entity',
                url: '/dairyEntrys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.dairyEntry.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dairyEntry/dairyEntrys.html',
                        controller: 'DairyEntryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dairyEntry');
                        $translatePartialLoader.addPart('dairyEntryType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('dairyEntry.detail', {
                parent: 'entity',
                url: '/dairyEntry/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.dairyEntry.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dairyEntry/dairyEntry-detail.html',
                        controller: 'DairyEntryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dairyEntry');
                        $translatePartialLoader.addPart('dairyEntryType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DairyEntry', function($stateParams, DairyEntry) {
                        return DairyEntry.get({id : $stateParams.id});
                    }]
                }
            })
            .state('dairyEntry.new', {
                parent: 'dairyEntry',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/dairyEntry/dairyEntry-dialog.html',
                        controller: 'DairyEntryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    date: null,
                                    entryType: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('dairyEntry', null, { reload: true });
                    }, function() {
                        $state.go('dairyEntry');
                    })
                }]
            })
            .state('dairyEntry.edit', {
                parent: 'dairyEntry',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/dairyEntry/dairyEntry-dialog.html',
                        controller: 'DairyEntryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DairyEntry', function(DairyEntry) {
                                return DairyEntry.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dairyEntry', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('dairyEntry.delete', {
                parent: 'dairyEntry',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/dairyEntry/dairyEntry-delete-dialog.html',
                        controller: 'DairyEntryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DairyEntry', function(DairyEntry) {
                                return DairyEntry.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dairyEntry', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
