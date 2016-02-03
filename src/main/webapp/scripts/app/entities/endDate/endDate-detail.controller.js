'use strict';

angular.module('try1App')
    .controller('EndDateDetailController', function ($scope, $rootScope, $stateParams, entity, EndDate, TimeSlot, ClassRoomSession, Section) {
        $scope.endDate = entity;
        $scope.load = function (id) {
            EndDate.get({id: id}, function(result) {
                $scope.endDate = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:endDateUpdate', function(event, result) {
            $scope.endDate = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
