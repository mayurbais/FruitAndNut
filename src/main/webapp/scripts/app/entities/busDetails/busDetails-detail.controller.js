'use strict';

angular.module('try1App')
    .controller('BusDetailsDetailController', function ($scope, $rootScope, $stateParams, entity, BusDetails, SchoolDetails, Student) {
        $scope.busDetails = entity;
        $scope.load = function (id) {
            BusDetails.get({id: id}, function(result) {
                $scope.busDetails = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:busDetailsUpdate', function(event, result) {
            $scope.busDetails = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
