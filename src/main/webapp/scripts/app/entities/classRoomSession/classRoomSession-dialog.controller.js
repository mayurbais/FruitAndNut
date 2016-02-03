'use strict';

angular.module('try1App').controller('ClassRoomSessionDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'ClassRoomSession', 'Employee', 'Section', 'Room', 'TimeTable',
        function($scope, $stateParams, $uibModalInstance, $q, entity, ClassRoomSession, Employee, Section, Room, TimeTable) {

        $scope.classRoomSession = entity;
        $scope.employees = Employee.query({filter: 'classroomsession-is-null'});
        $q.all([$scope.classRoomSession.$promise, $scope.employees.$promise]).then(function() {
            if (!$scope.classRoomSession.employee || !$scope.classRoomSession.employee.id) {
                return $q.reject();
            }
            return Employee.get({id : $scope.classRoomSession.employee.id}).$promise;
        }).then(function(employee) {
            $scope.employees.push(employee);
        });
        $scope.sections = Section.query();
        $scope.rooms = Room.query();
        $scope.timetables = TimeTable.query();
        $scope.load = function(id) {
            ClassRoomSession.get({id : id}, function(result) {
                $scope.classRoomSession = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:classRoomSessionUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.classRoomSession.id != null) {
                ClassRoomSession.update($scope.classRoomSession, onSaveSuccess, onSaveError);
            } else {
                ClassRoomSession.save($scope.classRoomSession, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
