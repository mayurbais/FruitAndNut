'use strict';

angular.module('try1App')
    .controller('ExamSubjectsController', function ($scope, $state, ExamSubjects) {

        $scope.examSubjectss = [];
        $scope.loadAll = function() {
            ExamSubjects.query(function(result) {
               $scope.examSubjectss = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.examSubjects = {
                maxMarks: null,
                minPassMark: null,
                isGrade: null,
                startTime: null,
                endTime: null,
                conductingDate: null,
                isResultPublished: null,
                classAverage: null,
                remarkByPrincipal: null,
                remarkByHeadTeacher: null,
                id: null
            };
        };
    });
