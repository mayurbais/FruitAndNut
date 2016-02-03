'use strict';

angular.module('try1App')
    .controller('AttendanceController', function ($scope, $state, Attendance) {

        $scope.attendances = [];
        $scope.loadAll = function() {
            Attendance.query(function(result) {
               $scope.attendances = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.attendance = {
                attendanceFor: null,
                date: null,
                isPresent: null,
                reasonForAbsent: null,
                isApproved: null,
                approvedBy: null,
                attribute: null,
                id: null
            };
        };
    });
