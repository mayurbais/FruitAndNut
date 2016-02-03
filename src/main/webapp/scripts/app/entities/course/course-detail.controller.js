'use strict';

angular.module('try1App')
    .controller('CourseDetailController', function ($scope, $rootScope, $stateParams, entity, Course, Subject, ElectiveSubject, AcedemicSession) {
        $scope.course = entity;
        $scope.load = function (id) {
            Course.get({id: id}, function(result) {
                $scope.course = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:courseUpdate', function(event, result) {
            $scope.course = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
