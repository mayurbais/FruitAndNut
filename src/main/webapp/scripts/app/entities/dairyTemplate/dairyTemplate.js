'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dairyTemplate', {
                parent: 'entity',
                url: '/dairyTemplates',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.dairyTemplate.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dairyTemplate/dairyTemplates.html',
                        controller: 'DairyTemplateController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dairyTemplate');
                        $translatePartialLoader.addPart('dairyEntryType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('dairyTemplate.detail', {
                parent: 'entity',
                url: '/dairyTemplate/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.dairyTemplate.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dairyTemplate/dairyTemplate-detail.html',
                        controller: 'DairyTemplateDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dairyTemplate');
                        $translatePartialLoader.addPart('dairyEntryType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DairyTemplate', function($stateParams, DairyTemplate) {
                        return DairyTemplate.get({id : $stateParams.id});
                    }]
                }
            })
            .state('dairyTemplate.new', {
                parent: 'dairyTemplate',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/dairyTemplate/dairyTemplate-dialog.html',
                        controller: 'DairyTemplateDialogController',
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
                        $state.go('dairyTemplate', null, { reload: true });
                    }, function() {
                        $state.go('dairyTemplate');
                    })
                }]
            })
            .state('dairyTemplate.edit', {
                parent: 'dairyTemplate',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/dairyTemplate/dairyTemplate-dialog.html',
                        controller: 'DairyTemplateDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DairyTemplate', function(DairyTemplate) {
                                return DairyTemplate.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dairyTemplate', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('dairyTemplate.delete', {
                parent: 'dairyTemplate',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/dairyTemplate/dairyTemplate-delete-dialog.html',
                        controller: 'DairyTemplateDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DairyTemplate', function(DairyTemplate) {
                                return DairyTemplate.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dairyTemplate', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
