'use strict';

angular.module('try1App')
    .controller('ExamResultController', function ($scope, $state, ExamResult) {

        $scope.examResults = [];
        $scope.loadAll = function() {
            ExamResult.query(function(result) {
               $scope.examResults = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.examResult = {
                studentId: null,
                percentage: null,
                grade: null,
                isPassed: null,
                isAbsent: null,
                remark: null,
                id: null
            };
        };
    });
