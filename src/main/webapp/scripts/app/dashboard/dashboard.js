'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dashboardView', {
                parent: 'entity',
                url: '/dashboardView',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.dashboardView.title'
                },
                views: {
                    'content@': {
                    	templateUrl: 'scripts/app/dashboard/dashboard.html',
                        controller: 'DashboardController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dashboardView');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            
            
    });
