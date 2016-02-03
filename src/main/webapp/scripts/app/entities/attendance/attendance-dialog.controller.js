'use strict';

angular.module('try1App').controller('AttendanceDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Attendance', 'IrisUser', 'Section', 'Course',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Attendance, IrisUser, Section, Course) {

        $scope.attendance = entity;
        $scope.irisusers = IrisUser.query({filter: 'attendance-is-null'});
        $q.all([$scope.attendance.$promise, $scope.irisusers.$promise]).then(function() {
            if (!$scope.attendance.irisUser || !$scope.attendance.irisUser.id) {
                return $q.reject();
            }
            return IrisUser.get({id : $scope.attendance.irisUser.id}).$promise;
        }).then(function(irisUser) {
            $scope.irisusers.push(irisUser);
        });
        $scope.sections = Section.query();
        $scope.courses = Course.query();
        $scope.load = function(id) {
            Attendance.get({id : id}, function(result) {
                $scope.attendance = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:attendanceUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.attendance.id != null) {
                Attendance.update($scope.attendance, onSaveSuccess, onSaveError);
            } else {
                Attendance.save($scope.attendance, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForDate = {};

        $scope.datePickerForDate.status = {
            opened: false
        };

        $scope.datePickerForDateOpen = function($event) {
            $scope.datePickerForDate.status.opened = true;
        };
}]);
