'use strict';

angular.module('try1App')
    .controller('SubjectDetailController', function ($scope, $rootScope, $stateParams, entity, Subject, Course, Teacher) {
        $scope.subject = entity;
        $scope.load = function (id) {
            Subject.get({id: id}, function(result) {
                $scope.subject = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:subjectUpdate', function(event, result) {
            $scope.subject = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
