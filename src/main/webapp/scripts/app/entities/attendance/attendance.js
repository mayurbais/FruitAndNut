'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('attendance', {
                parent: 'entity',
                url: '/attendances',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.attendance.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/attendance/attendances.html',
                        controller: 'AttendanceController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('attendance');
                        $translatePartialLoader.addPart('irisUserRole');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('attendance.detail', {
                parent: 'entity',
                url: '/attendance/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.attendance.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/attendance/attendance-detail.html',
                        controller: 'AttendanceDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('attendance');
                        $translatePartialLoader.addPart('irisUserRole');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Attendance', function($stateParams, Attendance) {
                        return Attendance.get({id : $stateParams.id});
                    }]
                }
            })
            .state('attendance.new', {
                parent: 'attendance',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/attendance/attendance-dialog.html',
                        controller: 'AttendanceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    attendanceFor: null,
                                    date: null,
                                    isPresent: null,
                                    reasonForAbsent: null,
                                    isApproved: null,
                                    approvedBy: null,
                                    attribute: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('attendance', null, { reload: true });
                    }, function() {
                        $state.go('attendance');
                    })
                }]
            })
            .state('attendance.edit', {
                parent: 'attendance',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/attendance/attendance-dialog.html',
                        controller: 'AttendanceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Attendance', function(Attendance) {
                                return Attendance.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('attendance', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('attendance.delete', {
                parent: 'attendance',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/attendance/attendance-delete-dialog.html',
                        controller: 'AttendanceDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Attendance', function(Attendance) {
                                return Attendance.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('attendance', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
