'use strict';

angular.module('try1App')
    .controller('AttendanceDetailController', function ($scope, $rootScope, $stateParams, entity, Attendance, IrisUser, Section, Course) {
        $scope.attendance = entity;
        $scope.load = function (id) {
            Attendance.get({id: id}, function(result) {
                $scope.attendance = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:attendanceUpdate', function(event, result) {
            $scope.attendance = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
