'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('exam', {
                parent: 'entity',
                url: '/exams',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.exam.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/exam/exams.html',
                        controller: 'ExamController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('exam');
                        $translatePartialLoader.addPart('examType');
                        $translatePartialLoader.addPart('progressStatus');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('exam.detail', {
                parent: 'entity',
                url: '/exam/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.exam.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/exam/exam-detail.html',
                        controller: 'ExamDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('exam');
                        $translatePartialLoader.addPart('examType');
                        $translatePartialLoader.addPart('progressStatus');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Exam', function($stateParams, Exam) {
                        return Exam.get({id : $stateParams.id});
                    }]
                }
            })
            .state('exam.new', {
                parent: 'exam',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/exam/exam-dialog.html',
                        controller: 'ExamDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    type: null,
                                    startDate: null,
                                    endDate: null,
                                    isPublished: null,
                                    progressStatus: null,
                                    isResultPublished: null,
                                    classAverage: null,
                                    remarkByPrincipal: null,
                                    remarkByHeadTeacher: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('exam', null, { reload: true });
                    }, function() {
                        $state.go('exam');
                    })
                }]
            })
            .state('exam.edit', {
                parent: 'exam',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/exam/exam-dialog.html',
                        controller: 'ExamDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Exam', function(Exam) {
                                return Exam.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('exam', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('exam.delete', {
                parent: 'exam',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/exam/exam-delete-dialog.html',
                        controller: 'ExamDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Exam', function(Exam) {
                                return Exam.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('exam', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
