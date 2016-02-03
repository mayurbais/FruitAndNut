'use strict';

angular.module('try1App')
    .controller('ExamSubjectsDetailController', function ($scope, $rootScope, $stateParams, entity, ExamSubjects, Exam, Subject, Teacher, Room) {
        $scope.examSubjects = entity;
        $scope.load = function (id) {
            ExamSubjects.get({id: id}, function(result) {
                $scope.examSubjects = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:examSubjectsUpdate', function(event, result) {
            $scope.examSubjects = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
