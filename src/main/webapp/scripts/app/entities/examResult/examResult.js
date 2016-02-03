'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('examResult', {
                parent: 'entity',
                url: '/examResults',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.examResult.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/examResult/examResults.html',
                        controller: 'ExamResultController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('examResult');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('examResult.detail', {
                parent: 'entity',
                url: '/examResult/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.examResult.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/examResult/examResult-detail.html',
                        controller: 'ExamResultDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('examResult');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ExamResult', function($stateParams, ExamResult) {
                        return ExamResult.get({id : $stateParams.id});
                    }]
                }
            })
            .state('examResult.new', {
                parent: 'examResult',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/examResult/examResult-dialog.html',
                        controller: 'ExamResultDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    studentId: null,
                                    percentage: null,
                                    grade: null,
                                    isPassed: null,
                                    isAbsent: null,
                                    remark: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('examResult', null, { reload: true });
                    }, function() {
                        $state.go('examResult');
                    })
                }]
            })
            .state('examResult.edit', {
                parent: 'examResult',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/examResult/examResult-dialog.html',
                        controller: 'ExamResultDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ExamResult', function(ExamResult) {
                                return ExamResult.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('examResult', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('examResult.delete', {
                parent: 'examResult',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/examResult/examResult-delete-dialog.html',
                        controller: 'ExamResultDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ExamResult', function(ExamResult) {
                                return ExamResult.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('examResult', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
