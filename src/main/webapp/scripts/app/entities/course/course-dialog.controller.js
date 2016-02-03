'use strict';

angular.module('try1App').controller('CourseDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Course', 'Subject', 'ElectiveSubject', 'AcedemicSession',
        function($scope, $stateParams, $uibModalInstance, entity, Course, Subject, ElectiveSubject, AcedemicSession) {

        $scope.course = entity;
        $scope.subjects = Subject.query();
        $scope.electivesubjects = ElectiveSubject.query();
        $scope.acedemicsessions = AcedemicSession.query();
        $scope.load = function(id) {
            Course.get({id : id}, function(result) {
                $scope.course = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:courseUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.course.id != null) {
                Course.update($scope.course, onSaveSuccess, onSaveError);
            } else {
                Course.save($scope.course, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
