'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('notice', {
                parent: 'entity',
                url: '/notices',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.notice.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/notice/notices.html',
                        controller: 'NoticeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('notice');
                        $translatePartialLoader.addPart('noticeType');
                        $translatePartialLoader.addPart('priorityLevel');
                        $translatePartialLoader.addPart('noticeSensitivity');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('notice.detail', {
                parent: 'entity',
                url: '/notice/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.notice.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/notice/notice-detail.html',
                        controller: 'NoticeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('notice');
                        $translatePartialLoader.addPart('noticeType');
                        $translatePartialLoader.addPart('priorityLevel');
                        $translatePartialLoader.addPart('noticeSensitivity');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Notice', function($stateParams, Notice) {
                        return Notice.get({id : $stateParams.id});
                    }]
                }
            })
            .state('notice.new', {
                parent: 'notice',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/notice/notice-dialog.html',
                        controller: 'NoticeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    noticeType: null,
                                    priority: null,
                                    sensitivity: null,
                                    sendDate: null,
                                    isRead: null,
                                    subject: null,
                                    sentBy: null,
                                    sentTo: null,
                                    message: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('notice', null, { reload: true });
                    }, function() {
                        $state.go('notice');
                    })
                }]
            })
            .state('notice.edit', {
                parent: 'notice',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/notice/notice-dialog.html',
                        controller: 'NoticeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Notice', function(Notice) {
                                return Notice.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('notice', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('notice.delete', {
                parent: 'notice',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/notice/notice-delete-dialog.html',
                        controller: 'NoticeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Notice', function(Notice) {
                                return Notice.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('notice', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
