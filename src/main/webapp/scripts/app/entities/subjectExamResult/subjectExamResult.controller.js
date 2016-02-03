'use strict';

angular.module('try1App')
    .controller('SubjectExamResultController', function ($scope, $state, SubjectExamResult) {

        $scope.subjectExamResults = [];
        $scope.loadAll = function() {
            SubjectExamResult.query(function(result) {
               $scope.subjectExamResults = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.subjectExamResult = {
                studentId: null,
                marksObtained: null,
                grade: null,
                isPassed: null,
                isAbsent: null,
                remark: null,
                id: null
            };
        };
    });
