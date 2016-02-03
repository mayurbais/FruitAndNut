'use strict';

angular.module('try1App')
    .controller('SectionDetailController', function ($scope, $rootScope, $stateParams, entity, Section, Course, Teacher, Room, TimeTable) {
        $scope.section = entity;
        $scope.load = function (id) {
            Section.get({id: id}, function(result) {
                $scope.section = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:sectionUpdate', function(event, result) {
            $scope.section = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
