'use strict';

angular.module('try1App')
    .controller('SchoolDetailsController', function ($scope, $state, SchoolDetails) {

        $scope.schoolDetailss = [];
        $scope.loadAll = function() {
            SchoolDetails.query(function(result) {
               $scope.schoolDetailss = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.schoolDetails = {
                schoolName: null,
                address: null,
                phoneNumber: null,
                attendanceType: null,
                startDayOfTheWeek: null,
                dateFormat: null,
                financialStartDate: null,
                financialEndDate: null,
                logo: null,
                gradingSystem: null,
                enableAutoIncreamentOfAdmissionNo: null,
                enableNewsCommentsModeration: null,
                enableSibling: null,
                enablePasswordChangeAtFirstLogin: null,
                enableRollNumberForStudent: null,
                institutionType: null,
                id: null
            };
        };
    });
