'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('classRoomSession', {
                parent: 'entity',
                url: '/classRoomSessions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.classRoomSession.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/classRoomSession/classRoomSessions.html',
                        controller: 'ClassRoomSessionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('classRoomSession');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('classRoomSession.detail', {
                parent: 'entity',
                url: '/classRoomSession/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.classRoomSession.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/classRoomSession/classRoomSession-detail.html',
                        controller: 'ClassRoomSessionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('classRoomSession');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ClassRoomSession', function($stateParams, ClassRoomSession) {
                        return ClassRoomSession.get({id : $stateParams.id});
                    }]
                }
            })
            .state('classRoomSession.new', {
                parent: 'classRoomSession',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/classRoomSession/classRoomSession-dialog.html',
                        controller: 'ClassRoomSessionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    sessionName: null,
                                    startTime: null,
                                    endTime: null,
                                    isBreak: null,
                                    attribute: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('classRoomSession', null, { reload: true });
                    }, function() {
                        $state.go('classRoomSession');
                    })
                }]
            })
            .state('classRoomSession.edit', {
                parent: 'classRoomSession',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/classRoomSession/classRoomSession-dialog.html',
                        controller: 'ClassRoomSessionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ClassRoomSession', function(ClassRoomSession) {
                                return ClassRoomSession.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('classRoomSession', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('classRoomSession.delete', {
                parent: 'classRoomSession',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/classRoomSession/classRoomSession-delete-dialog.html',
                        controller: 'ClassRoomSessionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ClassRoomSession', function(ClassRoomSession) {
                                return ClassRoomSession.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('classRoomSession', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
