'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('timeSlot', {
                parent: 'entity',
                url: '/timeSlots',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.timeSlot.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/timeSlot/timeSlots.html',
                        controller: 'TimeSlotController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('timeSlot');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('timeSlot.detail', {
                parent: 'entity',
                url: '/timeSlot/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.timeSlot.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/timeSlot/timeSlot-detail.html',
                        controller: 'TimeSlotDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('timeSlot');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TimeSlot', function($stateParams, TimeSlot) {
                        return TimeSlot.get({id : $stateParams.id});
                    }]
                }
            })
            .state('timeSlot.new', {
                parent: 'timeSlot',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/timeSlot/timeSlot-dialog.html',
                        controller: 'TimeSlotDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    startTime: null,
                                    endTime: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('timeSlot', null, { reload: true });
                    }, function() {
                        $state.go('timeSlot');
                    })
                }]
            })
            .state('timeSlot.edit', {
                parent: 'timeSlot',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/timeSlot/timeSlot-dialog.html',
                        controller: 'TimeSlotDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TimeSlot', function(TimeSlot) {
                                return TimeSlot.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('timeSlot', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('timeSlot.delete', {
                parent: 'timeSlot',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/timeSlot/timeSlot-delete-dialog.html',
                        controller: 'TimeSlotDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['TimeSlot', function(TimeSlot) {
                                return TimeSlot.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('timeSlot', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
