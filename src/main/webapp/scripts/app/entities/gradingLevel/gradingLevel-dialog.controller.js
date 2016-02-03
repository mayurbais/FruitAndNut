'use strict';

angular.module('try1App').controller('GradingLevelDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'GradingLevel', 'Course',
        function($scope, $stateParams, $uibModalInstance, $q, entity, GradingLevel, Course) {

        $scope.gradingLevel = entity;
        $scope.courses = Course.query({filter: 'gradinglevel-is-null'});
        $q.all([$scope.gradingLevel.$promise, $scope.courses.$promise]).then(function() {
            if (!$scope.gradingLevel.course || !$scope.gradingLevel.course.id) {
                return $q.reject();
            }
            return Course.get({id : $scope.gradingLevel.course.id}).$promise;
        }).then(function(course) {
            $scope.courses.push(course);
        });
        $scope.load = function(id) {
            GradingLevel.get({id : id}, function(result) {
                $scope.gradingLevel = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:gradingLevelUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.gradingLevel.id != null) {
                GradingLevel.update($scope.gradingLevel, onSaveSuccess, onSaveError);
            } else {
                GradingLevel.save($scope.gradingLevel, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
