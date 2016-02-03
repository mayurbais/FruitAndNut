'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('irisUser', {
                parent: 'entity',
                url: '/irisUsers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.irisUser.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/irisUser/irisUsers.html',
                        controller: 'IrisUserController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('irisUser');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('bloodGroup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('irisUser.detail', {
                parent: 'entity',
                url: '/irisUser/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.irisUser.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/irisUser/irisUser-detail.html',
                        controller: 'IrisUserDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('irisUser');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('bloodGroup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'IrisUser', function($stateParams, IrisUser) {
                        return IrisUser.get({id : $stateParams.id});
                    }]
                }
            })
            .state('irisUser.new', {
                parent: 'irisUser',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/irisUser/irisUser-dialog.html',
                        controller: 'IrisUserDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    firstName: null,
                                    lastName: null,
                                    middleName: null,
                                    dateOfBirth: null,
                                    gender: null,
                                    bloodGroup: null,
                                    birthPlace: null,
                                    religion: null,
                                    photo: null,
                                    phone: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('irisUser', null, { reload: true });
                    }, function() {
                        $state.go('irisUser');
                    })
                }]
            })
            .state('irisUser.edit', {
                parent: 'irisUser',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/irisUser/irisUser-dialog.html',
                        controller: 'IrisUserDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['IrisUser', function(IrisUser) {
                                return IrisUser.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('irisUser', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('irisUser.delete', {
                parent: 'irisUser',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/irisUser/irisUser-delete-dialog.html',
                        controller: 'IrisUserDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['IrisUser', function(IrisUser) {
                                return IrisUser.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('irisUser', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
