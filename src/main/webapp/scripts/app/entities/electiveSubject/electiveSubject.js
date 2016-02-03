'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('electiveSubject', {
                parent: 'entity',
                url: '/electiveSubjects',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.electiveSubject.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/electiveSubject/electiveSubjects.html',
                        controller: 'ElectiveSubjectController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('electiveSubject');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('electiveSubject.detail', {
                parent: 'entity',
                url: '/electiveSubject/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.electiveSubject.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/electiveSubject/electiveSubject-detail.html',
                        controller: 'ElectiveSubjectDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('electiveSubject');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ElectiveSubject', function($stateParams, ElectiveSubject) {
                        return ElectiveSubject.get({id : $stateParams.id});
                    }]
                }
            })
            .state('electiveSubject.new', {
                parent: 'electiveSubject',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/electiveSubject/electiveSubject-dialog.html',
                        controller: 'ElectiveSubjectDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    description: null,
                                    code: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('electiveSubject', null, { reload: true });
                    }, function() {
                        $state.go('electiveSubject');
                    })
                }]
            })
            .state('electiveSubject.edit', {
                parent: 'electiveSubject',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/electiveSubject/electiveSubject-dialog.html',
                        controller: 'ElectiveSubjectDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ElectiveSubject', function(ElectiveSubject) {
                                return ElectiveSubject.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('electiveSubject', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('electiveSubject.delete', {
                parent: 'electiveSubject',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/electiveSubject/electiveSubject-delete-dialog.html',
                        controller: 'ElectiveSubjectDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ElectiveSubject', function(ElectiveSubject) {
                                return ElectiveSubject.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('electiveSubject', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
