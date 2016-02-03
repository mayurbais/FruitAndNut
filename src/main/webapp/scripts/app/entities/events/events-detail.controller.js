'use strict';

angular.module('try1App')
    .controller('EventsDetailController', function ($scope, $rootScope, $stateParams, entity, Events, Section) {
        $scope.events = entity;
        $scope.load = function (id) {
            Events.get({id: id}, function(result) {
                $scope.events = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:eventsUpdate', function(event, result) {
            $scope.events = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
