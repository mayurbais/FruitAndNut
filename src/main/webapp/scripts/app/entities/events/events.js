'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('events', {
                parent: 'entity',
                url: '/eventss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.events.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/events/eventss.html',
                        controller: 'EventsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('events');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('events.detail', {
                parent: 'entity',
                url: '/events/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.events.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/events/events-detail.html',
                        controller: 'EventsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('events');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Events', function($stateParams, Events) {
                        return Events.get({id : $stateParams.id});
                    }]
                }
            })
            .state('events.new', {
                parent: 'events',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/events/events-dialog.html',
                        controller: 'EventsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    title: null,
                                    description: null,
                                    startDate: null,
                                    endDate: null,
                                    isHoliday: null,
                                    isCommonToAll: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('events', null, { reload: true });
                    }, function() {
                        $state.go('events');
                    })
                }]
            })
            .state('events.edit', {
                parent: 'events',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/events/events-dialog.html',
                        controller: 'EventsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Events', function(Events) {
                                return Events.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('events', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('events.delete', {
                parent: 'events',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/events/events-delete-dialog.html',
                        controller: 'EventsDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Events', function(Events) {
                                return Events.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('events', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
