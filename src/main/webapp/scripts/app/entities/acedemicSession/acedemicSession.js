'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('acedemicSession', {
                parent: 'entity',
                url: '/acedemicSessions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.acedemicSession.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/acedemicSession/acedemicSessions.html',
                        controller: 'AcedemicSessionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('acedemicSession');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('acedemicSession.detail', {
                parent: 'entity',
                url: '/acedemicSession/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.acedemicSession.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/acedemicSession/acedemicSession-detail.html',
                        controller: 'AcedemicSessionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('acedemicSession');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AcedemicSession', function($stateParams, AcedemicSession) {
                        return AcedemicSession.get({id : $stateParams.id});
                    }]
                }
            })
            .state('acedemicSession.new', {
                parent: 'acedemicSession',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/acedemicSession/acedemicSession-dialog.html',
                        controller: 'AcedemicSessionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    sessionStartDate: null,
                                    sessionEndDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('acedemicSession', null, { reload: true });
                    }, function() {
                        $state.go('acedemicSession');
                    })
                }]
            })
            .state('acedemicSession.edit', {
                parent: 'acedemicSession',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/acedemicSession/acedemicSession-dialog.html',
                        controller: 'AcedemicSessionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['AcedemicSession', function(AcedemicSession) {
                                return AcedemicSession.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('acedemicSession', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('acedemicSession.delete', {
                parent: 'acedemicSession',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/acedemicSession/acedemicSession-delete-dialog.html',
                        controller: 'AcedemicSessionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['AcedemicSession', function(AcedemicSession) {
                                return AcedemicSession.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('acedemicSession', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
