'use strict';

angular.module('try1App').controller('StudentDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Student', 'StudentCategory', 'Course', 'Section', 'IrisUser', 'Parent','BusDetails',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Student, StudentCategory, Course, Section, IrisUser, Parent, BusDetails) {

        $scope.student = entity;
        $scope.parents = Parent.query();
        $scope.studentcategorys = StudentCategory.query({filter: 'student-is-null'});
        $q.all([$scope.student.$promise, $scope.studentcategorys.$promise]).then(function() {
            if (!$scope.student.studentCategory || !$scope.student.studentCategory.id) {
                return $q.reject();
            }
            return StudentCategory.get({id : $scope.student.studentCategory.id}).$promise;
        }).then(function(studentCategory) {
            $scope.studentcategorys.push(studentCategory);
        });
        $scope.courses = Course.query({filter: 'student-is-null'});
        $q.all([$scope.student.$promise, $scope.courses.$promise]).then(function() {
            if (!$scope.student.course || !$scope.student.course.id) {
                return $q.reject();
            }
            return Course.get({id : $scope.student.course.id}).$promise;
        }).then(function(course) {
            $scope.courses.push(course);
        });
        $scope.sections = Section.query({filter: 'student-is-null'});
        $q.all([$scope.student.$promise, $scope.sections.$promise]).then(function() {
            if (!$scope.student.section || !$scope.student.section.id) {
                return $q.reject();
            }
            return Section.get({id : $scope.student.section.id}).$promise;
        }).then(function(section) {
            $scope.sections.push(section);
        });
        $scope.students = Student.query();
        $scope.irisusers = IrisUser.query({filter: 'student-is-null'});
        $q.all([$scope.student.$promise, $scope.irisusers.$promise]).then(function() {
            if (!$scope.student.irisUser || !$scope.student.irisUser.id) {
                return $q.reject();
            }
            return IrisUser.get({id : $scope.student.irisUser.id}).$promise;
        }).then(function(irisUser) {
            $scope.irisusers.push(irisUser);
        });
        
        $scope.busDetailss = BusDetails.query({filter: 'student-is-null'});
        $q.all([$scope.student.$promise, $scope.irisusers.$promise]).then(function() {
            if (!$scope.student.busDetail || !$scope.student.busDetail.id) {
                return $q.reject();
            }
            return BusDetails.get({id : $scope.student.busDetails.id}).$promise;
        }).then(function(busDetails) {
            $scope.busDetails.push(busDetails);
        });
        
        $scope.load = function(id) {
            Student.get({id : id}, function(result) {
                $scope.student = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:studentUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.student.id != null) {
                Student.update($scope.student, onSaveSuccess, onSaveError);
            } else {
                Student.save($scope.student, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);


angular.module('try1App').controller('AdmissionStudentDialogController',
	    ['$scope', '$stateParams',  '$q',  'Student', 'StudentCategory', 'Course', 'Section', 'IrisUser',
	        function($scope, $stateParams,  $q,  Student, StudentCategory, Course, Section, IrisUser) {
	    	
	        $scope.student =  function () {
                return {
                    rollNo: null,
                    id: null
                };
            };
	        $scope.studentcategorys = StudentCategory.query({filter: 'student-is-null'});
	        $q.all([$scope.student.$promise, $scope.studentcategorys.$promise]).then(function() {
	            if (!$scope.student.studentCategory || !$scope.student.studentCategory.id) {
	                return $q.reject();
	            }
	            return StudentCategory.get({id : $scope.student.studentCategory.id}).$promise;
	        }).then(function(studentCategory) {
	            $scope.studentcategorys.push(studentCategory);
	        });
	        $scope.courses = Course.query({filter: 'student-is-null'});
	        $q.all([$scope.student.$promise, $scope.courses.$promise]).then(function() {
	            if (!$scope.student.course || !$scope.student.course.id) {
	                return $q.reject();
	            }
	            return Course.get({id : $scope.student.course.id}).$promise;
	        }).then(function(course) {
	            $scope.courses.push(course);
	        });
	        $scope.sections = Section.query({filter: 'student-is-null'});
	        $q.all([$scope.student.$promise, $scope.sections.$promise]).then(function() {
	            if (!$scope.student.section || !$scope.student.section.id) {
	                return $q.reject();
	            }
	            return Section.get({id : $scope.student.section.id}).$promise;
	        }).then(function(section) {
	            $scope.sections.push(section);
	        });
	        $scope.students = Student.query();
	        $scope.irisusers = IrisUser.query({filter: 'student-is-null'});
	        $q.all([$scope.student.$promise, $scope.irisusers.$promise]).then(function() {
	            if (!$scope.student.irisUser || !$scope.student.irisUser.id) {
	                return $q.reject();
	            }
	            return IrisUser.get({id : $scope.student.irisUser.id}).$promise;
	        }).then(function(irisUser) {
	            $scope.irisusers.push(irisUser);
	        });
	        $scope.load = function(id) {
	            Student.get({id : id}, function(result) {
	                $scope.student = result;
	            });
	        };

	        var onSaveSuccess = function (result) {
	            $scope.$emit('try1App:studentUpdate', result);
	            $uibModalInstance.close(result);
	            $scope.isSaving = false;
	        };

	        var onSaveError = function (result) {
	            $scope.isSaving = false;
	        };

	        $scope.save = function () {
	            $scope.isSaving = true;
	            if ($scope.student.id != null) {
	                Student.update($scope.student, onSaveSuccess, onSaveError);
	            } else {
	                Student.save($scope.student, onSaveSuccess, onSaveError);
	            }
	        };

	        $scope.clear = function() {
	            $uibModalInstance.dismiss('cancel');
	        };
	}]);
