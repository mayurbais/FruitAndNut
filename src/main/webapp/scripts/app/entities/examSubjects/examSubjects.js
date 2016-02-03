'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('examSubjects', {
                parent: 'entity',
                url: '/examSubjectss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.examSubjects.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/examSubjects/examSubjectss.html',
                        controller: 'ExamSubjectsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('examSubjects');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('examSubjects.detail', {
                parent: 'entity',
                url: '/examSubjects/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.examSubjects.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/examSubjects/examSubjects-detail.html',
                        controller: 'ExamSubjectsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('examSubjects');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ExamSubjects', function($stateParams, ExamSubjects) {
                        return ExamSubjects.get({id : $stateParams.id});
                    }]
                }
            })
            .state('examSubjects.new', {
                parent: 'examSubjects',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/examSubjects/examSubjects-dialog.html',
                        controller: 'ExamSubjectsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    maxMarks: null,
                                    minPassMark: null,
                                    isGrade: null,
                                    startTime: null,
                                    endTime: null,
                                    conductingDate: null,
                                    isResultPublished: null,
                                    classAverage: null,
                                    remarkByPrincipal: null,
                                    remarkByHeadTeacher: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('examSubjects', null, { reload: true });
                    }, function() {
                        $state.go('examSubjects');
                    })
                }]
            })
            .state('examSubjects.edit', {
                parent: 'examSubjects',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/examSubjects/examSubjects-dialog.html',
                        controller: 'ExamSubjectsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ExamSubjects', function(ExamSubjects) {
                                return ExamSubjects.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('examSubjects', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('examSubjects.delete', {
                parent: 'examSubjects',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/examSubjects/examSubjects-delete-dialog.html',
                        controller: 'ExamSubjectsDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ExamSubjects', function(ExamSubjects) {
                                return ExamSubjects.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('examSubjects', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
