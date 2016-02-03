'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('subject', {
                parent: 'entity',
                url: '/subjects',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.subject.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/subject/subjects.html',
                        controller: 'SubjectController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('subject');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('subject.detail', {
                parent: 'entity',
                url: '/subject/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.subject.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/subject/subject-detail.html',
                        controller: 'SubjectDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('subject');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Subject', function($stateParams, Subject) {
                        return Subject.get({id : $stateParams.id});
                    }]
                }
            })
            .state('subject.new', {
                parent: 'subject',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/subject/subject-dialog.html',
                        controller: 'SubjectDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    description: null,
                                    code: null,
                                    noExam: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('subject', null, { reload: true });
                    }, function() {
                        $state.go('subject');
                    })
                }]
            })
            .state('subject.edit', {
                parent: 'subject',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/subject/subject-dialog.html',
                        controller: 'SubjectDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Subject', function(Subject) {
                                return Subject.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('subject', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('subject.delete', {
                parent: 'subject',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/subject/subject-delete-dialog.html',
                        controller: 'SubjectDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Subject', function(Subject) {
                                return Subject.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('subject', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
