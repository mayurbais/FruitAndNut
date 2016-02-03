'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('languageLOV', {
                parent: 'entity',
                url: '/languageLOVs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.languageLOV.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/languageLOV/languageLOVs.html',
                        controller: 'LanguageLOVController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('languageLOV');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('languageLOV.detail', {
                parent: 'entity',
                url: '/languageLOV/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.languageLOV.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/languageLOV/languageLOV-detail.html',
                        controller: 'LanguageLOVDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('languageLOV');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'LanguageLOV', function($stateParams, LanguageLOV) {
                        return LanguageLOV.get({id : $stateParams.id});
                    }]
                }
            })
            .state('languageLOV.new', {
                parent: 'languageLOV',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/languageLOV/languageLOV-dialog.html',
                        controller: 'LanguageLOVDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    value: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('languageLOV', null, { reload: true });
                    }, function() {
                        $state.go('languageLOV');
                    })
                }]
            })
            .state('languageLOV.edit', {
                parent: 'languageLOV',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/languageLOV/languageLOV-dialog.html',
                        controller: 'LanguageLOVDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['LanguageLOV', function(LanguageLOV) {
                                return LanguageLOV.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('languageLOV', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('languageLOV.delete', {
                parent: 'languageLOV',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/languageLOV/languageLOV-delete-dialog.html',
                        controller: 'LanguageLOVDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['LanguageLOV', function(LanguageLOV) {
                                return LanguageLOV.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('languageLOV', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
