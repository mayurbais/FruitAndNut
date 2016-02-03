'use strict';

angular.module('try1App')
    .controller('ExamController', function ($scope, $state, Exam) {

        $scope.exams = [];
        $scope.loadAll = function() {
            Exam.query(function(result) {
               $scope.exams = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.exam = {
                name: null,
                type: null,
                startDate: null,
                endDate: null,
                isPublished: null,
                progressStatus: null,
                isResultPublished: null,
                classAverage: null,
                remarkByPrincipal: null,
                remarkByHeadTeacher: null,
                id: null
            };
        };
    });
