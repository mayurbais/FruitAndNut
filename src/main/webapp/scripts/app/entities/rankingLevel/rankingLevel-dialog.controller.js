'use strict';

angular.module('try1App').controller('RankingLevelDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'RankingLevel', 'Course',
        function($scope, $stateParams, $uibModalInstance, $q, entity, RankingLevel, Course) {

        $scope.rankingLevel = entity;
        $scope.courses = Course.query({filter: 'rankinglevel-is-null'});
        $q.all([$scope.rankingLevel.$promise, $scope.courses.$promise]).then(function() {
            if (!$scope.rankingLevel.course || !$scope.rankingLevel.course.id) {
                return $q.reject();
            }
            return Course.get({id : $scope.rankingLevel.course.id}).$promise;
        }).then(function(course) {
            $scope.courses.push(course);
        });
        $scope.load = function(id) {
            RankingLevel.get({id : id}, function(result) {
                $scope.rankingLevel = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:rankingLevelUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.rankingLevel.id != null) {
                RankingLevel.update($scope.rankingLevel, onSaveSuccess, onSaveError);
            } else {
                RankingLevel.save($scope.rankingLevel, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
